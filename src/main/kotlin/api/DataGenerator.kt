package io.github.sweetberrycollective.libdatagen.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.quiltmc.qsl.resource.loader.api.InMemoryResourcePack
import org.quiltmc.qsl.resource.loader.api.ResourcePackRegistrationContext
import java.nio.charset.Charset

abstract class DataGenerator(
    val context: ResourcePackRegistrationContext,
    val modid: String
) {
    abstract fun generate(pack: InMemoryResourcePack)

    inline fun <reified T> writeFile(pack: InMemoryResourcePack, side: ResourceType, name: String, t: T) {
        pack.putText(side, Identifier(modid, name), Json.encodeToString(t))
    }

    inline fun <reified T> getFile(path: Identifier): T? {
        return try {
            val json = String(context.resourceManager().open(path).readAllBytes(), Charset.defaultCharset())
            Json.decodeFromString(json)
        } catch (err: Throwable) {
            null
        }
    }
}
