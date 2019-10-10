import de.inetsoftware.jwebassembly.JWebAssembly

object Aufgabe2 {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        Scanner.init("""
                x = ${"$"}arg0;
                a = 1;
                a = a + a;
                b = 4 * a + 4;
                c = 40;
                result = a * x + b * x + c / x;
            """
        ) // = 144
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }

}
