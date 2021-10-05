package io.github.sschrass.asciidoc.dsl

class Linebreak : Element {
    override fun render(builder: StringBuilder) {
        builder.append(" +")
            .append("\n")
    }
}
