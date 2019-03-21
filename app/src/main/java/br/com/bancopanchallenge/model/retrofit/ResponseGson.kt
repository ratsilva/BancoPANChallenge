package br.com.bancopanchallenge.model.retrofit

class ResponseGson {

    var top: List<Top>? = null

    inner class Top {

        var viewers: Int = 0
        var channels: Int = 0
        var game: GameGson? = null

    }

    inner class GameGson {

        var localized_name: String? = null
        var box: Map<String, String>? = null

    }

}
