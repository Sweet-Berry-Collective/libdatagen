package io.github.sweetberrycollective.libdatagen.impl

import io.github.sweetberrycollective.libdatagen.api.DataGenerator
import io.github.sweetberrycollective.libdatagen.api.DataGeneratorRegistry
import net.minecraft.resource.ResourceType
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.resource.loader.api.InMemoryResourcePack
import org.quiltmc.qsl.resource.loader.api.ResourceLoader
import org.quiltmc.qsl.resource.loader.api.ResourcePackRegistrationContext

val client_pack = InMemoryResourcePack.Named("LibDataGen Client Resources")
val server_pack = InMemoryResourcePack.Named("LibDatagen Server Resources")

fun register(
    context: ResourcePackRegistrationContext,
    pack: InMemoryResourcePack,
    action: DataGenerator.() -> Unit
) {
    pack.clearResources()
    DataGeneratorRegistry.forEach(action)
    context.addResourcePack(pack)
}

@Suppress("UNUSED", "UNUSED_PARAMETER")
fun main(mod: ModContainer) {
    ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerDefaultResourcePackEvent.register { context ->
        register(context, client_pack) {
            client_pack.generateClient(context)
        }
    }
    ResourceLoader.get(ResourceType.SERVER_DATA).registerDefaultResourcePackEvent.register { context ->
        register(context, server_pack) {
            server_pack.generateServer(context)
        }
    }
}
