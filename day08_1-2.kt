import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Box(val x:Int, val y:Int, val z:Int)

class Circuit() {
    var setBoxes = mutableSetOf<Box>()
    fun ajout (b:Box) { setBoxes.add(b) }
    fun getSetSize() : Int { return setBoxes.size }
}

fun main() {
    val inLines = readInput("input08_1")
    //val inLines = readInput("test08_1")

    //val toTake = 1000 // pour le prob. 1
    val inProb2 = true // pour faire le prob. 2
    val toTake = 5674 // 29 pour le test

    fun distBoxes(a:Box, b:Box) : Double {
        val dX = abs(a.x - b.x).toDouble().pow(2)
        val dY = abs(a.y - b.y).toDouble().pow(2)
        val dZ = abs(a.z - b.z).toDouble().pow(2)
        val dist = sqrt(dX + dY + dZ)
        return dist
    }

    val lstBoxes = mutableListOf<Box>()
    inLines.forEach {
        val (x, y, z) = it.splitInts()
        val b = Box(x, y, z)
        lstBoxes.add(b)
    }

    val mapCouples = mutableMapOf<Pair<Box, Box>, Double>()
    val lstConnus = mutableMapOf<Pair<Int, Int>, Int>()

    for (i in 0 until lstBoxes.size) {
        val aBox = lstBoxes[i]
        for (j in i+1 until lstBoxes.size) {
            if (j != i && !lstConnus.containsKey(Pair(i, j))  && !lstConnus.containsKey(Pair(j, i))) {
                val bBox = lstBoxes[j]
                val dist = distBoxes(aBox, bBox)
                mapCouples[Pair(aBox, bBox)] = dist
                lstConnus[(Pair(i, j))] = 0
                lstConnus[(Pair(j, i))] = 0
            }
        }
    }

    val tList = mapCouples.toList().sortedBy { (_, value) -> value} as MutableList
    val wList = mutableListOf<Pair<Box, Box>>()
    tList.forEach {
        val p = it.first
        wList.add(p)
    }
    var workList = wList.take(toTake)
    val v1 = workList.last().first.x.toLong()
    val v2 = workList.last().second.x.toLong()
    println("Prob_2 : ${v1 * v2}")

    if (inProb2) return

    val boxesInWork = mutableSetOf<Box>()
    boxesInWork.addAll(lstBoxes)

    val lstCircuits = mutableListOf<Circuit>()
    val bb1 = workList[0].first
    val bb2 = workList[0].second
    val c = Circuit()
    c.ajout(bb1)
    c.ajout(bb2)
    lstCircuits.add(c)
    boxesInWork.remove(bb1)
    boxesInWork.remove(bb2)
    workList = workList.drop(1)
    val listeRestante = mutableListOf<Pair<Box, Box>>()
    listeRestante.addAll(workList)

    var temoin = true
    while (listeRestante.isNotEmpty()) {
        lstCircuits.forEach { circuit ->
            temoin = false
            workList.forEach { w->
                val b1 = w.first
                val b2 = w.second
                if (boxesInWork.contains(b1) || boxesInWork.contains(b2)) {
                    if (circuit.setBoxes.contains(b1)) {
                        circuit.ajout(b2)
                        boxesInWork.remove(b2)
                        temoin = true
                        listeRestante.remove(w)
                    }
                    else if (circuit.setBoxes.contains(b2)) {
                        circuit.ajout(b1)
                        boxesInWork.remove(b1)
                        temoin = true
                        listeRestante.remove(w)
                    }
                }
            }
        }
        if (!temoin) {
            reprise@for (w in 0 until listeRestante.size) {
                val work = listeRestante[w]
                val b1 = work.first
                val b2 = work.second
                for (cir in 0 until lstCircuits.size) {
                    val cc = lstCircuits[cir]
                    if (cc.setBoxes.contains(b1) || cc.setBoxes.contains(b2)) {
                        continue@reprise
                    }
                }
                val c = Circuit()
                c.ajout(work.first)
                c.ajout(work.second)
                lstCircuits.add(c)
                boxesInWork.remove(work.first)
                boxesInWork.remove(work.second)
                listeRestante.remove(work)
                break
            }
        }

        val lstToDrop = mutableListOf<Pair<Box, Box>>()
        listeRestante.forEach {
            val b1 = it.first
            val b2 = it.second
            if (!boxesInWork.contains(b1) && !boxesInWork.contains(b2))
                lstToDrop.add(it)
        }
        lstToDrop.forEach {
            listeRestante.remove(it)
        }
    }

    while (boxesInWork.isNotEmpty()) {
        val box = boxesInWork.first()
        val c = Circuit()
        c.ajout(box)
        lstCircuits.add(c)
        boxesInWork.remove(box)
    }

    val lstSetSizes = mutableListOf<Int>()
    lstCircuits.forEach {
        lstSetSizes.add(it.getSetSize())
    }
    lstSetSizes.sortDescending()

    var rep1 = lstSetSizes[0].toLong()
    for (i in 1 .. 2) {
        val siz = lstSetSizes[i].toLong()
        rep1 *= siz
    }

    println("Prob_1 : $rep1")
}
