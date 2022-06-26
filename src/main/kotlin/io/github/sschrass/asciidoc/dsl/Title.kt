package io.github.sschrass.asciidoc.dsl

/**
 * A Title.
 */
open class Title(
    protected open val value: String,
    private val level: Int
) : Element {

    protected fun prefix() = "=".repeat((level + 1).coerceAtMost(MAX_LEVEL))

    override fun render(builder: StringBuilder) {
        value.let { builder.append("${prefix()} $it${System.lineSeparator()}") }
    }

    companion object {
        private const val MAX_LEVEL = 5
    }
}
