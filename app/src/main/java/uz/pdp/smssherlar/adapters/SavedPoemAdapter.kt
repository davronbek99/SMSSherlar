package uz.pdp.smssherlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_poem.view.*
import uz.pdp.smssherlar.R
import uz.pdp.smssherlar.db.PoemEntity

class SavedPoemAdapter(var onItemClickListener: OnItemClickListener) :
    ListAdapter<PoemEntity, SavedPoemAdapter.VH>(MyDiffUtil()) {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(poemEntity: PoemEntity) {
            itemView.poem_name_tv.text = poemEntity.name
            itemView.poem_desc_tv.text = poemEntity.desc
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(poemEntity)
            }
        }
    }

    class MyDiffUtil() : DiffUtil.ItemCallback<PoemEntity>() {
        override fun areItemsTheSame(oldItem: PoemEntity, newItem: PoemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PoemEntity, newItem: PoemEntity): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_poem, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(poemEntity: PoemEntity)
    }
}