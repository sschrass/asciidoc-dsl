package io.github.sschrass.asciidoc.dsl

@Suppress("unused")
class Section(
    private val level: Int
) : Element {
    private var title: Title? = null
    private val elements = mutableListOf<Element>()

    fun title(value: () -> String) = Title(value(), level)
        .also { title = it }

    fun sectionNumbers(enabled: () -> Boolean) = SectionNumbers(enabled())
        .also(elements::add)

    fun paragraph(init: Paragraph.() -> Unit) = Paragraph()
        .also(init)
        .also(elements::add)

    fun subSection(init: Section.() -> Unit) = Section(level + 1)
        .also(init)
        .also(elements::add)

    fun list(init: List.() -> Unit) = List()
        .also(init)
        .also(elements::add)

    override fun render(builder: StringBuilder) {
        title?.also { it.render(builder) }
        elements.forEach { it.render(builder) }
    }
}
