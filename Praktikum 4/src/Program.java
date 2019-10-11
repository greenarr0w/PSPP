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
        JWebAssembly.il.add(new WasmLoadStoreInstruction(true, JWebAssembly.local(ValueType.f64, "result"), 0));
        JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.RETURN, null, 0));
    }

    static void statementSequence() throws Exception {
        do {
            statement();
        } while (Scanner.la != Token.EOF);
    }

    static void statement() throws Exception {
        assignement();
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

