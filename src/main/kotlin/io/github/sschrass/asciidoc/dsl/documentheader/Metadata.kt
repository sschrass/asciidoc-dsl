package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Element

open class Metadata(
    private val key: String,
    private val value: String
) : Element {

    override fun render(builder: StringBuilder) {
        builder.append(":$key: $value\n")
    }
}

class Description(value: String) : Metadata("description", value)
class Keywords(value: String) : Metadata("keywords", value)
