package br.com.rmso.mesanews.network.response

import br.com.rmso.mesanews.model.New
import com.google.gson.annotations.SerializedName

data class HighlightsResponse (
    @SerializedName("data")
    var listNews: List<New>
)