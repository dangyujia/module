package com.app.a_module_ui.kotlin

import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * @ClassName DelegateUtils
 * @Author DYJ
 * @Date 2021/1/17 19:13
 * @Version 1.0
 * @Description 代理
 */

/**
 * 代理是什么
 * 接口代理 : 对象X代替当前类A实现接口B的方法 ->我代替你处理它
 * 属性代理 : 对象X代替属性a实现getter/setter方法
 * */

//region 接口代理
class SuperArray<E>(
    private val list: MutableList<E?> = ArrayList(),
    private val map: MutableMap<Any, E> = HashMap()
) : MutableList<E?> by list, MutableMap<Any, E> by map {

    override fun isEmpty(): Boolean = list.isEmpty() && map.isEmpty()
    override val size: Int
        get() = list.size + map.size

    override fun clear() {
        list.clear()
        map.clear()
    }

    override fun set(index: Int, element: E?): E? {
        if (list.size <= index) {
            repeat(index - list.size + 1) {
                list.add(null)
            }
        }
        return list.set(index, element)
    }

    override fun toString(): String {
        return """ List: [$list] ; Map : [$map]"""
    }
}
//endregion

//region 属性代理

//lazy 返回的对象代理了firstName的getter
class Persons(private val name: String) {
    val firstName by lazy { name.split(" ")[0] }
}

//observable
class StateManager {
    var state: Int by Delegates.observable(0) { property, oldValue, newValue ->
        println("State changed from $oldValue -> $newValue")
    }
}

class Foo {
    val x: Int by X(::x)
    var y: Int by X(::y)
}

class X(val property: KProperty<*>) {
    //thisRef: Any?, property: KProperty<*> 固定的
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return 2
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, i: Int) {

    }

}
//vetoable 使用场景
//notNull 的是方法并与lateinit做对比

//endregion

//region 属性代理案例

class PropertiesDelegate(private val path: String, private val defaultValue: String = "") {
    private lateinit var url: URL

    private val properties: Properties by lazy {
        val prop = Properties()
        url = try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
            javaClass.getResource(path)

        } catch (e: Exception) {

            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
                ClassLoader.getSystemClassLoader().getResource(path)

            } catch (e: Exception) {
                FileInputStream(path).use {
                    prop.load(it)
                }
                URL("file://${File(path).canonicalPath}")
            }
        }
        prop
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return properties.getProperty(property.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        //use可以自动关闭
        properties.setProperty(property.name, value)
        File(url.toURI()).outputStream().use {
            properties.store(it, "Hello!!")
        }
    }
}

abstract class AbsProperties(path: String) {
    protected val prop = PropertiesDelegate(path)
}

class Config : AbsProperties("Config.properties") {
    var author by prop
    var version by prop
    var desc by prop

}

fun funz() {
    Thread() {
        val config = Config()
        println(config.author)
        config.author = "RAIN"
        println(config.author)
    }
}
//endregion

fun main() {
//    val superArray = SuperArray<String>()
//    val superArray2 = SuperArray<String>()
//    superArray += "Hello"
//    superArray["Hello"] = "World"
//    superArray2[superArray] = "World"
//    superArray[1] = "world"
//    superArray[4] = "!!!!"
//    println(superArray)
//    println(superArray2)

//    val persons = Persons("哈哈哈")
//    val firstName = persons.firstName
//    val kProperty1 = Persons::firstName
//    val get = kProperty1.get(persons)
//    println(get)

//    val stateManager = StateManager()
//    stateManager.state = 3
//    stateManager.state = 4
//
//    println(Foo().x)


}