package com.example.playlistmaker.db.data

import com.example.playlistmaker.db.data.entity.PlaylistEntity
import com.example.playlistmaker.db.domain.models.PlayList

class PlaylistDbConvertor {
    fun playlistMap(playList: PlayList): PlaylistEntity {
        return PlaylistEntity(
            playlistId = playList.id,
            name = playList.name,
            description = playList.description,
            imageUri = playList.imageUri,
         //   trackIds = playList.trackIds,
            trackCount = playList.trackCount

        )
    }

    fun playlistMap(playlistEntity: PlaylistEntity): PlayList {
        return PlayList(
            id = playlistEntity.playlistId,
            name = playlistEntity.name,
            description = playlistEntity.description,
            imageUri = playlistEntity.imageUri,
       //     trackIds = playlistEntity.trackIds,
            trackCount = playlistEntity.trackCount
        )
    }

}
