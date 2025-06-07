package gb.coding.lightnovel

import android.app.Application
import gb.coding.lightnovel.reader.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LightNovelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LightNovelApp)
            modules(appModule)
        }
    }
}