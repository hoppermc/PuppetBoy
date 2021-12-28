package dev.helight.hopper.puppetboy.versions

import dev.helight.hopper.puppetboy.reflections.Classes
import dev.helight.hopper.puppetboy.reflections.Fields
import dev.helight.hopper.puppetboy.reflections.Methods
import java.lang.reflect.Method

interface Version {

    fun resolveClass(clazz: Classes): String
    fun resolveMethod(method: Methods): String
    fun filterMethod(methods: Methods, method: Method): Boolean
    fun resolveField(field: Fields): String

    val mechanism: Mechanism
    val controllers: Controllers

    fun prepare()

}