package io.github.sschrass.asciidoc.dsl

/**
 * A Title.
 */
open class Title(
    private val level: Int
) : Element {
    protected var value: String? = null

    protected fun prefix() = "=".repeat(level + 1)

    operator fun String.unaryPlus() {
        value = this
    }

    override fun render(builder: StringBuilder) {
        value?.let { builder.append("${prefix()} $it\n") }
    }
}
