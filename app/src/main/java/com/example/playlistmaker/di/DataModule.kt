package com.example.playlistmaker.di


import android.content.Context
import androidx.room.Room
import com.example.playlistmaker.db.PlaylistDatabase
import com.example.playlistmaker.db.TracksDatabase
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.network.TrackApi
import com.example.playlistmaker.search.data.preferences.TrackPreferences
import com.example.playlistmaker.settings.data.preferences.DayNightThemesPrefs
import com.example.playlistmaker.settings.data.preferences.ThemesPreferencesImpl
import com.example.playlistmaker.settings.domain.ThemesPreferences
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(ITUNES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrackApi::class.java)
    }
    factory { Gson() }

    single(named(TRACK)) {
        androidContext().getSharedPreferences(TRACK, Context.MODE_PRIVATE)
    }
    single(named(SETTINGS_DAY_NIGHT)) {
        androidContext().getSharedPreferences(SETTINGS_DAY_NIGHT, Context.MODE_PRIVATE)
    }


    single {
        TrackPreferences(get(named(TRACK)), get())
    }
    single {
        DayNightThemesPrefs(get(named(SETTINGS_DAY_NIGHT)))
    }

    single<ThemesPreferences> {
        ThemesPreferencesImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        Room.databaseBuilder(androidContext(), TracksDatabase::class.java, "track_table.db").build()
    }
    single {
        Room.databaseBuilder(androidContext(), PlaylistDatabase::class.java, "playlist_table.db")
            .build()
    }

}

const val TRACK = "track"
const val SETTINGS_DAY_NIGHT = "app_settings_day_night_themes"
const val ITUNES = "https://itunes.apple.com/"