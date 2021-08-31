package net.yakclient.mixins.base.internal.loader

import java.net.URL
import java.net.URLClassLoader

class ExternalJarLoader(
    url: URL,
    parent: ClassLoader = getSystemClassLoader(),
) : URLClassLoader(arrayOf(url), parent), ProxyClassLoader {

    override fun defineClass(name: String, b: ByteArray): Class<*> {
        return this.defineClass(name, b, 0, b.size)
    }

    override fun isDefined(cls: String): Boolean {
        return this.findLoadedClass(cls) != null
    }

    override fun loadClass(name: String): Class<*> {
        return super.loadClass(name)
    }
}