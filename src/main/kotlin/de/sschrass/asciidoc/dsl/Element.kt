package de.sschrass.asciidoc.dsl

@AsciiDocDslMarker
interface Element {
    fun render(builder: StringBuilder)
}
