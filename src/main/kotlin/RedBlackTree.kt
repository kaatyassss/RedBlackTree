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
        var gparent: RedBlackNode<T>? = null // Определяем родительский узел и дедушку
        // Пока родительский узел существует, и цвет родительского узла красный
        while (node?.parent != null && isRed(parent)) {
            if (parent != null) gparent = parent.parent // Получаем дедушку

            // Если родительский узел является левым дочерним узлом дедушкиного узла
            if (parent == gparent?.left) {
                val uncle: RedBlackNode<T>? = gparent?.right // Получаем узел дяди
                // case1: узел дяди тоже красный
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent) // Делаем черными родительский узел и узел дяди
                    setBlack(uncle)
                    setRed(gparent) // Закрашиваем дедушку в красный цвет
                    node = gparent // Помещаем позицию на дедушку узла
                    continue  // Продолжить while
                }
                // case2: узел-дядя черный, а текущий узел - левый дочерний узел
                if (node == parent?.left) {
                    setBlack(parent)
                    setRed(gparent)
                    rightRotate(gparent)
                }
                // case3: узел-дядя черный, а текущий узел - правый дочерний узел
                if (node == parent?.right) {
                    leftRotate(parent) // Повернуть влево от родительского узла
                    val tmp: RedBlackNode<T> = parent // Затем меняем местами родительский узел и себя, чтобы подготовиться к следующему правому вращению
                    parent = node
                    node = tmp
                }
            } else {
                val uncle: RedBlackNode<T>? = gparent?.left // Получаем узел дяди
                // case1: узел дяди тоже красный
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent)
                    setBlack(uncle)
                    setRed(gparent)
                    node = gparent
                    continue
                }
                // case4: узел-дядя черный, а текущий узел - правый дочерний узел
                if (node == parent?.right) {
                    setBlack(parent)
                    setRed(gparent)
                    leftRotate(gparent)
                }
                // case5: узел-дядя черный, а текущий узел - левый дочерний узел
                if (node == parent?.left) {
                    rightRotate(parent)
                    val tmp: RedBlackNode<T> = parent
                    parent = node
                    node = tmp
                }
            }
        }
        // Устанавливаем корневой узел в черный цвет
        setBlack(this.root)
    }

    /**   p                       p
    *    /                       /
    *   x                       y
    *  / \                     / \
    * lx  y      ----->       x  ry
    *    / \                 / \
    *   ly ry               lx ly
    **/
    private fun leftRotate(x: RedBlackNode<T>?) {
        // 1. Назначаем левый дочерний узел y правому дочернему узлу x и назначаем x родительскому узлу левого дочернего узла y (когда левый дочерний узел y не пуст)
        val y: RedBlackNode<T> = x?.right!!
        x.right = y.left
        if (y.left != null) y.left?.parent = x

        // 2. Назначаем родительский узел p элемента x (если он не пустой) родительскому узлу y и обновляем дочерний узел p до y (слева или справа)
        y.parent = x.parent
        if (x.parent == null) {
            this.root = y // Если родительский узел x пуст, устанавливаем y как родительский узел
        } else {
            if (x == x.parent?.left) // Если x левый дочерний узел
                x.parent?.left = y // Затем также устанавливаем y как левый дочерний узел
            else x.parent?.right = y // в противном случае устанавливаем y как правый дочерний узел
        }

        // 3. Устанавливаем левый дочерний узел y на x, а родительский узел x на y
        y.left = x
        x.parent = y
    }

    /**      p                   p
    *       /                   /
    *      y                   x
    *     / \                 / \
    *    x  ry   ----->      lx  y
    *   / \                     / \
    * lx  rx                   rx ry
    **/
    private fun rightRotate(y: RedBlackNode<T>?) {
        // 1. Назначаем левый дочерний узел y правому дочернему узлу x и назначаем x родительскому узлу левого дочернего узла y (когда левый дочерний узел y не пуст)
        val x: RedBlackNode<T> = y?.left!!
        y.left = x.right
        if (x.right != null) x.right?.parent = y

        // 2. Назначаем родительский узел p элемента x (если он не пуст) родительскому узлу y и обновляем дочерний узел p до y (слева или справа)
        x.parent = y.parent
        if (y.parent == null) {
            this.root = x // Если родительский узел x пуст, устанавливаем y как родительский узел
        } else {
            if (y === y.parent?.right) // Если x левый дочерний узел
                y.parent?.right = x // Затем также устанавливаем y как левый дочерний узел
            else y.parent?.left = x // в противном случае устанавливаем y как правый дочерний узел
        }

        // 3. Устанавливаем левый дочерний узел y на x, а родительский узел x на y
        x.right = y
        y.parent = x
    }
}