package gb.coding.lightnovel.reader.data.repository

import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.util.Error
import gb.coding.lightnovel.core.domain.util.Result
import gb.coding.lightnovel.reader.domain.models.Chapter
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.rpc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Request data classes
@Serializable
private data class NovelTagRequest(
    @SerialName("novel_id")
    val novelId: String,
    @SerialName("lang_code")
    val langCode: String,
)

@Serializable
data class TagResponse(
    @SerialName("tag_name")
    val tagName: String
)

class NovelRepositoryImpl(
    private val supabase: SupabaseClient
) : NovelRepository {
    override suspend fun getNovelById(id: String): Result<Novel, Error> {
        println("NovelRepositoryImpl | getNovelById | id: \"$id\"")

        val novel = supabase
            .from("novel")
            .select() {
                filter {
                    eq("id", id)
                }
            }
            .decodeList<Novel>()
            .first()

        return Result.Success(novel)
    }

    override suspend fun getTagsByNovelId(
        novelId: String,
        lang: LanguageCode,
    ): Result<List<String>, Error> {
        println("NovelRepositoryImpl | getNovelTags | Language code: \"${lang.code}\" | Novel id: \"$novelId\"")
        val tags = supabase
            .postgrest
            .rpc("get_novel_tags", NovelTagRequest(novelId, lang.code))
            .decodeList<TagResponse>()
            .map { it.tagName }

        println("NovelRepositoryImpl | getNovelTags | Tags: $tags")

        return Result.Success(tags)
    }

    override suspend fun getChapterById(id: String): Result<Chapter, Error> {
        println("NovelRepositoryImpl | getChapterById | id: \"$id\"")
        val chapter = supabase
            .from("chapter")
            .select() {
                filter {
                    eq("id", id)
                }
            }
            .decodeSingle<Chapter>()

        return Result.Success(chapter)
    }

    /// Returns a list of all chapters for a given novel id without their content
    override suspend fun getChaptersByNovelId(id: String): Result<List<Chapter>, Error> {
        println("NovelRepositoryImpl | getChaptersByNovelId | Novel id: \"$id\"")
        val chapters = supabase
            .from("chapter")
            // Fetching content will be too much overhead when loading the chapter list
            // Much data which isn't even going to be read
            .select(
                columns = Columns.list(
                    "id",
                    "novel_id",
                    "part",
                    "title",
                    "chapter_number",
                    "created_at"
                )
            ) {
                filter {
                    eq("novel_id", id)
                }
            }
            .decodeList<Chapter>()

        println("NovelRepositoryImpl | getChaptersByNovelId | Chapter count: ${chapters.size}")
        chapters.forEach {
            println("NovelRepositoryImpl | getChaptersByNovelId | Chapter | id: \"${it.id}\" | title: \"${it.title}\"")
        }

        return Result.Success(chapters)
    }

    override suspend fun searchNovels(query: String): Result<List<Novel>, Error> {
        println("NovelRepositoryImpl | searchNovels | query: \"$query\"")

        val novels = supabase
            .from("novel")
            .select() {
                filter {
                    ilike("title", "%$query%")
                }
            }
            .decodeList<Novel>()

        return Result.Success(novels)
    }
}