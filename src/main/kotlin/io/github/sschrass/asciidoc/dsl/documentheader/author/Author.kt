package io.github.sschrass.asciidoc.dsl.documentheader.author

import io.github.sschrass.asciidoc.dsl.Element

class Author : Element {
    private var fullName: String? = null
    private var eMail: String? = null

    fun fullName(fullName: () -> String) = fullName()
        .also { this.fullName = it }

    fun eMail(eMail: () -> String) = eMail()
        .also { this.eMail = it }

    override fun render(builder: StringBuilder) {
        fullName?.let(builder::append)
        eMail
            ?.also { builder.append(" ") }
            ?.let { builder.append("<$it>") }
    }
}
