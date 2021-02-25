package solver

import parser.*
import parser.Output
import java.io.File
import kotlin.math.max
import kotlin.math.min

class Solver(file: File) {
    val angabe = Parser().parse(file)
    val cycle = min(60, angabe.meta.duation)
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
                    calcLightPhase(intersection)
                )
            }
                .filter { it.lightPhases.isNotEmpty() }
        )
    }

    private fun calcLightPhase(intersection: Intersection): List<LightPhase> {
        val topStreets = intersection.incomingStreets
            .sortedByDescending { it.carsThatWantToPass }
            .filter { it.carsThatWantToPass != 0 }
            .take(cycle)
        val totalCars = topStreets.sumBy { it.carsThatWantToPass }
        if (totalCars == 0) return listOf()
        return topStreets.map {
            val lightPhase = LightPhase(
                it.street,
                max(((it.carsThatWantToPass.toDouble() / totalCars.toDouble()) * cycle).toInt(), 1)
            )
            lightPhase
        }
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
