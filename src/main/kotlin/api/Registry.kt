package io.github.sweetberrycollective.libdatagen.api

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.Holder
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier

private val data_generator = RegistryKey.ofRegistry<DataGenerator>(Identifier("libdatagen", "data_generators"))
private val registrar = RegistryKey.ofRegistry<Registrar>(Identifier("libdatagen", "registrators"))

object DataGeneratorRegistry : SimpleRegistry<DataGenerator>(data_generator, Lifecycle.stable())
object RegistrarRegistry : SimpleRegistry<Registrar>(registrar, Lifecycle.stable()) {
    override fun register(
        key: RegistryKey<Registrar>,
        entry: Registrar,
        lifecycle: Lifecycle
    ): Holder.Reference<Registrar> {
        entry.register()
        return super.register(key, entry, lifecycle)
    }
}
