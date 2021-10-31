package io.github.sschrass.asciidoc.dsl.documentheader.author

import io.github.sschrass.asciidoc.dsl.Element

class EMail : Element {
    private var value: String? = null

    operator fun String.unaryPlus() {
        value = this
    }

    override fun render(builder: StringBuilder) {
        value?.let { builder.append("<$it>") }
    }
}
