class RedBlackNode<T>(var value: T?) : TreePrinter.PrintableNode {
    var left: RedBlackNode<T>? = null
    var right: RedBlackNode<T>? = null
    var parent: RedBlackNode<T>? = null
    var color: RedBlackColor = RedBlackColor.RED

    override fun toString(): String = TreePrinter.print(this)

    override fun getLeft(): TreePrinter.PrintableNode? {
        return left
    }

    override fun getRight(): TreePrinter.PrintableNode? {
        return right
    }

    override fun getText(): String {
        return when (color) {
            RedBlackColor.RED -> "($value)"
            RedBlackColor.BLACK -> "$value"
        }
    }
}