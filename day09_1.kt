import kotlin.math.abs

fun main() {

    //val inLines = readInput("input09_1")
    val inLines = readInput("test09_2")

    val lstPos = mutableListOf<Pair<Long, Long>>()
    inLines.forEach {
        val (col, lig) = it.splitLongs()
        lstPos.add(Pair(col, lig))
    }

    val compAsc = compareBy<Pair<Long, Long>> { it.first }.thenBy { it.second }
    val lstPosAsc = lstPos.sortedWith(compAsc).toMutableList()

    var maxSizeCarre = 0L
    for (i in 0 until lstPosAsc.size) {
        for (j in i+1 until lstPosAsc.size) {
            val p1 = lstPosAsc[i]
            val p2 = lstPosAsc[j]
            val nbL = abs(p1.first - p2.first)
            val nbC = abs(p1.second - p2.second)
            val size = (1 + nbL) * (1 + nbC)
            maxSizeCarre = maxOf(maxSizeCarre, size)
        }
    }

    println("Prob_1 : $maxSizeCarre")
}
