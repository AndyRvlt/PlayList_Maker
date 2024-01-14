package com.example.playlistmaker

import android.content.Context
import androidx.annotation.StringRes

class ContextProviderImpl(val context: Context): ContextProvider {

   override fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }
}
