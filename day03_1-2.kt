
fun main() {

    val inLines = readInput("input03_1")
    //val inLines = readInput("test03_1")

    var rep1 = 0L
    var rep2 = 0L
    var joltStr = StringBuilder()
    var lgBank = inLines[0].length - 1

    inLines.forEach {
        for (i in 9 downTo 1) {
            val first = i.toString()
            if (it.contains(first)) {
                val p = it.indexOf(first)
                if (p < lgBank) {
                    joltStr.append(first)
                    val finBank = it.substring(p+1)
                    val lstFin = finBank.chunked(1).sorted()
                    joltStr.append(lstFin.last())
                    rep1 += joltStr.toString().toLong()
                    joltStr.clear()
                    break
                }
            }
        }
    }

    inLines.forEach {
        var bank = it
        for (j in 11 downTo 0) {
            lgBank = bank.length
            val debStr = bank.take(lgBank-j)
            val lstFin = debStr.chunked(1).sorted()
            val c = lstFin.last()
            joltStr.append(c)
            val cChar = c[0]
            val pos = debStr.indexOfFirst { it == cChar }
            bank = bank.substring(pos+1)
        }
        rep2 += joltStr.toString().toLong()
        joltStr.clear()
    }

    println("Prob_1 : $rep1")
    println("Prob_2 : $rep2")
}

