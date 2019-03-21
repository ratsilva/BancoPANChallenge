package br.com.bancopanchallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Games")
data class Game(

    var viewers: Int = 0,

    var channels: Int = 0,

    @PrimaryKey @NonNull var name: String,

    var logo: String = "",

    var offset: Int = 0
)