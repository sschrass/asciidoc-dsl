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
    private val metadata: MutableList<Metadata> = mutableListOf()

    fun documentTitle(init: DocumentTitle.() -> Unit) = DocumentTitle()
        .also(init)
        .also { documentTitle = it }

    fun author(init: Author.() -> Unit) = Author()
        .also(init)
        .also { author = it }

    fun revision(init: Revision.() -> Unit) = Revision()
        .also(init)
        .also { revision = it }

    fun metadata(pair: () -> Pair<String, String>) = pair()
        .let { Metadata(it.first, it.second) }
        .also { metadata.add(it) }

    fun description(value: () -> String) = Description(value())
        .also { metadata.add(it) }

    fun keywords(value: () -> List<String>) = value()
        .joinToString(", ")
        .let(::Keywords)
        .also { metadata.add(it) }

    override fun render(builder: StringBuilder) {
        listOf(documentTitle, author, revision)
            .mapNotNull { it }
            .forEach { it.render(builder).also { builder.append("\n") } }
            .also { if (metadata.isNotEmpty()) metadata.forEach { it.render(builder) } }
            .takeIf { documentTitle != null }
            ?.also { builder.append("\n") }
    }
}
