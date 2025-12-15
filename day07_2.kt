
fun main() {
    val inLines = readInput("input07_1").toMutableList()
    //val inLines = readInput("test07_1").toMutableList()
    inLines.removeLast()

    val nbL = inLines.size
    val nbC = inLines[0].length

    var lstPosSplitters = mutableListOf<Pair<Int, Int>>()
    var mapPosInTree = mutableMapOf<Pair<Int, Int>, Char>()

    val mapCodes = mutableMapOf<String, MutableList<String>>()
    var start = ""

    var tabGame = Array(nbL) { Array(nbC) { '.'} }

    inLines.forEachIndexed { ixl, lig ->
        lig.forEachIndexed { ixc, car ->
            mapPosInTree[Pair(ixl, ixc)] = car
            if (car == '^') {
                lstPosSplitters.add(Pair(ixl, ixc))
                tabGame[ixl][ixc] = '^'
            }
            else if (car == 'S') {
                start = "L${ixl}C${ixc}"
                tabGame[ixl][ixc] = '@'
                tabGame[ixl+1][ixc] = '|'
            }
            else if (tabGame[ixl][ixc] != '|')
                tabGame[ixl][ixc] = car
        }
    }

    mapPosInTree.forEach {
        val l = it.key.first
        val c = it.key.second
        if (lstPosSplitters.contains(it.key)) {
            tabGame[l][c-1] = '|'
            tabGame[l][c+1] = '|'
            if (l < nbL-1) {
                tabGame[l+1][c-1] = '|'
                tabGame[l+1][c+1] = '|'
            }
        }
        else if (l > 0 && tabGame[l - 1][c] == '|') {
            tabGame[l][c] = '|'
        }
    }

    for (l in 0 until nbL) {
        for (c in 1 until nbC-1) {
            if (tabGame[l][c] == '^') {
                mapCodes["L${l}C${c-1}"] = mutableListOf<String>()
                mapCodes["L${l}C${c+1}"] = mutableListOf<String>()
                tabGame[l][c-1] = '@'
                tabGame[l][c+1] = '@'
            }
            else if (l == 0 && tabGame[l][c] == '@') {
                tabGame[l][c] = '@'
                mapCodes["L${l}C${c}"] = mutableListOf<String>()
            }
            else if (l == nbL-1 && tabGame[l][c] == '|') {
                tabGame[l][c] = '@'
                mapCodes["L${l}C${c}"] = mutableListOf<String>()
            }
        }
    }

    for (l in 0 until nbL-2) {
        for (c in 1 until nbC) {
            if (tabGame[l][c] == '@') {
                var numl = l
                while (true) {
                    numl++
                    val car = tabGame[numl][c]
                    if (car == '^') {
                        mapCodes["L${l}C${c}"]!!.add("L${numl}C${c-1}")
                        mapCodes["L${l}C${c}"]!!.add("L${numl}C${c+1}")
                        break
                    }
                    else if (car == '@') {
                        mapCodes["L${l}C${c}"]!!.add("L${numl}C${c}")
                        break
                    }
                }
            }
        }
    }

    val lstOut = mutableListOf<String>()
    for (l in nbL-1 .. nbL-1) {
        for (c in 0 until nbC) {
            if (tabGame[l][c] == '@') {
                lstOut.add("L${l}C${c}")
            }
        }
    }

    fun dfs(source: String, target: String, memory: MutableMap<String, Long> = mutableMapOf()): Long {
        if (source == target)
            return 1L
        else {
            return memory.getOrPut(source) {
                mapCodes[source]?.sumOf { next ->
                    dfs(next, target, memory)} ?: 0L
            }
        }
    }

    var totalPaths = 0L
    lstOut.forEach { out ->
        val allPaths = dfs(start, out)
        println("out : $out = $allPaths")
        totalPaths += allPaths
    }

    println("$totalPaths")
}
