
fun main() {

    val inLines = readInput("input05_1")
    //val inLines = readInput("test05_2")
    var rep1 = 0
    var rep2 = 0L

    val lstRangesID = mutableListOf<LongRange>()
    val lstID = mutableListOf<Long>()

    var ligBl = false
    inLines.forEach {
        if (it.isEmpty()) ligBl = true
        if (it.isNotEmpty()) {
            if (!ligBl) {
                val r1 = it.substringBefore("-").toLong()
                val r2 = it.substringAfter("-").toLong()
                val rg = LongRange(r1, r2)
                lstRangesID.add(rg)
            }
            else {
                lstID.add(it.toLong())
            }
        }
    }

    val setRanges = mutableSetOf<LongRange>()
    setRanges.addAll(lstRangesID)
    lstRangesID.clear()
    lstRangesID.addAll(setRanges)

    val setSpoiled = mutableSetOf<Long>()
    lstID.forEach { id ->
        lstRangesID.forEach { it ->
            if (it.contains(id))
                setSpoiled.add(id)
        }
    }
    rep1 = setSpoiled.size
    println("Prob_1 : $rep1")

    var setRgToDrop = mutableSetOf<Int>()
    var setTempoRanges = mutableSetOf<LongRange>()
    var setCleanRanges = mutableSetOf<LongRange>()
    var temoin = true

    while (temoin) {
        temoin = false
        lstRangesID.forEachIndexed { ix, rg ->
            val startRg = rg.first
            val endRg = rg.last
            lstRangesID.forEachIndexed { ixTest, rgTest ->
                if (ixTest != ix) {
                    val startTst = rgTest.first
                    val endTst = rgTest.last

                    if (startTst >= startRg && endTst <= endRg) {
                        setRgToDrop.add(ixTest)
                        temoin = true
                    }
                    else if (startTst >= startRg && startTst <= endRg) {
                        val newRange = LongRange(startRg, endTst)
                        setCleanRanges.add(newRange)
                        setRgToDrop.add(ix)
                        setRgToDrop.add(ixTest)
                        temoin = true
                    }
                    else if (endTst >= startRg && endTst <= endRg) {
                        val newRange = LongRange(startTst, endRg)
                        setCleanRanges.add(newRange)
                        setRgToDrop.add(ix)
                        setRgToDrop.add(ixTest)
                        temoin = true
                    }
                }
            }
        }

        lstRangesID.forEachIndexed { ix, rg ->
            if (!setRgToDrop.contains(ix)) {
                setTempoRanges.add(rg)
            }
        }

        setRgToDrop.clear()
        lstRangesID.clear()
        lstRangesID.addAll(setTempoRanges)
        lstRangesID.addAll(setCleanRanges)
        setTempoRanges.clear()
        setCleanRanges.clear()
    }

    lstRangesID.forEach {
        rep2 += (it.last - it.first) + 1
    }
    println("Prob_2 : $rep2")
}
