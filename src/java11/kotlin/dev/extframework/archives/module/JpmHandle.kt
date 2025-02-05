package dev.extframework.archives.module

import dev.extframework.archives.ArchiveHandle
import java.lang.module.Configuration
import java.lang.module.ModuleDescriptor

public class JpmHandle internal constructor(
    public val module: Module,
) : ArchiveHandle {
    override val classloader: ClassLoader = module.classLoader ?: ClassLoader.getSystemClassLoader()
    override val packages: Set<String> = module.packages
    override val parents: Set<ArchiveHandle>
    override val name: String = module.name
    public val configuration: Configuration = module.layer.configuration()
    public val layer: ModuleLayer = module.layer
    init {
        JpmArchives.archives[module.name] = this
        fun ModuleLayer.allModules(): Set<Module> = modules() + parents().flatMap { it.allModules() }
        fun loadArchive(name: String): ArchiveHandle? =
            JpmArchives.nameToArchive[name] ?: module.layer.allModules().find { it.name == name }?.let(::JpmHandle)
        parents = if (module.descriptor.isAutomatic)
            module.layer.allModules().mapTo(HashSet(), JpmArchives::moduleToArchive)
        else module.descriptor.requires()
            .filterNot {
                it.modifiers().contains(ModuleDescriptor.Requires.Modifier.STATIC)
            }.mapTo(HashSet()) {
                loadArchive(it.name())!!
            }
    }
}