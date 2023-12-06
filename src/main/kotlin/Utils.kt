/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = object {}.javaClass.classLoader.getResource("$name.txt")
    ?.readText()
    ?.lines()!!

