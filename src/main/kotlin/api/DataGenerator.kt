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
    /**
     * The modid to register everything under
     * */
    val modid: String
) {
    /**
     * Generates "Client" resources (textures, blockstates, models, ...)
     * */
    abstract fun InMemoryResourcePack.generateClient(context: ResourcePackRegistrationContext)

    /**
     * Generates "Server" resources (lang, loot tables, tags, ...)
     * */
    abstract fun InMemoryResourcePack.generateServer(context: ResourcePackRegistrationContext)

    /**
     * Writes a string to the pack at the given path
     * @param side Must always be the side that you're registering on
     * */
    fun InMemoryResourcePack.writeFile(side: ResourceType, path: Identifier, text: String) {
        putText(side, path, text)
    }

    /**
     * Writes an object to the pack at the given path, must be serializable with kotlinx.serialization
     * @param side Must always be the side that you're registering on
     * */
    inline fun <reified T> InMemoryResourcePack.writeFileTyped(side: ResourceType, path: Identifier, t: T) {
        writeFile(side, path, Json.encodeToString(t))
    }

    /**
     * Reads a string from all resources at the given path
     * */
    fun getFile(context: ResourcePackRegistrationContext, path: Identifier): String? {
        return try {
            String(context.resourceManager().open(path).readAllBytes(), Charset.defaultCharset())
        } catch (err: Throwable) {
            null
        }
    }

    /**
     * Reads an object from all resources at the given path, must be serializable with kotlinx.serialization
     * */
    inline fun <reified T> getFileTyped(context: ResourcePackRegistrationContext, path: Identifier): T? {
        return try {
            Json.decodeFromString((getFile(context, path) ?: return null))
        } catch (err: Throwable) {
            null
        }
    }
}
