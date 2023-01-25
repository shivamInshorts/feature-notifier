package com.nis.app.featurenotifier

enum class ViewType(private val type: String, private val id: Int) {
    DOT("dot", 1),
    NEW("new", 2),
    DIALOGUE("dialogue", 3),
    NUMBER("number", 4);

    open fun string(): String? {
        return type;
    }
}
