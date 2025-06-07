package gb.coding.lightnovel.reader.di

import gb.coding.lightnovel.BuildConfig
import gb.coding.lightnovel.reader.data.repository.NovelRepositoryImpl
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import gb.coding.lightnovel.reader.presentation.browse.BrowseViewModel
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
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

    singleOf(::NovelRepositoryImpl).bind<NovelRepository>()

    viewModelOf(::BrowseViewModel)
    viewModelOf(::NovelDetailViewModel)
}