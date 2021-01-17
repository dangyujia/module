package com.app.a_module_ui.kotlin

/**
 * @ClassName KotlinClass
 * @Author DYJ
 * @Date 2021/1/16 19:09
 * @Version 1.0
 * @Description 为person实现 equals 和 hashCode
 */

class Person(val age: Int, val name: String) {
    override fun equals(other: Any?): Boolean {
        val other = (other as? Person) ?: return false
        return other.age == age && other.name == name
    }

    override fun hashCode(): Int {// 如果是一样的不一定是同一个对象 ，如果不一样那一定不是同一个对象
        return 1 + 7 * age + 13 * name.hashCode()
    }
}

operator fun String.minus(rights: Any?) = this.replaceFirst(rights.toString(), "")

operator fun String.times(rights: Int): String {
    return (1..rights).joinToString("") { this }
}

operator fun String.div(rights: Any?): Int {
    val right = rights.toString()
    return this.windowed(right.length, 1) {
        it == right
    }.count { it }
}

fun main() {

//    val persons = HashSet<Person>()
//    (0..5).forEach { _ ->
//        persons += Person(20, "")
//    }

//    val person = Person(20, "Rain")
//    persons += person
//    println(persons.size)
//
//    val person2 = Person(person.age + 1, person.name)
//
//    persons -= person
//
//    println(persons.size)

    val value = "Hello World World"

//    val star = "*"
//    println("*" * 20)
//    println(value - "World")
    println(value / 3)
    println(value / "ld")

    try {

    }catch (e : Exception){

    }
}