package io.github.sschrass.asciidoc.dsl

@Suppress("unused")
class Linebreak : Element {
    override fun render(builder: StringBuilder) {
        builder.append(" +")
            .append(System.lineSeparator())
    }
}
