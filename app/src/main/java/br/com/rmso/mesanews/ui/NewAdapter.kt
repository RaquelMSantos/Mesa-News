package br.com.rmso.mesanews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rmso.mesanews.New
import br.com.rmso.mesanews.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_new.view.*

class NewAdapter (private var newList: ArrayList<New>): RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun getItemCount(): Int = newList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = newList[position]
        holder.bind(new)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_new, parent, false)) {

        private var mNameTextView = itemView.tv_title
        private var mImageView = itemView.img_new
        private var mDescriptionTextView = itemView.tv_description

        fun bind(new: New) {
            mNameTextView?.text = new.title
            mDescriptionTextView?.text = new.description

            Picasso.get()
                .load(new.image_url)
                .into(mImageView)
        }

    }
}
