package io.github.sschrass.asciidoc.dsl

class Header(
    private val level: Int
) : Element {
    private val elements = mutableListOf<Element>()

    fun title(init: Title.() -> Unit) = Title(level)
        .also(init)
        .also(elements::add)

    override fun render(builder: StringBuilder) {
        if (elements.isNotEmpty()) {
            elements.forEach { it.render(builder) }
            builder.append("\n")
        }
    }
}
