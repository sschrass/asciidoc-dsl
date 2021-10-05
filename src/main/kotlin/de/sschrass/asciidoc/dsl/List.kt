package de.sschrass.asciidoc.dsl

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
            builder.append("\n")
        }
    }
}
