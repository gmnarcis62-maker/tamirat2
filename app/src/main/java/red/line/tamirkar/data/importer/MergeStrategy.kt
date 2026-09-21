package red.line.tamirkar.data.importer

enum class MergeStrategy {
    REPLACE_ALL,
    MERGE_KEEP_EXISTING,
    MERGE_OVERWRITE_EXISTING,
    SKIP_EXISTING
}