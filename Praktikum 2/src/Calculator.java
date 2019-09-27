import java.util.HashMap;

/**
 * User: Karl Rege
 */


class Calculator {

    static double stack[] = new double[100];
    static int sp = 0;

    static HashMap<String, Double> doubleConstants = new HashMap<>() {
        {
            put("PI", Math.PI);
            put("E", Math.E);
        }
    };

    static void push(double val) {
        stack[sp++] = val;
    }

    static double pop() {
        return stack[--sp];
    }

    static void expr() throws Exception {
        term();
        while (Scanner.la == Token.PLUS
                || Scanner.la == Token.MINUS) {
            Scanner.scan();
            int op = Scanner.token.kind;
            term();
            if (op == Token.PLUS) {
                push(pop() + pop());
            } else {
                push(-pop() + pop());
            }
        }
    }

    static void term() throws Exception {
        factor();
        while (Scanner.la == Token.TIMES || Scanner.la == Token.SLASH) {
            Scanner.scan();
            int op = Scanner.token.kind;
            factor();
            if (op == Token.TIMES) {
                push(pop() * pop());
            } else {
                push(1 / pop() * pop()); // 1 / divisor * dividend == dividend / divisor
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
            String ident = Scanner.token.str;
            Double doubleConstant = doubleConstants.get(ident);
            if (doubleConstant != null) {
                push(doubleConstant);
            } else {
                throw new IllegalStateException("Illegal Constant " + ident);
            }

        }
    }


    public static void main(String[] args) throws Exception {
        Scanner.init("3.4+2-4-1.4+5+3.1"); // result 8.1
        Scanner.scan();
        expr();
        System.out.println("result=" + pop());

        Scanner.init("3.3*5"); // result 16.5
        Scanner.scan();
        expr();
        System.out.println("result=" + pop());

        Scanner.init("9/3"); // result 3
        Scanner.scan();
        expr();
        System.out.println("result=" + pop());

        Scanner.init("4*PI + E"); // result 15.2846524428
        Scanner.scan();
        expr();
        System.out.println("result=" + pop());
    }


}
