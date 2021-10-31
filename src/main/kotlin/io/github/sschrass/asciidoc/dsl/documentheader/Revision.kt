package io.github.sschrass.asciidoc.dsl.documentheader

import io.github.sschrass.asciidoc.dsl.Element

/**
 * A document’s revision information is assigned
 * to three built-in attributes: revnumber, revdate and revremark.
 * These optional attributes can be set and assigned values using the revision line or
 * using attribute entries in a document header.
 */
class Revision : Element {
    private var number: String? = null
    private var date: String? = null
    private var remark: String? = null

    /**
     * The document’s revision number or version is assigned to the built-in revnumber attribute.
     * When assigned using the revision line, the version must contain at least one number, and,
     * if it isn’t followed by a date or remark, it must begin with the letter v (e.g., v7.0.6).
     * Any letters or symbols preceding the number, including v, are dropped when the document is rendered.
     * If revnumber is set with an attribute entry, it doesn’t have to contain a number and the entire value is
     * displayed in the rendered document.
     */
    fun number(init: () -> String) {
        number = init()
    }

    /**
     * The date the revision was completed is assigned to the built-in revdate attribute.
     * If the date is assigned using the revision line, it must be separated from the
     * version by a comma (e.g., 78.1, 2020-10-10). The date can contain letters, numbers, symbols, and
     * attribute references.
     */
    fun date(init: () -> String) {
        date = init()
    }

    /**
     * Remarks about the revision of the document are assigned to the built-in revremark attribute.
     * The remark must be separated by a colon (:) from the version or revision date when assigned
     * using the revision line.
     */
    fun remark(init: () -> String) {
        remark = init()
    }

    override fun render(builder: StringBuilder) {
        number?.let { builder.append(it) }
        date?.let { builder.append(", $it") }
        remark?.let { builder.append(":$it") }
    }
}
