package solver

import parser.Street

class Intersection(
    val intersectionId: Int,
    val incomingStreets: MutableList<IntersectionStreet> = mutableListOf(),
    val outgoingStreets: MutableList<Street> = mutableListOf()
)

class IntersectionStreet(
    val street: Street,
    var carsThatWantToPass: Int = 0
)
