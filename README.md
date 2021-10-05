# asciidoc-dsl
A yet very shallow AsciiDoc DSL for Kotlin.

For a different project I need something simple to write an .adoc programatically, so I ended up writing this.

Usage:

```
document {
    header {
        title { +"A Document Title" }
    }
    paragraph { +"Some Text" }
    paragraph { +"Another Text" }

    section {
        title { +"Section title (matches level)" }
        subSection {
            title { +"Section title (also matches level)" }
            list {
                + "A thing"
                + "Another thing"
                + listOf("two very nice", "things")
            }
        }
    }
}
    .render()
```
