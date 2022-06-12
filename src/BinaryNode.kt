class BinaryNode(
    override var data: Int
) : IBSTNode<Int> {

    override var leftNode: BinaryNode? = null
    override var rightNode: BinaryNode? = null

    override val depth: Int
        get() = computeDepth()

    override val size: Int
        get() = computeSize()

    private var parentNode: BinaryNode? = null

    constructor(data: Int, parentNode: BinaryNode) : this(data) {
        this.data = data
        this.parentNode = parentNode
    }

    override fun contains(item: Int): Boolean {
        return if(item == data){
            true
        }else if(item < data){
            leftNode?.contains(item)?:false
        }else{
            rightNode?.contains(item)?:false
        }
    }

    override fun plusAssign(item: Int) {
        if (item < data) {
            //* Lower numbers are stored on the left side
            if (leftNode == null) leftNode = BinaryNode(item, this) else leftNode!! += item
        } else {
            //* Duplicates and higher numbers are stored on the right side
            if (rightNode == null) rightNode = BinaryNode(item, this) else rightNode!! += item
        }
    }

    override fun minusAssign(item: Int) {
        if (data == item) {
            if (leftNode == null && rightNode == null) {
                // has zero child nodes - delete node
                if (data < parentNode!!.data) parentNode!!.leftNode = null
                else parentNode!!.rightNode = null
            } else if (leftNode == null || rightNode == null) {
                // has one child node - replace this.data with data of its child
                data = maxOf(leftNode?.data ?: Int.MIN_VALUE, rightNode?.data ?: Int.MIN_VALUE)
                leftNode = null
                rightNode = null
            } else {
                // has two child nodes - replace this.data with the lowest value node in the right subtree and delete the lowest value node
                var lowestNode = this.rightNode
                while (true) {
                    if (lowestNode?.leftNode != null) lowestNode = lowestNode.leftNode
                    else break
                }
                data = lowestNode!!.data
                lowestNode.parentNode!!.leftNode = null
            }
        } else {
            leftNode?.minusAssign(item)
            rightNode?.minusAssign(item)
        }
    }

    override fun forEachItem(cb: (item: Int) -> Unit) {
        cb(data)
        leftNode?.forEachItem(cb)
        rightNode?.forEachItem(cb)
    }

    private fun computeDepth(): Int {
        //* computes depth of its child nodes, chooses the biggest one and adds 1 to it
        if (leftNode == null && rightNode == null) return 1
        leftNode?.computeDepth()
        rightNode?.computeDepth()
        return maxOf(leftNode?.depth ?: Int.MIN_VALUE, rightNode?.depth ?: Int.MIN_VALUE) + 1
    }

    private fun computeSize(): Int {
        var x = 0
        forEachItem { x++ }
        return x
    }
}

fun main() {
    val binaryNode = BinaryNode(21)
    binaryNode += 12
    binaryNode += 15
    binaryNode += 25
    binaryNode += 23
    binaryNode += 23
    binaryNode += 22

    binaryNode.forEachItem { println(it) }
    println("size: ${binaryNode.size} | depth: ${binaryNode.depth}")

    println(binaryNode.contains(21))
    println(binaryNode.contains(12))
    println(binaryNode.contains(15))
    println(binaryNode.contains(25))
    println(binaryNode.contains(23))
    println(binaryNode.contains(22))
    println(binaryNode.contains(46544855))
    binaryNode -= 21
    binaryNode -= 23
    binaryNode -= 23
    println(binaryNode.contains(21))
    println(binaryNode.contains(23))

    binaryNode.forEachItem { println(it) }
    println("size: ${binaryNode.size} | depth: ${binaryNode.depth}")
}