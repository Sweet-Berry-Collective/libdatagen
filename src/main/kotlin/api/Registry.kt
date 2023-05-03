package io.github.sweetberrycollective.libdatagen.api

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.Holder
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier

private val data_generator = RegistryKey.ofRegistry<DataGenerator>(Identifier("libdatagen", "data_generators"))
private val registrator = RegistryKey.ofRegistry<Registrator>(Identifier("libdatagen", "registrators"))

object DataGeneratorRegistry : SimpleRegistry<DataGenerator>(data_generator, Lifecycle.stable())
object RegistratorRegistry : SimpleRegistry<Registrator>(registrator, Lifecycle.stable()) {
    override fun register(
        key: RegistryKey<Registrator>,
        entry: Registrator,
        lifecycle: Lifecycle
    ): Holder.Reference<Registrator> {
        entry.register()
        return super.register(key, entry, lifecycle)
    }
}
