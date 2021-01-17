package com.app.a_module_ui.kotlin

/**
 * @ClassName FunctionUtils
 * @Author DYJ
 * @Date 2021/1/16 19:55
 * @Version 1.0
 * @Description 函数
 */


//region +折叠
/**
 * IntArray 扩展方法
 * action: (Int) -> Unit 接收函数类型作为参数
 */
inline fun IntArray.forEach(action: (Int) -> Unit): Unit {
    for (element in this) action(element) //调用
}

/**
 *  <R> 返回值元素类型
 *  transform: (Int) -> R 接收函数类型作为参数
 *
 */
inline fun <R> IntArray.map(transform: (Int) -> R): List<R> {
    return mapTo(ArrayList<R>(size), transform)//进一步传递transform
}

// endregion

//region + 高阶函数
fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("${System.currentTimeMillis() - start}ms")
}

fun fibonacci(): () -> Long {
    var first = 0L
    var second = 1L
    return {
        val next = first + second
        val current = first
        first = second
        second = next
        current
    }
}
//endregion

//region +内联函数

inline fun cost1(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("${System.currentTimeMillis() - start}ms")
}

// 没有backing-field的属性的getter/setter 可以被内联
class Goods {
    var pocket: Double = 0.0
    var money: Double
        inline get() = pocket
        inline set(value) {
            pocket = value
        }
}

fun func() {
    val ints = intArrayOf(1, 2, 3, 4)
    ints.forEach {
        if (it == 3) return@forEach // 跳出这一次的内联函数调用 等价于 continue
        println("Hello $it")
    }
    for (element in ints) {
        if (element == 3) continue
        println("Hello $element")
    }
}

// region +non-local return 非本地退出
inline fun nonLocalReturn(block: () -> Unit) {
    block()
}

fun fund() {
    nonLocalReturn {
        return // 从外部直接退出
    }
}


//inline fun Runnable(block: () -> Unit): Runnable {
//    return object : Runnable {
//        override fun run() {
//            block() //有可能存在不合法的non-local return ,因为block的调用处与定义处不在同一个调用上下文
//        }
//
//    }
//}
//crossinline 禁止 non-local return
//inline fun Runnable(crossinline block: () -> Unit):Runnable{
//    return object :Runnable{
//        override fun run() {
//            block() //有可能存在不合法的non-local return ,因为block的调用处与定义处不在同一个调用上下文
//        }
//
//    }
//}
//noinline  inline 没有任何意义 禁止函数参数被内联 没有性能上的改善
//inline fun Runnable(noinline block: () -> Unit):Runnable{
//    return object :Runnable{
//        override fun run() {
//            block() //有可能存在不合法的non-local return ,因为block的调用处与定义处不在同一个调用上下文
//        }
//
//    }
//}

// endregion

//public/protected 的内联方法只能访问对应类的public成员
//内联函数的内联函数参数不能被存储（赋值给变量）
//捏脸函数的内联函数只能传递给其他内联函数参数
// inline 内联函数
//高阶函数的性能优化
//函数本身被内联到调用出
//函数的函数参数被内联到调用出

//endregion

fun main() {
    cost {
        val fibonacciNext = fibonacci()
        for (i in 0..10) {
            println(fibonacciNext())
        }
    }

    cost1 {
        println("hello")
    }

    //cost1 使用inline 最后就相当于
//    val start = System.currentTimeMillis()
//    println("hello")
//    println(System.currentTimeMillis() - start)
}