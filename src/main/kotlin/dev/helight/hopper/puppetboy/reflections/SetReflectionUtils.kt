package dev.helight.hopper.puppetboy.reflections

object SetReflectionUtils {

    private val addMethod = java.util.Set::class.java.declaredMethods.first {
        it.name.contains("add")
    }

    private val addAllMethod = java.util.Set::class.java.declaredMethods.first {
        it.name.contains("addAll")
    }

    private val clearMethod = java.util.Set::class.java.declaredMethods.first {
        it.name.contains("clear")
    }

    fun clear(obj: Any) {
        clearMethod.invoke(obj)
    }

    fun add(obj: Any, addition: Any) {
        addMethod.invoke(obj, addition)
    }

    fun replace(obj: Any, contents: Collection<*>) {
        clearMethod.invoke(obj)
        addAllMethod.invoke(contents)
    }

}