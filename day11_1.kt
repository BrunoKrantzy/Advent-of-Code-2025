
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.graph.SimpleDirectedGraph

fun main() {
    val inLines = readInput("input11_1")
    //val inLines = readInput("test11_1")

    val patLine = "([a-z]+): ([[a-z]+\\s?]+)".toRegex()
    val mapCodes = mutableMapOf<String, MutableList<String>>()

    inLines.forEach {
        var premCode = ""
        val match = patLine.find(it)
        if (match != null) {
            premCode = match.groups[1]!!.value
            val suite = match.groups[2]!!.value
            val lstSuite = suite.split(" ") as MutableList

            if (!mapCodes.containsKey(premCode)) {
                mapCodes[premCode] = mutableListOf()
            }

            lstSuite.forEach { code ->
                mapCodes[premCode]!!.add(code)
            }
        }
    }
    mapCodes["out"] = mutableListOf()

    fun createStringGraph(): Graph<String, DefaultEdge> {
        val g: Graph<String, DefaultEdge> = SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge::class.java)
        mapCodes.forEach { key ->
            g.addVertex(key.key)
        }
        mapCodes.forEach {
            val v1 = it.key
            for (v2 in it.value) {
                g.addEdge(v1, v2)
            }
        }
        return g
    }

    val graph = createStringGraph()
    val paths = AllDirectedPaths(graph)
    val allPaths = paths.getAllPaths("you", "out", true, 1000)

    println("Prob_1 : ${allPaths.size}")
}
