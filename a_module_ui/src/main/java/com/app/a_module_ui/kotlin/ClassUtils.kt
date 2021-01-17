package com.app.a_module_ui.kotlin

/**
 * @ClassName ObjectUtils
 * @Author DYJ
 * @Date 2021/1/17 22:30
 * @Version 1.0
 * @Description 类
 */

//region 单例
class Foos {
    @JvmField // 生成非静态的没有getset方法
    var x: Int = 2

    companion object {
        @JvmField //生成静态的Filed
        var y: Int = 2

        @JvmStatic
        fun a() {

        }
    }
}

object ObjectUtils {

    init {

    }

}
//endregion

//region 内部类

class Outer {
    inner class Inner //非静态，实例持有外部类实例引用
    class StaticInner //静态
}

//内部类实例化
fun funq() {
    val inner = Outer().Inner()
    val staticInner = Outer.StaticInner()
}
//runnable & Cloneable
private val runnableCloneable = object : Cloneable, Runnable {
    override fun run() {
    }
}
//endregion