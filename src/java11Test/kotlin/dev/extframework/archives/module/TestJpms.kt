package dev.extframework.archives.module

import dev.extframework.archives.Archives
import org.junit.jupiter.api.Test
import java.net.URLClassLoader
import kotlin.io.path.Path

class TestJpms {

    @Test
    fun `Test JPM load`() {
        val path = Path(this::class.java.classLoader.getResource("log4j-api.jar")!!.path)

        val ref: JpmReference = JpmFinder.find(path)

        val resolved = Archives.resolve(
            ref,
            URLClassLoader(arrayOf(path.toUri().toURL())),
            JpmResolver,
            setOf()
        ).archive

        println("")
    }
}