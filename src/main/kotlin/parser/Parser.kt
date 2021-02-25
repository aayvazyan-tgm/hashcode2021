package parser

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Parser {
    fun parse(file: File): Input {
        var line: String
        var splitLine: Array<String>
        // data
        lateinit var simulationMeta: SimulationMeta
        val streets = arrayListOf<Street>()
        val streetHashMap = HashMap<String, Street>()
        val cars = arrayListOf<Car>()
        BufferedReader(FileReader(file)).use { reader ->
            // header line
            // cnt_books, cnt_libraries, days for scanning
            line = reader.readLine()
            splitLine = line.split(" ".toRegex()).toTypedArray()
            simulationMeta = SimulationMeta(
                duation = splitLine[0].toInt()
                , intersectionsCount = splitLine[1].toInt()
                , streetCount = splitLine[2].toInt()
                , carCount = splitLine[3].toInt()
                , bonusPointsPerCarInTime = splitLine[4].toInt()
            )
            for (i in 0 until simulationMeta.streetCount) {
                line = reader.readLine()
                splitLine = line.split(" ".toRegex()).toTypedArray()
                val street = Street(
                    intersectionStart = splitLine[0].toInt(),
                    intersectionEnd = splitLine[1].toInt(),
                    streetName = splitLine[2],
                    travelTime = splitLine[3].toInt()
                )
                streets.add(street)
                streetHashMap.put(street.streetName, street)
            }
            for (i in 0 until simulationMeta.carCount) {
                line = reader.readLine()
                splitLine = line.split(" ".toRegex()).toTypedArray()
                cars.add(Car(
                    streetsToTravel = (1 until splitLine.size).map {
                        streetHashMap[splitLine[it]]!!
                    }
                ))
            }
        }
        return Input(simulationMeta, streets, cars)
    }
}

data class Input(
    val meta: SimulationMeta,
    val street: List<Street>,
    val cars: List<Car>
)
