package gb.coding.lightnovel.reader.di

import androidx.room.Room
import gb.coding.lightnovel.BuildConfig
import gb.coding.lightnovel.core.domain.data.networking.HttpClientFactory
import gb.coding.lightnovel.reader.data.local.LightNovelDatabase
import gb.coding.lightnovel.reader.data.repository.BookmarkedNovelRepositoryImpl
import gb.coding.lightnovel.reader.data.repository.NovelRepositoryImpl
import gb.coding.lightnovel.reader.data.repository.SavedWordRepositoryImpl
import gb.coding.lightnovel.reader.data.repository.UnsplashRepositoryImpl
import gb.coding.lightnovel.reader.domain.repository.BookmarkedNovelRepository
import gb.coding.lightnovel.reader.domain.repository.ImageRepository
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import gb.coding.lightnovel.reader.domain.repository.SavedWordRepository
import gb.coding.lightnovel.reader.presentation.browse.BrowseViewModel
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderViewModel
import gb.coding.lightnovel.reader.presentation.library.LibraryViewModel
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.engine.cio.CIO
import okhttp3.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::UnsplashRepositoryImpl).bind<ImageRepository>()

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
        )
            .fallbackToDestructiveMigration(true) // Can only do it before prod.
            .build()
    }
    single { get<LightNovelDatabase>().bookmarkedNovelDao }
    single { get<LightNovelDatabase>().savedWordDao }

    singleOf(::NovelRepositoryImpl).bind<NovelRepository>()
    singleOf(::BookmarkedNovelRepositoryImpl).bind<BookmarkedNovelRepository>()
    singleOf(::SavedWordRepositoryImpl).bind<SavedWordRepository>()

    viewModelOf(::BrowseViewModel)
    viewModelOf(::NovelDetailViewModel)
    viewModelOf(::ChapterReaderViewModel)
    viewModelOf(::LibraryViewModel)
}