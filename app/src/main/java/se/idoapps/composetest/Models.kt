package se.idoapps.composetest

import se.idoapps.annotations.Test

@Test
data class MyDataClassImpl(val tmp: Int)

@Test
interface MyTest {
    fun test()
}

class MyTestImpl: MyTest {
    override fun test() {
        println("Hepp!")
    }
}