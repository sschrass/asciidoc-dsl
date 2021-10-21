package io.github.sschrass.asciidoc.dsl.documentheader.author

import io.github.sschrass.asciidoc.dsl.Element

class Author : Element {
    private var fullName: FullName? = null
    private var eMail: EMail? = null

    fun fullName(init: FullName.() -> Unit) = FullName()
        .also(init)
        .also { fullName = it }

    fun eMail(init: EMail.() -> Unit) = EMail()
        .also(init)
        .also { eMail = it }

    override fun render(builder: StringBuilder) {
        fullName?.render(builder)
        eMail
            ?.also { builder.append(" ") }
            ?.render(builder)
    }
}
