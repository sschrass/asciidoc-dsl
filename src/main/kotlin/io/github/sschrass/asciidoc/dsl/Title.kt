package io.github.sschrass.asciidoc.dsl

/**
 * A Title.
 */
open class Title(
    private val level: Int
) : Element {
    private var value: String? = null

    private fun prefix() = "=".repeat(level + 1)

    operator fun String.unaryPlus() {
        value = this
    }

    override fun render(builder: StringBuilder) {
        value?.let {
            builder.append("${prefix()} $it")
        }
    }
}
