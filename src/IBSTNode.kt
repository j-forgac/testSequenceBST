interface IBSTNode<T: Comparable<T>> {
    val data: T
    val leftNode: IBSTNode<T>?
    val rightNode: IBSTNode<T>?

    val depth: Int
    val size: Int

    operator fun contains(item: T): Boolean
    operator fun plusAssign(item: T)
    operator fun minusAssign(item: T)

    fun forEachItem(cb: (item: T) -> Unit)
}