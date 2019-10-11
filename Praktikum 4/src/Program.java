import de.inetsoftware.jwebassembly.JWebAssembly;
import de.inetsoftware.jwebassembly.module.*;

public class Program implements Emitter {

    @Override
    public void emit() {
        try {
            program();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void program() throws Exception {
        statementSequence();
    }

    static void statementSequence() throws Exception {
        do {
            statement();
        } while (Scanner.la != Token.EOF && Scanner.la != Token.RCBRACK);
    }

    /**
     * statement = (assignment | returnStatement | if | while | block)
     */
    static void statement() throws Exception {
        switch (Scanner.la) {
            case Token.RETURN:
                returnStatement();
                break;
            case Token.IF:
                ifStatement();
                break;
            case Token.WHILE:
                whileStatement();
                break;
            case Token.LCBRACK:
                blockStatement();
                break;
            default:
                assignement();
        }
    }

    /**
     * if = "if" condition statement [ "else" statement]
     * unreachable verwenden
     */
    static void ifStatement() throws Exception {
        Scanner.check(Token.IF);
        condition();
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.IF, null, 0));
        statement();
        if(Scanner.la == Token.ELSE){
            Scanner.check(Token.ELSE);
            JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.ELSE, null, 0));
            statement();
        }
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));
    }

    static void condition() throws Exception {
        Scanner.check(Token.LBRACK);
        boolean hasNot = false;
        if (Scanner.la == Token.NOT) {
            Scanner.check(Token.NOT);
            hasNot = true;
        }
        expr();
        JWebAssembly.il.add(new WasmLoadStoreInstruction(true, JWebAssembly.local(ValueType.f64, Scanner.token.str), 0));
        JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.nearest, ValueType.f64, 0));
        JWebAssembly.il.add(new WasmConvertInstruction(ValueTypeConvertion.d2i, 0));

        if (hasNot) {
            JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.eqz, ValueType.i32, 0));
        } else {
            push(0.0);
            JWebAssembly.il.add(new WasmConvertInstruction(ValueTypeConvertion.d2i, 0));
            JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.ne, ValueType.i32, 0));
        }
        Scanner.check(Token.RBRACK);
    }

    /**
     * while = "while " condition statement
     */
    static void whileStatement() throws Exception {
        Scanner.check(Token.WHILE);
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BLOCK, null, 0));
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.LOOP, null, 0));
        condition();
        JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.eqz,ValueType.i32,0));
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BR_IF, 1, 0));
        statement();
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BR, 0, 0));
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));


    }

    /**
     * block = "{" statementSequence "}"
     * @throws Exception
     */
    static void blockStatement() throws Exception {
        Scanner.check(Token.LCBRACK);
        statementSequence();
        Scanner.check(Token.RCBRACK);
    }

    /**
     * returnStatement = "return" expr ";"
     */
    static void returnStatement() throws Exception {
        Scanner.check(Token.RETURN);
        expr();
        Scanner.check(Token.SCOLON);
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.RETURN, null, 0));
    }

    static void assignement() throws Exception {
        Scanner.check(Token.IDENT);
        int slot = JWebAssembly.local(ValueType.f64, Scanner.token.str);
        Scanner.check(Token.EQUAL);
        expr();
        Scanner.check(Token.SCOLON);
        JWebAssembly.il.add(new WasmLoadStoreInstruction(false, slot, 0));
    }


    static void push(double val) {
        JWebAssembly.il.add(new WasmConstInstruction(val, 0));
    }

    static double pop() {
        return 0.0;
    }

    static void expr() throws Exception {
        term();
        while (Scanner.la == Token.PLUS
                || Scanner.la == Token.MINUS) {
            Scanner.scan();
            int op = Scanner.token.kind;
            term();
            if (op != Token.PLUS) {
                JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.sub, ValueType.f64, 0));
            } else {
                JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.add, ValueType.f64, 0));
            }
        }
    }

    static void term() throws Exception {
        factor();
        while (Scanner.la == Token.TIMES || Scanner.la == Token.SLASH) {
            Scanner.scan();
            int op = Scanner.token.kind;
            factor();
            if (op != Token.TIMES) {
                JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.div, ValueType.f64, 0));
            } else {
                JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.mul, ValueType.f64, 0));
            }
        }
    }

    static void factor() throws Exception {
        if (Scanner.la == Token.LBRACK) {
            Scanner.scan();
            expr();
            Scanner.check(Token.RBRACK);
        } else if (Scanner.la == Token.NUMBER) {
            Scanner.scan();
            push(Scanner.token.val);
        } else if (Scanner.la == Token.IDENT) {
            Scanner.scan();
            JWebAssembly.il.add(new WasmLoadStoreInstruction(true, JWebAssembly.local(ValueType.f64, Scanner.token.str), 0));
        } else {
            Scanner.error("illegal Symbol");
        }
    }

}

