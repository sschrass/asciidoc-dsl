package io.github.sschrass.asciidoc.dsl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.lang.System.lineSeparator

internal class TitleTest {

    @Test
    fun `title level caps at max level`() {
        val actual = StringBuilder()
        Title("test", Int.MAX_VALUE - 1)
            .render(actual)

        actual.toString() shouldBe "===== test${lineSeparator()}"
    }
}
