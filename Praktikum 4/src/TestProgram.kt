import de.inetsoftware.jwebassembly.JWebAssembly

object Test1 {
    @JvmStatic
    fun main(args: Array<String>) {
        Scanner.init("""
                x = ${"$"}arg0;
                c = x - 10;
                if(c){ 
                    r = 1; 
                } else { 
                    r = 0; 
                }
                return r;
            """
        )
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }
}

object Test2 {
    @JvmStatic
    fun main(args: Array<String>) {
        Scanner.init("""
                x = ${"$"}arg0;
                c = x - 10;
                if(c){ 
                    return 1; 
                } else { 
                    return 0; 
                }
            """
        )
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }
}

object Test3 {
    @JvmStatic
    fun main(args: Array<String>) {
        Scanner.init("""
                x = ${"$"}arg0;
                c = x - 10;
                if(!c){ 
                    return 1; 
                } else { 
                    return 0; 
                }
            """        )
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }
}

object Test4 {
    @JvmStatic
    fun main(args: Array<String>) {
        Scanner.init("""
                x = ${"$"}arg0;
                r = 0;
                while(x){
                    r = r + 1;
                    x = x - 1;
                }
                return r;
            """        )
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }
}

/**
 * Beispiel Fakultät
 */
object Test5 {
    @JvmStatic
    fun main(args: Array<String>) {
        Scanner.init("""
             m = ${"$"}arg0;
             s = 1;
             while (m) {
                s = s * m;
                m = m - 1;
             }
             return s;
            """        )
        Scanner.scan()
        JWebAssembly.emitCode(IProgram::class.java, Program())
    }
}

