package com.app.a_module_ui.kotlin

import java.io.File
import java.lang.StringBuilder

/**
 * @ClassName ArrayListUtils
 * @Author DYJ
 * @Date 2021/1/16 23:32
 * @Version 1.0
 * @Description 集合变化与序列
 */

// region +集合变化与序列
/*
集合映射
filter 保留满足条件的元素
map 集合中的所有元素--映射到其他元素构成新集合
flapMap 集合中的所有元素--映射到新集合并合并这些集合得到新集合

集合聚合
sum 所有元素求和
reduce 将元素依次按规则聚合，结果与元素类型一致
fold 给定初始化值，将元素按规则聚合，结果与初始化值类型一致

zip

懒序列的机制
 */
fun funList() {
    val list = intArrayOf(1, 2, 3, 4, 5, 6)
    val filter = list.filter { it % 2 == 0 }
    // list.stream().filter java
    val filter1 = list.asSequence().filter { it % 2 == 0 }//转换为懒序列 asSequence 必须有forEach 没有就不会执行
    val map = list.map { it * 2 + 1 }
    val map1 = list.asSequence().map { it * 2 + 1 }
    //list.stream().map() java
    //asSequence 一步一步执行 先filter 如果满足再map 最后输出 必须有forEach 输出
    //没有asSequence 会先执行filter 完成后再执行 map 最后统一输出 立即输出
    list.asSequence().filter {
        println("filter : $it")
        it % 2 == 0
    }.map {
        println("map : $it")
        it * 2 + 1
    }.forEach {
        println("forEach : $it")
    }

    //flatMap
    list.flatMap {
        0 until it
    }.joinToString().let(::println)

    list.asSequence().flatMap {
        (0 until it).asSequence()
    }.joinToString().let(::println)

    //fold
    list.fold(StringBuilder()) { acc, i ->
        acc.append(i)
    }


}
//endregion

//region +SAM转换

//一个参数类型为只有一个方法的Java接口的Java方法调用时可用Lambda表达式做转换为参数

typealias FuncationX = () -> Unit

fun submit(lambda: FuncationX) {

}
//endregion

//region +案例统计字符个数

fun funa() {
    File("build.gradle").readText()//1、读文件
        .toCharArray()//得到一个字符数组
        .filterNot(Char::isWhitespace)//过滤掉空白 isWhitespace filter{!it.isWhitespace()}
        .groupBy { it }//分组
        .map {
            it.key to it.value.size
        }.let {
            println(it)
        }
}

//endregion




fun main() {

}