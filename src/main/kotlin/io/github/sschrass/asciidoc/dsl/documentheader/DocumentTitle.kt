package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Title

class DocumentTitle(
    override val value: String
) : Title(
    value = value,
    level = 0
) {

    override fun render(builder: StringBuilder) {
        value.let { builder.append("${prefix()} $it") }
    }
}
