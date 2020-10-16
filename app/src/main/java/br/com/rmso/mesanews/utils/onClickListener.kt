package br.com.rmso.mesanews.utils

import br.com.rmso.mesanews.model.New

interface onClickListener {
    fun onClickCard(
        position: Int,
        newList: ArrayList<New>
    )

    fun onClickShare(
        position: Int,
        newList: ArrayList<New>
    )

    fun onClickFavorites(
        position: Int,
        newList: ArrayList<New>
    )
}