package io.github.sschrass.asciidoc.dsl.documentheader.author

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FullNameTest {

    @Test
    fun `fullName renders correctly`() {
        val actual = StringBuilder()
        FullName()
            .apply { +"First I. Last" }
            .render(actual)

        actual.toString() shouldBe "First I. Last"
    }
}
