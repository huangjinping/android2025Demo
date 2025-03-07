package con.fire.android2023demo.kt.delegate

import kotlin.random.Random
import kotlin.reflect.KProperty

/**
 * https://mp.weixin.qq.com/s/wY8kYYHdRI8v0m5L8IIYSw
 */
interface IUserAction {

    fun attack()

    fun defense()
}

class UserActionImpl : IUserAction {

    override fun attack() {
        YYLogUtils.w("默认操作-开始执行攻击")
    }

    override fun defense() {
        YYLogUtils.w("默认操作-开始执行防御")
    }
}

class UserDelegate1(private val action: IUserAction) : IUserAction {
    override fun attack() {
        YYLogUtils.w("UserDelegate1-需要自己实现攻击")
    }

    override fun defense() {
        YYLogUtils.w("UserDelegate1-需要自己实现防御")
    }
}

class UserDelegate2(private val action: IUserAction) : IUserAction by action


class UserDelegate3(private val action: IUserAction) : IUserAction by action {

    override fun attack() {
        YYLogUtils.w("UserDelegate3 - 只重写了攻击")
    }
}


class TextDelegate {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "我是赋值给与的文本"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        YYLogUtils.w("设置的值为：$value")
    }


    class Personal(var name: String, var age: Int)

}

class Person2(
    var name: String = "test", var age: Int = 0, var about: String? = null
) {
    var token: String? = null
    override fun toString(): String {
        return "Person(name=$name, age=$age, about=$about)"
    }
}

fun login(toke: String): String {
    var result = Random(10).nextLong()
    return "$result"
}

fun getPerson(): Person2 = Person2()