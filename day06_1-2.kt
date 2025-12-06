
fun main() {
    val inLines = readInput("input06_1")
    //val inLines = readInput("test06_1")

    val nbL = inLines.size - 1
    val lstValeurs = mutableListOf<MutableList<Long>>()
    val lstOperands = mutableListOf<Char>()

    for (l in 0 until nbL) {
        val it = inLines[l]
        lstValeurs.add(it.splitLongs().toMutableList())
    }
    inLines[nbL].forEach {
        if (!it.isWhitespace())
            lstOperands.add(it)
    }

    var lTab = lstValeurs[0].size
    var cTab = lstValeurs.size
    val tabOperations = Array(lTab) { LongArray(cTab) }

    for (lig in 0 until lstValeurs.size) {
        val  lst = lstValeurs[lig]
        for (j in 0 until lst.size) {
            tabOperations[j][lig] = lst[j]
        }
    }

    var rep1 = 0L
    for (l in 0 until lTab) {
        val ope = lstOperands[l]
        var somL = tabOperations[l][0]
        for (c in 1 until cTab) {
            if (ope == '+')
                somL += tabOperations[l][c]
            else
                somL *= tabOperations[l][c]
        }
        rep1 += somL
    }
    println("Prob_1 : $rep1")


    var rep2 = 0L
    lTab = inLines.size - 1
    cTab = inLines[0].length + 2
    val tabProb2 = Array(lTab) { Array<Char>(cTab) {' '} }
    inLines.forEachIndexed { ixl, it ->
        if (ixl < lTab)
        it.forEachIndexed { ixc, car ->
            tabProb2[ixl][ixc+1] = car
        }
    }

    val lstColSep = mutableListOf<Int>()
    for (c in 0 until cTab) {
        var colSep = true
        for (l in 0 until lTab) {
            if (tabProb2[l][c] != ' ')
                colSep = false
        }
        if (colSep) lstColSep.add(c)
    }

    val lstBlocsOpe = mutableListOf<Pair<Int,Int>>()
    for (sep in 0 until lstColSep.size-1) {
        val p = Pair(lstColSep[sep], lstColSep[sep+1])
        lstBlocsOpe.add(p)
    }

    var numOpe = -1
    lstBlocsOpe.forEach { bloc ->
        val dB = bloc.first+1
        val fB = bloc.second-1
        numOpe++
        val lstNombres = mutableListOf<Long>()
        for (col in dB .. fB) {
            val nombre = StringBuilder()
            for (lig in 0 until lTab) {
                if (tabProb2[lig][col] != ' ')
                    nombre.append(tabProb2[lig][col])
            }
            lstNombres.add(nombre.toString().toLong())
        }

        val ope = lstOperands[numOpe]
        if (ope == '+')
            rep2 += lstNombres.sum()
        else {
            var vNombre = lstNombres[0]
            for (n in 1 until lstNombres.size) {
                vNombre *= lstNombres[n]
            }
            rep2 += vNombre
        }
    }
    println("Prob_2 : $rep2")
}
