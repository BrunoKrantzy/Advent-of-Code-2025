
fun main() {

    val inLines = readInput("input12_1")
    //val inLines = readInput("test12_1")

    data class Shape(val tabShape: Array<Array<Int>>, val size:Int)
    data class Region(val larg:Int, val long:Int, val tabNumP:Array<Int>)

    val lstShapes = mutableListOf<Shape>()
    val lstRegions = mutableListOf<Region>()

    var numL = -1
    for (s in 0 .. 5) {
        numL += 2
        val tabShape = Array(3) { Array(3) { -1 } }
        var nv = 0
        for (l in 0 until 3) {
            val ligne = inLines[numL]
            ligne.forEachIndexed { ix, car ->
                if (car == '#') {
                    tabShape[l][ix] = s
                    nv++
                }
            }
            numL ++
        }
        val shape = Shape(tabShape, nv)
        lstShapes.add(shape)
    }

    numL++
    for (l in numL until inLines.size) {
        var lig = inLines[l]
        val larg = lig.substringBefore('x').toInt()
        val l1 = lig.substringAfter('x')
        val long = l1.substringBefore(':').toInt()
        val s1 = lig.substringAfter(": ")
        var tab = Array(6) { -1 }
        val lstN = s1.splitInts()
        lstN.forEachIndexed { ix, c ->
            tab[ix] = c
        }
        val r = Region(larg, long, tab)
        lstRegions.add(r)
    }

    var rep1 = 0
    lstRegions.forEachIndexed { ixR, region ->
        val size = region.larg * region.long
        var totalMin = 0
        var totalMax = 0
        region.tabNumP.forEachIndexed { ix, num ->
            if (num != 0) {
                val shape = lstShapes[ix]
                totalMin += shape.size * num
                totalMax += 9 * num
            }
        }

        if (size >= totalMax) {
            rep1++
        }
        else if (size >= totalMin) {
            rep1++
            println("dans le doute pourquoi pas...")
        }
    }

    println("\nProb_1 : $rep1")
}
