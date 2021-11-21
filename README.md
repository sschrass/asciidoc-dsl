
# asciidoc-dsl

![License](https://img.shields.io/github/license/sschrass/asciidoc-dsl) ![Build](https://img.shields.io/github/checks-status/sschrass/asciidoc-dsl/mainline)

A yet very shallow AsciiDoc DSL for Kotlin.

For a different project I need something simple to write an .adoc programatically, so I ended up writing this.

Usage:

```
article {
    header {
        documentTitle { "A Document Title" }
        author {
            fullName { "Stefan Schrass" }
            eMail { "stefan.schrass@gmail.com" }
        }
        revision {
            number { "123" }
            date { "2021-01-31" }
            remark { "5" }
        }
        description { "The description" }
        keywords { listOf("a", "list", "of", "keywords") }
        metadata { "key" to "value" }
    }
    paragraph { +"first Paragraph" }
    paragraph { +"second Paragraph" }

    section {
        title { "Section title" }
        paragraph { +"Some Text" }
        subSection {
            title { "SubSection title" }
            list {
                +"A thing"
                +"Another thing"
                +listOf("first thing", "second thing")
            }
        }
    }
}
    .render()
```

will produce

```
= A Document Title
Stefan Schrass <stefan.schrass@gmail.com>
123, 2021-01-31:5
:description: The description
:keywords: a, list, of, keywords
:key: value

first Paragraph

second Paragraph

== Section title
Some Text

=== SubSection title
* A thing
* Another thing
* first thing
* second thing
```
