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

//region data数据类型
data class Book(
    val id: Long,
    val name: String,
    val author: PersonU
)

data class PersonU(
    val id: Long,
    val name: String,
    val age: String
)

fun funaa() {
    val pair = "Hello" to "Word"
    val (hello, world) = pair
    val book = Book(0, "Kotlin in Action", PersonU(2, "Rain", "20"))
    val (id, name, author) = book

}

//endregion

//region 枚举
enum class State {
    Idle, Busy
}

//定义一个构造器
enum class States(val id: Int) {
    Idle(0), Busy(1)
}

//实现接口
enum class IState : Runnable {
    Idle, Busy {
        override fun run() {//实现自己的
            println("For Busy State ")
        }
    };

    override fun run() {//外部实现
        println("For Every State ")
    }

}

//定义扩展 (获取枚举下一个)
fun State.next(): State {
    return State.values().let {
        val nextOrdinal = (ordinal + 1) % it.size
        it[nextOrdinal]
    }
}

//区间
enum class Color {
    While, Red, Green, Blue, Yellow, Black
}

fun funab() {
    State.Idle.name // Idle
    State.Idle.ordinal // 0
    IState.Idle.run()
    IState.Busy.run()
    val state: State = State.Idle
    when (state) {
        State.Idle -> {
        }
        State.Busy -> {
        }
    }
    val colorRange = Color.Green..Color.Black
    val color = Color.Red
    val color1 = Color.Blue
    color in colorRange // color 是否在 colorRange
    color1 in colorRange // color 是否在 colorRange

}
//endregion

//region 密封类sealed
//一种特殊的抽象类
//密封类的子类定义在与自身相同的文件中
//密封类子类的个数是有限的
//首先是一个抽象类 其次才是密封类
sealed class PlayerState
object Idle : PlayerState()
data class Song(val name: String)
data class ErrorInfo(val error: String)
class Playing(val song: Song) : PlayerState() {
    fun start() {}
    fun stop() {}
}

class Error(val errorInfo: ErrorInfo) : PlayerState() {
    fun recover() {}
}

class Player {
    var state: PlayerState = Idle
    fun play(song: Song) {
        this.state = when (val state = this.state) {
            Idle -> {
                Playing(song).also(Playing::start)
            }
            is Playing -> {
                state.stop()
                Playing(song).also(Playing::start)
            }
            is Error -> {
                state.recover()
                Playing(song).also(Playing::start)
            }
        }
    }
}


//endregion

//region 枚举和密封类差别
//密封类 ：状态实现 子类继承 状态可数 子类可数 状态差异 类型差异
//枚举类 ：状态实现 类实例化 状态可数 实力可数 状态差异 值差异
//endregion

//region 内联类

//内联类是对某一个类型的包装
//内联类是类似于Java装箱类型的一种类型
//编译器会尽可能使用被包装的类型进行优化

//必须是val 包装一个int
inline class BoxInt(val value: Int) : Comparable<Int> {
    operator fun inc(): BoxInt {
        return BoxInt(value + 1)
    }

    override fun compareTo(other: Int): Int = value.compareTo(other)
}

fun fundd() {
    var boxInt = BoxInt(5)
    val newValue = boxInt.value * 200
    println(newValue)
    boxInt++
    println(boxInt)
}
//endregion

//region 内联类模拟枚举
inline class StateEnum(val ordinal: Int) {
    companion object {
        val Idle = StateEnum(0)
        val Busy = StateEnum(1)
    }

    fun values() = arrayOf(Idle, Busy)

}

//编译后拆箱为整型，降低内存开销
inline class RouteTypeInline(val value: Int)

object RouteTypes {
    val WALK = RouteTypeInline(0)
    val BUS = RouteTypeInline(0)
    val CAR = RouteTypeInline(0)
}

fun setRouteType(routeTypeInline: RouteTypeInline) {

}

fun main() {
    setRouteType(RouteTypes.WALK)
}
//内联类的限制
//主构造器必须有且只有一个只读属性
//不能定义有backing-filed的其他属性
//被包装类型必须不能是泛型类型
//不能继承父类也不能被继承
//内联类不能定义为其他类的内部类
//endregion

//region typealias与inline class区别
//typealias  类型 没有新类型 实例 与原类型一致 场景 类型更直观
//inline     类型 有包装类型的产生 实例 必要时使用包装类型 场景 优化包装类型性能
//endregion
