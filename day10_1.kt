
class Machine(val diagBox: ArrayList<Int>) {
    var buttons = mutableListOf<String>()
    fun buttonsInit(lstB: MutableList<String>) {
        buttons = lstB
    }
    var joltage = ArrayList<Int>()
    fun jolmtageInit(tab: ArrayList<Int>) {
        joltage = tab
    }
}

fun main() {
    val inLines = readInput("input10_1")
    //val inLines = readInput("test10_2")

    val lstMachines = mutableListOf<Machine>()
    // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
    inLines.forEachIndexed { ix, it ->
        val d1 = it.substringAfter('[')
        val diag = d1.substringBefore(']')
        val d2 = it.substringAfter('{')
        val jolt = d2.substringBefore('}')
        val s1 = it.substringAfter("] ")
        val s2 = s1.substringBefore(" {")

        val tabDiag = ArrayList<Int>()
        diag.forEachIndexed { ixv, v ->
            when (v) {
                '.' -> tabDiag.add(0)
                '#' -> tabDiag.add(1)
            }
        }
        val machine = Machine(tabDiag)

        val lstJolt = jolt.splitInts()
        val tabJolt = ArrayList<Int>()
        lstJolt.forEach { v ->
            tabJolt.add(v)
        }
        machine.jolmtageInit(tabJolt)

        val lstB = mutableListOf<String>()
        val pat = "\\(((\\d,?)+)\\)\\s*".toRegex()
        var matchL = pat.find(s2)
        if (matchL != null) {
            while (matchL != null) {
                val tabBut = IntArray(tabDiag.size) { 0 }
                val p = matchL.groups[1]!!.value
                val l = p.splitInts()
                l.forEach { pos ->
                    tabBut[pos] = 1
                }
                val str = StringBuilder()
                tabBut.forEach { car ->
                    str.append(car)
                }
                lstB.add(str.toString())

                matchL = matchL.next()
            }
        }
        machine.buttonsInit(lstB)
        lstMachines.add(machine)
    }

    fun workToBuild(numMachine:Int, diag: String, buttons: MutableList<String>, nbPasses:Int): Int {
        val diag = diag
        val decDiag = diag.toInt(2)
        if (decDiag == 0) return 0
        var isFound = false
        val lstButtons = mutableListOf<Int>()
        var lstWork = mutableListOf<Int>()
        val lstTemp = mutableListOf<Int>()
        var nbButtons = 0

        buttons.forEach { b ->
            val bDec = b.toInt(2)
            lstButtons.add(bDec)
            lstWork.add(bDec)
            nbButtons++
        }

        var noPasse = 0
        for (p in 1 .. nbPasses) {
            noPasse++
            for (but in lstButtons) {
                for (item in lstWork) {
                    val v = but.xor(item)
                    lstTemp.add(v)
                }
            }
            if (lstTemp.contains(decDiag)) {
                isFound = true
                break
            }
            lstWork = lstTemp.toMutableList()
            lstTemp.clear()
        }

        if (noPasse == nbPasses && !isFound)
            println("Erreur sur machine $numMachine : not found")

        return 1 + noPasse
    }

    var rep1 = 0
    var noMachine = 0
    for (machine in lstMachines) {
        noMachine++
        var cpFin = 0
        val diag = machine.diagBox
        val diagToBuild = StringBuilder()
        val buttons = machine.buttons

        diag.forEach {
            diagToBuild.append(it)
        }

        if (buttons.contains(diagToBuild.toString())) {
            // un bouton correspond à la demande
            rep1++
            continue
        }

        val nbPasses = 7
        cpFin = workToBuild(noMachine, diagToBuild.toString(), buttons, nbPasses)
        rep1 += cpFin
    }

    println("Prob_1 : $rep1")
}
