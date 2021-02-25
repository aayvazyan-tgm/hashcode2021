package solver

import parser.*
import parser.Output
import java.io.File

class Solver(file: File) {
    val angabe = Parser().parse(file)
    fun solve(outPutFile: File) {
        val solution = internalSolve()
        OutputWriter(solution).writeOutput(outPutFile)
    }

    fun internalSolve(): Output {
        val intersections = calcIntersections()

        return Output(
            intersections.values.map { intersection ->
                IntersectionSchedule(
                    intersection.intersectionId,
                    listOf(
                        LightPhase(
                            intersection.incomingStreets.maxBy { it.carsThatWantToPass }!!.street,
                            angabe.meta.duation
                        )
                    )
                )
            }
        )
    }

    private fun calcIntersections(): HashMap<Int, Intersection> {
        val intersections = hashMapOf<Int, Intersection>()
        angabe.street.forEach { street ->
            intersections.putIfAbsent(
                street.intersectionStart,
                Intersection(street.intersectionStart)
            )
            intersections[street.intersectionStart]!!.outgoingStreets.add(street)
            intersections.putIfAbsent(
                street.intersectionEnd,
                Intersection(street.intersectionEnd)
            )
            intersections[street.intersectionEnd]!!.incomingStreets.add(IntersectionStreet(street))
        }
        //Cars
        angabe.cars.forEach { car ->
            car.streetsToTravel.forEach { street ->
                intersections[street.intersectionEnd]!!
                    .incomingStreets.find { it.street.streetName == street.streetName }!!
                    .carsThatWantToPass++
            }
        }
        return intersections
    }
}
