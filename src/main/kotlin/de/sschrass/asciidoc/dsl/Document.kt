package de.sschrass.asciidoc.dsl

class Document {
    private val level = 0
    private val elements = mutableListOf<Element>()

    fun header(init: Header.() -> Unit) = Header(level)
        .also(init)
        .also(elements::add)

    fun paragraph(init: Paragraph.() -> Unit) = Paragraph()
        .also(init)
        .also(elements::add)

    fun section(init: Section.() -> Unit) = Section(level + 1)
        .also(init)
        .also(elements::add)

    fun render() = StringBuilder()
        .also { builder -> elements.forEach { it.render(builder) } }
}

fun document(init: Document.() -> Unit): Document = Document().also(init)
