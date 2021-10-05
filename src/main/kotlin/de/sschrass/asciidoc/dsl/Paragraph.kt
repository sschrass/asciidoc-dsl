package de.sschrass.asciidoc.dsl

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
            text.joinToString(separator = " ", postfix = "\n")
                .let(builder::append)
            builder.append("\n")
        }
    }
}
