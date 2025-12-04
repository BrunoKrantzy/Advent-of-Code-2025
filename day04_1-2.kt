
fun main() {

    val inLines = readInput("input04_1")
    //val inLines = readInput("test04_1")

    var rep1 = 0
    var rep2 = 0

    var nbL = inLines.size + 4
    var nbC = inLines[0].length + 4
    val lDep = 2
    val cDep = 2
    var tabGame = Array(nbL) { Array(nbC) { '.' } }

    inLines.forEachIndexed { lx, line ->
        line.forEachIndexed { cx, c ->
            tabGame[lDep+lx][cDep+cx] = c
        }
    }

    fun scanGame(l:Int, c:Int) : Int {
        var nb = 0
        if (tabGame[l-1][c-1] == '@') nb++
        if (tabGame[l-1][c] == '@') nb++
        if (tabGame[l-1][c+1] == '@') nb++
        if (tabGame[l][c-1] == '@') nb++
        if (tabGame[l][c+1] == '@') nb++
        if (tabGame[l+1][c-1] == '@') nb++
        if (tabGame[l+1][c] == '@') nb++
        if (tabGame[l+1][c+1] == '@') nb++
        return nb
    }

    for (l in 0 until nbL) {
        for (c in 0 until nbC) {
            val p = tabGame[l][c]
            if (p == '@') {
                val nbRolls = scanGame(l, c)
                if (nbRolls < 4) {
                    rep1++
                }
            }
        }
    }
    println("Prob_1 : $rep1")

    var temoin = true
    while (temoin) {
        temoin = false
        for (l in 0 until nbL) {
            for (c in 0 until nbC) {
                val p = tabGame[l][c]
                if (p == '@') {
                    val nbRolls = scanGame(l, c)
                    if (nbRolls < 4) {
                        temoin = true
                        rep2++
                        tabGame[l][c] = '.'
                    }
                }
            }
        }
    }
    println("Prob_2 : $rep2")
}
