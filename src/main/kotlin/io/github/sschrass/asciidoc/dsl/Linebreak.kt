package io.github.sschrass.asciidoc.dsl

import java.lang.System.lineSeparator

@Suppress("unused")
class Linebreak : Element {
    override fun render(builder: StringBuilder) {
        builder.append(" +")
            .append(lineSeparator())
    }
}
