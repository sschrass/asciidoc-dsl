package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Element

/**
 * The document header is a series of contiguous lines at the start of the document that encapsulates
 * the document title, author and revision information, metadata, and document-wide attributes.
 */
@Suppress("unused")
class DocumentHeader : Element {
    private val elements = mutableListOf<Element>()

    fun documentTitle(init: DocumentTitle.() -> Unit) = DocumentTitle()
        .also(init)
        .also(elements::add)

    fun author(init: Author.() -> Unit) = Author()
        .also(init)
        .also(elements::add)

    fun revision(init: Revision.() -> Unit) = Revision()
        .also(init)
        .also(elements::add)

    fun metadata(init: Metadata.() -> Unit) = Metadata()
        .also(init)
        .also(elements::add)

    override fun render(builder: StringBuilder) {
        if (elements.isNotEmpty()) {
            elements.forEach { it.render(builder) }
            builder.append("\n")
        }
    }
}
