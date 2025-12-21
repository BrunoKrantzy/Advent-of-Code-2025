
import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)

object RayCasting {
    fun intersects(A: IntArray, B: IntArray, P: DoubleArray): Boolean {
        if (A[1] > B[1])
            return intersects(B, A, P)

        if (P[1] == A[1].toDouble() || P[1] == B[1].toDouble())
            P[1] += 0.0001

        if (P[1] > B[1] || P[1] < A[1] || P[0] >= max(A[0], B[0]))
            return false

        if (P[0] < min(A[0], B[0]))
            return true

        val red = (P[1] - A[1]) / (P[0] - A[0])
        val blue = (B[1] - A[1]) / (B[0] - A[0]).toDouble()
        return red >= blue
    }

    fun contains(shape: ArrayList<IntArray>, pnt: DoubleArray): Boolean {
        var inside = false
        val len = shape.size
        for (i in 0..<len) {
            if (intersects(shape[i], shape[(i + 1) % len], pnt))
                inside = !inside
        }
        return inside
    }
}


fun main() {

    val inLines = readInput("input09_1")
    //val inLines = readInput("test09_1")

    val lstPos = mutableListOf<Point>()
    inLines.forEach {
        val (col, lig) = it.splitInts()
        lstPos.add(Point(lig, col))
    }
    lstPos.add(lstPos.first())

    var polygon = ArrayList<IntArray>()
    lstPos.forEach {
        polygon.add(intArrayOf(it.x, it.y))
    }

    val compAsc = compareBy<Point> { it.x }.thenBy { it.y }
    val lstPosAsc = lstPos.sortedWith(compAsc).toMutableList()

    var maxSizeCarre = 0L
    for (i in 0 until lstPosAsc.size) {
        for (j in i+1 until lstPosAsc.size) {

            if (lstPosAsc[i].x == lstPosAsc[j].x || lstPosAsc[i].y == lstPosAsc[j].y) {
                continue
            }

            var lstP = mutableListOf<Point>()
            var p1 = lstPosAsc[i]
            lstP.add(p1)
            var p3 = lstPosAsc[j]
            lstP.add(p3)
            var p2 = Point(p1.x, p3.y)
            lstP.add(p2)
            var p4 = Point(p3.x, p1.y)
            lstP.add(p4)
            lstP = lstP.sortedWith(compAsc).toMutableList()

            p1 = lstP[0]
            p2 = lstP[1]
            p3 = lstP[3]
            p4 = lstP[2]

            val points = arrayListOf<DoubleArray>()
            val result = mutableListOf<Boolean>()

            // col 1 de p1 à p4
            points.add(doubleArrayOf(p1.x+0.1, p1.y+0.1))
            for (i in (p1.x) + 1 until p4.x) {
                points.add(doubleArrayOf(i.toDouble(), p1.y+0.1))
            }
            points.add(doubleArrayOf(p4.x-0.1, p4.y+0.1))

            // col 2 de p2 à p3
            points.add(doubleArrayOf(p2.x+0.1, p2.y-0.1))
            for (i in (p2.x) + 1 until p3.x) {
                points.add(doubleArrayOf(i.toDouble(), p2.y-0.1))
            }
            points.add(doubleArrayOf(p3.x-0.1, p3.y-0.1))

            // lig 1 de p1 à p2
            points.add(doubleArrayOf(p1.x+0.1, p1.y+0.1))
            for (i in (p1.y) + 1 until p2.y) {
                points.add(doubleArrayOf(p1.x+0.1, i.toDouble()))
            }
            points.add(doubleArrayOf(p1.x+0.1, p2.y-0.1))

            // lig 2 de p4 à p3
            points.add(doubleArrayOf(p4.x-0.1, p4.y+0.1))
            for (i in (p4.y) + 1 until p3.y) {
                points.add(doubleArrayOf(p4.x-0.1, i.toDouble()))
            }
            points.add(doubleArrayOf(p4.x-0.1, p3.y-0.1))

            for (pnt in points) {
                val res = RayCasting.contains(polygon, pnt)
                result.add(res)
                if (!res)
                    break
            }

            if (!result.contains(false)) {
                val nbL = kotlin.math.abs(p1.x - p3.x).toLong()
                val nbC = kotlin.math.abs(p1.y - p3.y).toLong()
                val size = ((1 + nbL) * (1 + nbC))
                maxSizeCarre = maxOf(maxSizeCarre, size)
            }
        }
    }
    
    println("maxSize = $maxSizeCarre")
}

//  1429596008
