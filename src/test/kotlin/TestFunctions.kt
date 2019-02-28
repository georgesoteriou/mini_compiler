import org.junit.Assert
import java.io.BufferedReader

import java.io.File
import java.io.InputStreamReader


fun testCompile(file: File, expectedExit: Int, expectedOut: String = "") {
    if (file.extension == "wacc") {
        val process = Runtime.getRuntime().exec("./runtest ${file.path} ${file.nameWithoutExtension}")

        val input = BufferedReader(InputStreamReader(process.inputStream))


        val error = BufferedReader(InputStreamReader(process.errorStream))

        var out = ""
        input.lines().forEach { out += "$it\n" }

        var eout = ""
        error.lines().forEach { eout += "$it\n" }

        // check error code
        val exitCode = process.waitFor()
        if(exitCode != expectedExit) {
            println("Error:\n$eout")
            Assert.fail("At ${file.canonicalFile}, wrong exit code.\nExpected: $expectedExit \nBut got: $exitCode")
        }

        if(expectedExit != 200 && expectedExit != 100) {
            // Check output
            if (expectedOut !in out) {
                println("Error:\n$eout")
                Assert.fail("At ${file.canonicalFile}, wrong output.\nExpected:\n$expectedOut \nBut got:\n$out")
            }
        }
        println("TEST PASSED: ${file.path}")

    }
}