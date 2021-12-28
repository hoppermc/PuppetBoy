package dev.helight.hopper.puppetboy

import net.bytebuddy.ByteBuddy
import net.bytebuddy.dynamic.DynamicType
import java.io.File

class PuppetGenerationClassLoader : ClassLoader(PuppetBoy::class.java.classLoader) {

    fun create(block: (ByteBuddy) -> DynamicType.Unloaded<*>): Class<*> {
        val byteBuddy = block(ByteBuddy())
        byteBuddy.toJar(File("GeneratedPuppetWrapperClassImpl.jar"))
        val loadedType = byteBuddy.load(this)
        return loadedType.loaded
    }

}