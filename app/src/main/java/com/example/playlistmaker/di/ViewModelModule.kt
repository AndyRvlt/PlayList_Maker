package com.example.playlistmaker.di

import android.media.MediaPlayer
import com.example.playlistmaker.mediateca.ui.FavoriteTracksViewModel
import com.example.playlistmaker.mediateca.ui.MediatecaViewModel
import com.example.playlistmaker.mediateca.ui.SavePlaylistViewModel
import com.example.playlistmaker.player.UI.AudioPlayerViewModel
import com.example.playlistmaker.player.UI.TrackPlayer
import com.example.playlistmaker.player.UI.TrackPlayerImpl
import com.example.playlistmaker.search.ui.SearchViewModel
import com.example.playlistmaker.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        AudioPlayerViewModel(get(),get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }
    viewModel {
        MediatecaViewModel()
    }
    viewModel {
        SavePlaylistViewModel()
    }
    viewModel {
        FavoriteTracksViewModel(get())
    }

    single { MediaPlayer() }

    single<TrackPlayer> {
        TrackPlayerImpl(get())
    }


}