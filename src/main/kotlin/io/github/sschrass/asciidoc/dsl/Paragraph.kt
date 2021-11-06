package io.github.sschrass.asciidoc.dsl

import java.lang.System.lineSeparator

class Paragraph : Element {
    private val text = mutableListOf<String>()

    operator fun Collection<String>.unaryPlus() {
        text.addAll(this)
    }

    operator fun String.unaryPlus() {
        text.add(this)
    }

    override fun render(builder: StringBuilder) {
        if (text.isNotEmpty()) {
            text.joinToString(separator = " ", postfix = lineSeparator())
                .let(builder::append)
            builder.append(lineSeparator())
        }
    }
}
