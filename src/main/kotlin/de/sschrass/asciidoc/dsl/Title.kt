package de.sschrass.asciidoc.dsl

class Title(
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
            builder.append("\n")
        }
    }
}
