import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.math.absoluteValue

private fun readChars() = readLn().toCharArray()
private fun readLn() = readln() // string line
private fun readSb() = StringBuilder(readLn())
private fun readInt() = readLn().toInt() // single int
private fun readLong() = readLn().toLong() // single long
private fun readBigInt() = readLn().toBigInteger() // single BigInteger
private fun readDouble() = readLn().toDouble() // single double
private fun readStrings() = readLn().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() } // list of ints
private fun readLongs() = readStrings().map { it.toLong() } // list of longs
private fun readBigInts() = readStrings().map { it.toBigInteger() } // list of BigIntegers
private fun readDoubles() = readStrings().map { it.toDouble() } // list of doubles
private fun readIntArray() = readStrings().map { it.toInt() }.toIntArray() // Array of ints
private fun readLongArray() = readStrings().map { it.toLong() }.toLongArray() // Array of longs
private fun readMutableMap() = readLn().groupingBy { it }.eachCount().toMutableMap() // map char/Int occur.


fun readInput(name: String) = File("src", "$name.txt").readLines()

class MutableStack<E>(vararg items: E) {
    private val elements = items.toMutableList()
    fun push(element: E) = elements.add(element)
    fun peek(): E = elements.last()
    fun pop(): E = elements.removeAt(elements.size - 1)
    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    override fun toString() = elements.joinToString(" ")
}

data class Directory (val nomDir: Int, var parent: Int, var tailleDir:Long)


/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.md5pad() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


val NUM_REGEX = "-?[0-9]*\\.?[0-9]*".toRegex()
fun String.splitNumbers(): List<Number> =
    NUM_REGEX.findAll(this).filter { it.value.isNotEmpty() }.map { if ("." in it.value) it.value.toDouble() else it.value.toInt() }.toList()

val INT_REGEX = "-?[0-9]*".toRegex()
fun String.splitInts(): List<Int> =
    INT_REGEX.findAll(this).filter { it.value.isNotEmpty() }.map { it.value.toInt() }.toList()

fun String.splitLongs(): List<Long> =
    INT_REGEX.findAll(this).filter { it.value.isNotEmpty() }.map { it.value.toLong() }.toList()


// BFS grid
fun possiblePath(n: Int, m: Int, grid: Array<IntArray>): Int {
    // Check if the source or destination cell is blocked
    if (grid[0][0] == 1 || grid[n - 1][m - 1] == 1) {
        // Return -1 to indicate no path
        return -1
    }

    // Create a queue to store the cells to explore
    val q: Queue<IntArray> = LinkedList()

    // Add the source cell to the queue and mark its distance as 0
    q.add(intArrayOf(0, 0))

    // Define two arrays to represent the four directions of movement
    val dx = intArrayOf(-1, 0, 1, 0)
    val dy = intArrayOf(0, 1, 0, -1)

    // Create a 2D array to store the distance of each cell
    // from the source
    val dis = Array(n) { IntArray(m) }
    for (i in 0..<n) {
        Arrays.fill(dis[i], -1)
    }

    // Set the distance of the source cell as 0
    dis[0][0] = 0

    // Loop until the queue is empty or the destination is reached
    while (!q.isEmpty()) {
        // Get the front cell from the queue and remove it
        val p = q.poll()
        val x = p[0]
        val y = p[1]

        // Loop through the four directions of movement
        for (i in 0..3) {
            // Calculate the coordinates of the neighboring cell
            val xx = x + dx[i]
            val yy = y + dy[i]

            // Check if the neighboring cell is inside the grid
            // and not visited before
            if (xx >= 0 && xx < n && yy >= 0 && yy < m && dis[xx][yy] == -1) {
                // Check if the neighboring cell is free or special
                if (grid[xx][yy] == 0 || grid[xx][yy] == 2) {
                    // Set the distance of the neighboring cell as one
                    // more than the current cell
                    dis[xx][yy] = dis[x][y] + 1

                    // Add the neighboring cell to the queue for
                    // further exploration
                    q.add(intArrayOf(xx, yy))
                }

                // Check if the neighboring cell is special
                if (grid[xx][yy] == 2) {
                    // Loop through the four directions of movement again
                    for (j in 0..3) {
                        // Calculate the coordinates of the adjacent cell
                        val xxx = xx + dx[j]
                        val yyy = yy + dy[j]

                        // Check if the adjacent cell is inside the grid
                        if (xxx >= 0 && xxx < n && yyy >= 0 && yyy < m) {
                            // Check if the adjacent cell is blocked
                            if (grid[xxx][yyy] == 1) {
                                // Change the adjacent cell to free
                                grid[xxx][yyy] = 0
                            }
                        }
                    }
                }
            }
        }
    }

    // Return the distance of the destination cell from the source
    return dis[n - 1][m - 1]
}


data class Point2D(val x: Int, val y: Int) {

    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            this + NORTH,
            this + EAST,
            this + SOUTH,
            this + WEST
        )

    fun distanceTo(other: Point2D): Int =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue

    operator fun div(by: Int): Point2D =
        Point2D(x / by, y / by)

    operator fun minus(other: Point2D): Point2D =
        Point2D(x - other.x, y - other.y)

    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    operator fun times(times: Int): Point2D =
        Point2D(x * times, y * times)

    companion object {
        val ORIGIN = Point2D(0, 0)
        val NORTH = Point2D(0, -1)
        val EAST = Point2D(1, 0)
        val SOUTH = Point2D(0, 1)
        val WEST = Point2D(-1, 0)

        fun of(input: String): Point2D =
            input.split(",").let {
                Point2D(it.first().toInt(), it.last().toInt())
            }
    }
}

// Ctrl + Space -> code completion
// Ctrl + P -> paramètres

// val tab2D: Array<Array<Int>> = Array (5) { Array(3) { 0 } }

// val tabA = IntArray(100) { it + 1 }

// val dp = Array(100 + 1) { Array(100 + 1) { BooleanArray(10000 + 1) } }

// val tabI = readInts().toIntArray()
// val tabC = readLn().toCharArray()

// val liste = readLn().toList().sorted()

// tableau 2D de n Strings
// var matrix = Array(n) { readLn() }

// var tabListes: Array<MutableList<Int>> = Array (m) { mutableListOf() }

// var tabDataClasse: Array<DataClasse> = Array (5) { DataClasse(0, 0, 0) }

// pour une lecture d'éléments à indexer
// val (l, r) = readLn().map { it.toInt() - 1 }

// pour une lecture d'éléments à indexer avec abandon de la première valeur lue
// val days = readInts().drop(1).map { it - 1 } as MutableList<Int>

// println(list.joinToString("\n")) // each element of array/list with separate line
// println(list.joinToString(" "))  // each element of array/list with separate space

// un StringBuilder java qui prévoit le séparateur
// val sj = StringJoiner(" ")

// utiliser asSequence lors de traitement de données
// (1 until 2_000_000).asSequence()
// .map { un traitement }
// .filter { un filtrage }
// .first()

// val MOD = 1_000_000_007

// private fun Int.pow(x: Int) = toDouble().pow(x).toInt()
// private fun Long.pow(x: Int) = toDouble().pow(x).toLong()

