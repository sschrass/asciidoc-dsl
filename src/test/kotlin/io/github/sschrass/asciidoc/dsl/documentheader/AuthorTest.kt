package io.github.sschrass.asciidoc.dsl.documentheader

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class AuthorTest {

    @Test
    fun `author renders correctly`() {
        val actual = StringBuilder()
        val author = Author()
        author.fullName { "James T. Kirk" }
        author.eMail { "kirk@enterprise.org" }
        author.render(actual)

        actual.toString() shouldBe "James T. Kirk <kirk@enterprise.org>"
    }
}
