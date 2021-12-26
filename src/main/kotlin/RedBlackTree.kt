class RedBlackTree<T : Comparable<T>> {
    private var root: RedBlackNode<T>? = null

    override fun toString(): String = root?.toString() ?: "empty tree"

    private fun isRed(node: RedBlackNode<T>?): Boolean {
        return node?.color == RedBlackColor.RED
    }
    private fun setRed(node: RedBlackNode<T>?) {
        if (node != null) node.color = RedBlackColor.RED
    }
    private fun setBlack(node: RedBlackNode<T>?) {
        if (node != null) node.color = RedBlackColor.BLACK
    }


    fun insert(value: T) {
        insert(RedBlackNode(value))
    }

    private fun insert(node: RedBlackNode<T>): RedBlackNode<T> {
        var parent: RedBlackNode<T>? = null
        var x = root

        while (x != null) {
            parent = x
            x = if (node.value!! < x.value!!) x.left else x.right
        }
        node.parent = parent
        if (parent == null) {
            root = node
        } else if (node.value!! < parent.value!!) {
            parent.left = node
        } else parent.right = node

        insertFixup(node)
        return node
    }

    private fun insertFixup(nodeToFix: RedBlackNode<T>?) {
        var node: RedBlackNode<T>? = nodeToFix
        var parent: RedBlackNode<T>? = node?.parent
        var gparent: RedBlackNode<T>? = null
        while (node?.parent != null && isRed(parent)) {
            if (parent != null) gparent = parent.parent
            if (parent == gparent?.left) {
                val uncle: RedBlackNode<T>? = gparent?.right

                if (uncle != null && isRed(uncle)) {
                    setBlack(parent)
                    setBlack(uncle)
                    setRed(gparent)
                    node = gparent
                    continue
                }

                if (node == parent?.left) {
                    setBlack(parent)
                    setRed(gparent)
                    rightRotate(gparent)
                }

                if (node == parent?.right) {
                    leftRotate(parent)
                    val tmp: RedBlackNode<T> = parent
                    parent = node
                    node = tmp
                }
            } else {
                val uncle: RedBlackNode<T>? = gparent?.left

                if (uncle != null && isRed(uncle)) {
                    setBlack(parent)
                    setBlack(uncle)
                    setRed(gparent)
                    node = gparent
                    continue
                }

                if (node == parent?.right) {
                    setBlack(parent)
                    setRed(gparent)
                    leftRotate(gparent)
                }

                if (node == parent?.left) {
                    rightRotate(parent)
                    val tmp: RedBlackNode<T> = parent
                    parent = node
                    node = tmp
                }
            }
        }
        setBlack(this.root)
    }

    private fun leftRotate(x: RedBlackNode<T>?) {
        val y: RedBlackNode<T> = x?.right!!
        x.right = y.left
        if (y.left != null) y.left?.parent = x

        y.parent = x.parent
        if (x.parent == null) {
            this.root = y
        } else {
            if (x == x.parent?.left)
                x.parent?.left = y
            else x.parent?.right = y
        }


        y.left = x
        x.parent = y
    }


    private fun rightRotate(y: RedBlackNode<T>?) {

        val x: RedBlackNode<T> = y?.left!!
        y.left = x.right
        if (x.right != null) x.right?.parent = y


        x.parent = y.parent
        if (y.parent == null) {
            this.root = x
        } else {
            if (y === y.parent?.right)
                y.parent?.right = x
            else y.parent?.left = x
        }

        x.right = y
        y.parent = x
    }
}