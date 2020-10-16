package br.com.rmso.mesanews.network.response

import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.model.Pagination
import com.google.gson.annotations.SerializedName

data class NewResponse (
    @SerializedName("pagination")
    var pagination: Pagination,

    @SerializedName("data")
    var listNews: List<New>
)