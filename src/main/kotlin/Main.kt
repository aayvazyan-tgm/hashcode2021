import solver.Solver
import java.io.File

fun main() {
    Runtime.getRuntime().exec("zipper.bat")
    val files = listOf(
        "a.txt"
        , "b.txt"
        , "c.txt"
        , "d.txt"
        , "e.txt"
        , "f.txt"
    )
    files
//        .parallelStream()
        .forEach {
            println("Starting $it")
            Solver(File("input/$it")).solve(File("output/sol_$it"))
            println("Done with $it")
        }
}
