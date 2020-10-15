package br.com.rmso.mesanews.network.response

import br.com.rmso.mesanews.New
import br.com.rmso.mesanews.Pagination
import com.google.gson.annotations.SerializedName

data class NewResponse (
    @SerializedName("pagination")
    var pagination: Pagination,

    @SerializedName("data")
    var listNews: List<New>
)