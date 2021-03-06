package io.github.sschrass.asciidoc.dsl

class SectionNumbers(
    private val enabled: Boolean
) : Element {

    override fun render(builder: StringBuilder) {
        takeIf { enabled }
            ?.also { builder.appendLine(":sectnums:") }
            ?: builder.appendLine(":sectnums!:")
    }
}
