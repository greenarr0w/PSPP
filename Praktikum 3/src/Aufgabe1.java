import de.inetsoftware.jwebassembly.JWebAssembly;

public class Aufgabe1 {

    public static void main(String[] args) throws Exception {
        Scanner.init("4.2 + 3.2*2");
        Scanner.scan();
        JWebAssembly.emitCode(ICalculator.class, new Calculator());
    }

}
