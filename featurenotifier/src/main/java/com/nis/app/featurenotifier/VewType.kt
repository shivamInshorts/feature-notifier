package com.nis.app.featurenotifier

enum class ViewType(private val type: String, private val id: Int) {
    DOT("dot", 1),
    NEW("new", 2),
    DIALOGUE("tooltip", 3),
    NUMBER("number", 4);

    fun string(): String {
        return type;
    }

    companion object {
        fun fromString(type: String): ViewType? {
            var viewType: ViewType? = null;
            for (t in values()) {
                if (t.string() == type) {
                    viewType = t;
                    break;
                }
            }
            return viewType;
        }
    }
}
