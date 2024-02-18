package io.github.sschrass.asciidoc.dsl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class TitleTest {

    @Test
    fun `title level caps at max level`() {
        val actual = StringBuilder()
        Title("test", 1000)
            .render(actual)

        actual.toString() shouldBe "===== test\n"
    }
}
