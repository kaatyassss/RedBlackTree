fun main() {
    val tree = RedBlackTree<Int>()
    val list = listOf(5, 3, 2, 3, 1, 7, 6)
    for (x in list) {
        tree.insert(x)
    }
    println(tree)
}