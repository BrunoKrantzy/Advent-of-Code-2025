

fun main() {

    val inLines = readInput("input01_1")
    //val inLines = readInput("test01_1")

    var rep = 0

    inLines.forEach {
        var f = it.filter { c -> c.isDigit() }.first()
        var l = it.filter { c -> c.isDigit() }.last()

        var n = ("$f" + "$l").toInt()
        rep += n
    }


    println(rep)
}

