import org.antlr.v4.runtime.*
import org.junit.Assert

import java.io.File


fun testSynAndSem(pathname: String, expectedExit: Int) {

    var fail = false
    File(pathname).listFiles().forEach {
        if (it.extension == "wacc") {
            val process = Runtime.getRuntime().exec("./compile ${it.absolutePath}")
            val exitCode = process.waitFor()
            if(exitCode != expectedExit) {
                System.out.println("Failed: ${it.canonicalPath}")
                fail = true
            }
        }
    }
    if (fail) {
        Assert.fail("At least one file failed")
    }
}