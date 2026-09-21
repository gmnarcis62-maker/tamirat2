package red.line.tamirkar.core.normalization

object PersianNormalizer {
    fun normalize(input: String): String {
        return input.trim()
            .replace("ي", "ی")
            .replace("ك", "ک")
            .replace("ة", "ه")
            .replace("ۀ", "ه")
            .replace(Regex("\s+"), " ")
            .lowercase()
    }

    fun normalizeForSearch(input: String): String {
        return normalize(input)
            .replace("ء", "")
            .replace("ئ", "ی")
            .replace("ؤ", "و")
            .replace("إ", "ا")
            .replace("أ", "ا")
            .replace("آ", "ا")
    }
}
