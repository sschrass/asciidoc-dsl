package io.github.sschrass.asciidoc.dsl

import java.lang.System.lineSeparator

class List : Element {
    private val elements = mutableListOf<String>()

    operator fun Collection<String>.unaryPlus() {
        elements.addAll(this)
    }

    operator fun String.unaryPlus() {
        elements.add(this)
    }

    override fun render(builder: StringBuilder) {
        if (elements.isNotEmpty()) {
            elements.joinToString(separator = "") { "* $it\n" }
                .let(builder::append)
            builder.append(lineSeparator())
        }
    }
}
