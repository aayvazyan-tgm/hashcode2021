package solver

import parser.*
import parser.Output
import java.io.File

class Solver(file: File) {
    val angabe = Parser().parse(file)
    fun solve(outPutFile: File) {
        val solution = internalSolve(angabe)
        OutputWriter(solution).writeOutput(outPutFile)
    }

    fun internalSolve(angabe: Input): Output {
        return Output(
            angabe.street.map { street ->
                IntersectionSchedule(
                    street.intersectionStart,
                    listOf(LightPhase(street, angabe.meta.duation))
                )
            }
        )
    }
}
