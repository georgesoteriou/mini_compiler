import org.junit.Assert
import java.io.BufferedReader

import java.io.File
import java.io.InputStreamReader


fun testcompile(file: File, expectedExit: Int, expectedOut: String) {
    if (file.extension == "wacc") {
        val process = Runtime.getRuntime().exec("./runtest ${file.path} ${file.nameWithoutExtension}")

        val input = BufferedReader(InputStreamReader(process.inputStream))


        var error = BufferedReader(InputStreamReader(process.errorStream))

        // check error code
        val exitCode = process.waitFor()
        if(exitCode != expectedExit) {
            Assert.fail("At ${file.canonicalFile}, wrong exit code.\nExpected: $expectedExit \nBut got: $exitCode")
        }

        if(expectedExit != 200 && expectedExit != 100) {
            // Check output
            var out = ""
            input.lines().forEach { out += "$it\n" }
            out = out.dropLast(1)
            if (out != expectedOut) {
                Assert.fail("At ${file.canonicalFile}, wrong output.\nExpected:\n$expectedOut \nBut got:\n$out")
            }
        }

    }
}