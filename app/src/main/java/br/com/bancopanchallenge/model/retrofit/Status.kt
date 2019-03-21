package br.com.bancopanchallenge.model.retrofit

import androidx.annotation.NonNull

class Status(var status: FetchStatus, var msg: String) {

    enum class FetchStatus{
        @NonNull
        LOADING, FAIL, SUCCESS
    }

}