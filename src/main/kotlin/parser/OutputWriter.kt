package parser

import java.io.File

class OutputWriter(val output: Output) {
    fun writeOutput(outPutFile: File) {
        outPutFile.delete()
        outPutFile.createNewFile()
        outPutFile.writeText(output.toString())
    }

}
