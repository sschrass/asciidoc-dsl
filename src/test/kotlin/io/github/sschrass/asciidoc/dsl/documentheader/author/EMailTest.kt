package io.github.sschrass.asciidoc.dsl.documentheader.author

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class EMailTest {

    @Test
    fun `email renders correctly`() {
        val actual = StringBuilder()
        EMail()
            .apply { +"kirk@enterprise.org" }
            .render(actual)

        actual.toString() shouldBe "<kirk@enterprise.org>"
    }
}
