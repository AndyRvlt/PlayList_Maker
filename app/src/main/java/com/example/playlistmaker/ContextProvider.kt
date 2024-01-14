package com.example.playlistmaker

import androidx.annotation.StringRes

interface ContextProvider {
    fun getString(@StringRes res: Int): String
}
