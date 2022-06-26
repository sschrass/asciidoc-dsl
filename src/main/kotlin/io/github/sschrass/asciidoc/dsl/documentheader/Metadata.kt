package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Element

open class Metadata(
    private val key: String,
    private val value: String? = null
) : Element {

    override fun render(builder: StringBuilder) {
        builder.appendLine(":$key:${value?.let { " $it" } ?: ""}")
    }
}

class Flag(key: String) : Metadata(key)
class Description(value: String) : Metadata("description", value)
class Keywords(value: String) : Metadata("keywords", value)
