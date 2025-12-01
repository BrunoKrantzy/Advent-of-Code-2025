
fun main() {

    val inLines = readInput("input01_1")
    //val inLines = readInput("test01_1")

    var dialPos = 50
    var sizeDial = 100
    var repP1 = 0
    var repP2 = 0

    inLines.forEach {
        val dir = it[0]
        val nbDep = it.substring(1).toInt()

        repP2 += nbDep / sizeDial
        if (nbDep % sizeDial > 0 && dialPos != 0) {
            if (dir == 'L' && nbDep % sizeDial >= dialPos)
                repP2++
            else if (dir == 'R' && nbDep % sizeDial + dialPos >= sizeDial)
                repP2++
        }

        if (dir == 'L')
            dialPos = transPose(dialPos, -nbDep, sizeDial)
        else
            dialPos = transPose(dialPos, nbDep, sizeDial)

        if (dialPos == 0)
            repP1++
    }

    println("Prob_1 : $repP1")
    println("Prob_2 : $repP2")
}

