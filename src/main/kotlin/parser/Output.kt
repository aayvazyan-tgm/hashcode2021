package parser


class Output(
    val intersectionSchedules: List<IntersectionSchedule>
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("${intersectionSchedules.size}\n")
        intersectionSchedules.forEach { ischedule ->
            stringBuilder.append("${ischedule.intersectionId}\n")
            stringBuilder.append("${ischedule.streets.size}\n")
            ischedule.streets.forEach { lightPhase ->
                stringBuilder.append("${lightPhase.street.streetName} ${lightPhase.time}\n")
            }
        }
        return stringBuilder.toString()
    }
}

