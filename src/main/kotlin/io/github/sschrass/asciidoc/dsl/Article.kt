package io.github.sschrass.asciidoc.dsl

import io.github.sschrass.asciidoc.dsl.documentheader.DocumentHeader

/**
 * The default doctype.
 * In DocBook, includes the appendix, abstract, bibliography, glossary, and index sections.
 * Unless you are making a book or a man page, you don’t need to worry about the doctype. The default will suffice.
 */
class Article {
    private val level = 0
    private val elements = mutableListOf<Element>()

    fun header(init: DocumentHeader.() -> Unit) = DocumentHeader()
        .also(init)
        .also(elements::add)

    fun paragraph(init: Paragraph.() -> Unit) = Paragraph()
        .also(init)
        .also(elements::add)

    fun sectionNumbers(enabled: () -> Boolean) = SectionNumbers(enabled())
        .also(elements::add)

    fun section(init: Section.() -> Unit) = Section(level + 1)
        .also(init)
        .also(elements::add)

    fun render() = StringBuilder()
        .also { builder -> elements.forEach { it.render(builder) } }
}

fun article(init: Article.() -> Unit): Article = Article().also(init)
