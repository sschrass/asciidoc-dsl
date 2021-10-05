package io.github.sschrass.asciidoc.dsl

@AsciiDocDslMarker
interface Element {
    fun render(builder: StringBuilder)
}
