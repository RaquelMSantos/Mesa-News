package br.com.rmso.mesanews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.utils.onClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_new.view.*

class NewAdapter (private var newList: ArrayList<New>, val onClickListener: onClickListener): RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun getItemCount(): Int = newList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = newList[position]
        holder.bind(new)

        holder.itemView.btn_share.setOnClickListener {
            onClickListener.onClickShare(position, newList)
        }

        holder.itemView.btn_favorite.setOnClickListener {
            onClickListener.onClickFavorites(position, newList)
        }

        holder.itemView.setOnClickListener{
            onClickListener.onClickCard(position, newList)
        }
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_new, parent, false)) {

        private var mTitleTextView = itemView.tv_title
        private var mImageView = itemView.img_new
        private var mDescriptionTextView = itemView.tv_description

        fun bind(new: New) {
            mTitleTextView?.text = new.title
            mDescriptionTextView?.text = new.description

            Picasso.get()
                .load(new.image_url)
                .into(mImageView)
        }

    }

    internal fun setNews(news: ArrayList<New>){
        this.newList = news
        notifyDataSetChanged()
    }
}

