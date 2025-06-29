package gb.coding.lightnovel.reader.di

import androidx.room.Room
import gb.coding.lightnovel.BuildConfig
import gb.coding.lightnovel.reader.data.local.LightNovelDatabase
import gb.coding.lightnovel.reader.data.repository.BookmarkedNovelRepositoryImpl
import gb.coding.lightnovel.reader.data.repository.NovelRepositoryImpl
import gb.coding.lightnovel.reader.domain.repository.BookmarkedNovelRepository
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import gb.coding.lightnovel.reader.presentation.browse.BrowseViewModel
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderViewModel
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY
        ) {
            install(Postgrest)
        }
    }

    single {
        Room.databaseBuilder(
            context = androidContext().applicationContext,
            klass = LightNovelDatabase::class.java,
            name = "light_novel.db"
        ).build()
    }
    single { get<LightNovelDatabase>().bookmarkedNovelDao }

    singleOf(::NovelRepositoryImpl).bind<NovelRepository>()
    singleOf(::BookmarkedNovelRepositoryImpl).bind<BookmarkedNovelRepository>()

    viewModelOf(::BrowseViewModel)
    viewModelOf(::NovelDetailViewModel)
    viewModelOf(::ChapterReaderViewModel)
}