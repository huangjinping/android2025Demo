package con.fire.android2023demo

import con.fire.android2023demo.kt.delegate.getPerson
import con.fire.android2023demo.kt.delegate.login

class MainKt {
    companion object {
        @JvmStatic
        fun start() {
//            YYLogUtils.w("======MainKt=====")
//            val actionImpl = UserActionImpl()
//            UserDelegate1(actionImpl).run {
//                attack()
//                defense()
//            }
//            UserDelegate2(actionImpl).run {
//                attack()
//                defense()
//            }
//
//            UserDelegate3(actionImpl).run {
//                attack()
//                defense()
//            }
//            var textStr by TextDelegate()
//            YYLogUtils.w("textStr:$textStr")
//            textStr = "abc123"
//
//            val personal = TextDelegate.Personal("test", 20)
//            val withResult = with(personal) {
//                println("name = $name")
//                println("age = $age")
//                "$name + $age"
//            }
//            YYLogUtils.w("withResult ==== $withResult")

//            val jake = Person2().also {
//                YYLogUtils.w("=======================0")
//                println(it)
//                YYLogUtils.w("=======================1")
//            }

            getPerson().also {
                it.token?.let { tempToen -> login(tempToen) }
            }


        }

    }
}