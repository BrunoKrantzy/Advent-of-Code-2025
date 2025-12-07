
fun main() {
    val inLines = readInput("input07_1")
    //val inLines = readInput("test07_1")

    val nbL = inLines.size
    val nbC = inLines[0].length

    var nbSplitters = 0
    var freeSplitters = 0

    var mapPosInTree = mutableMapOf<Pair<Int, Int>, Char>()
    var lstPosSplitters = mutableListOf<Pair<Int, Int>>()

    var tabGame = Array(nbL) { Array(nbC) { '.'} }

    inLines.forEachIndexed { ixl, lig ->
        lig.forEachIndexed { ixc, car ->
            if (car == '^') {
                nbSplitters++
                lstPosSplitters.add(Pair(ixl, ixc))
            }
            mapPosInTree[Pair(ixl, ixc)] = car
            tabGame[ixl][ixc] = car
        }
    }

    mapPosInTree.forEach {
        val l = it.key.first
        val c = it.key.second
        if (lstPosSplitters.contains(it.key)) {
                tabGame[l][c-1] = '|'
                tabGame[l][c+1] = '|'
                tabGame[l+1][c-1] = '|'
                tabGame[l+1][c+1] = '|'
        }
        else if (l > 0 && tabGame[l - 1][c] == '|') {
            tabGame[l][c] = '|'
        }
    }

    for (l in 0 until nbL) {
        for (c in 0 until nbC) {
            if (tabGame[l][c] == '|')
                mapPosInTree[Pair(l, c)] = '|'
        }
    }

    lstPosSplitters.forEach {
        val l = it.first
        val c = it.second
        if (mapPosInTree[Pair(l - 1, c)] == '.') {
            freeSplitters++
        }
    }

    println("Prob_1 : ${nbSplitters - freeSplitters}")
}

