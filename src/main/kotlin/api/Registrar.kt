package io.github.sweetberrycollective.libdatagen.api

import net.minecraft.registry.Registry
import org.quiltmc.qkl.library.registry.RegistryAction
import org.quiltmc.qkl.library.registry.invoke

abstract class Registrar(
    /**
     * The modid to register everything under
     * */
    val modid: String
) {
    /**
     * The function in charge of registering everything
     *
     * See [withRegistry] for registering to a specific registry
     * */
    abstract fun register()

    fun <T> withRegistry(registry: Registry<T>, action: RegistryAction<T>.() -> Unit) = registry(modid, action)
}
