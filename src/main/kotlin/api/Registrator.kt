package io.github.sweetberrycollective.libdatagen.api

import net.minecraft.registry.Registry
import org.quiltmc.qkl.library.registry.RegistryAction
import org.quiltmc.qkl.library.registry.invoke

abstract class Registrator(val modid: String) {
    abstract fun register()

    fun <T> withRegistry(registry: Registry<T>, action: RegistryAction<T>.() -> Unit) = registry(modid, action)
}
