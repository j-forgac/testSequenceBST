val primeSequence = sequence {
    val primeNums: MutableList<Int> = mutableListOf()
    var num = 2
    while (true){
        if(primeNums.none { num%it == 0}){
            primeNums.add(num)
            yield(num)
        }
        num++
    }
}

fun main() {
    println(primeSequence.take(100).toList())
    println(primeSequence.takeWhile { it<100 }.toList())
}