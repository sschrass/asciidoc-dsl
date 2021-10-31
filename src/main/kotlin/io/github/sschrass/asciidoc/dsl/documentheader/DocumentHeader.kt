package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Element
import io.github.sschrass.asciidoc.dsl.documentheader.author.Author

/**
 * The document header is a series of contiguous lines at the start of the document that encapsulates
 * the document title, author and revision information, metadata, and document-wide attributes.
 */
@Suppress("unused")
class DocumentHeader : Element {
    private var documentTitle: DocumentTitle? = null
    private var author: Author? = null
    private var revision: Revision? = null
    private var metadata: Metadata? = null

    fun documentTitle(init: DocumentTitle.() -> Unit) = DocumentTitle()
        .also(init)
        .also { documentTitle = it }

    fun author(init: Author.() -> Unit) = Author()
        .also(init)
        .also { author = it }

    fun revision(init: Revision.() -> Unit) = Revision()
        .also(init)
        .also { revision = it }

    fun metadata(init: Metadata.() -> Unit) = Metadata()
        .also(init)
        .also { metadata = it }

    override fun render(builder: StringBuilder) {
        listOf(documentTitle, author, revision, metadata)
            .mapNotNull { it }
            .forEach { it.render(builder).also { builder.append("\n") } }
            .takeIf { documentTitle != null }
            ?.also { builder.append("\n") }
    }
}
