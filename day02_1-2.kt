
fun main() {

    val inLines = readInput("input02_1")
    //val inLines = readInput("test02_1")

    val lstID = inLines[0].split(",")
    val lstBad = mutableListOf<Long>()
    val lstBad2 = mutableListOf<Long>()

    fun scanForInvalid(id:Long) : MutableList<Long> {
        val lst = mutableListOf<Long>()
        val idStr = id.toString()

        if (idStr.length % 2 == 0) {
            val mid = idStr.length / 2
            val id1 = idStr.take(mid)
            val id2 = idStr.substring(mid)
            if (id1 == id2)
                lst.add(id)
        }
        return lst
    }

    fun scanForInvalid2(id:Long) : MutableList<Long> {
        val lst = mutableListOf<Long>()
        val idStr = id.toString()
        val lg = idStr.length / 2
        for (i in 1.. lg) {
            val lRep = idStr.chunked(i).toMutableList()
            val setPat = mutableSetOf<String>()
            setPat.addAll(lRep)
            if (setPat.size == 1) {
                lst.add(id)
                break
            }
        }
        return lst
    }

    lstID.forEach {
        val firstID = it.substringBefore("-").toLong()
        val lastID = it.substringAfter("-").toLong()
        for (i in firstID .. lastID) {
            val l = scanForInvalid(i)
            lstBad.addAll(l)
            val l2 = scanForInvalid2(i)
            lstBad2.addAll(l2)
        }
    }

    println("Prob_1 : ${lstBad.sum()}")
    println("Prob_2 : ${lstBad2.sum()}")
}
