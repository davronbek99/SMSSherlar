package uz.pdp.smssherlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_poem.view.*
import uz.pdp.smssherlar.R
import uz.pdp.smssherlar.models.PoemModel

class PoemAdapter(var list: ArrayList<PoemModel>, var onClick: OnClickListener) :
    RecyclerView.Adapter<PoemAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(poemModel: PoemModel) {
            itemView.poem_name_tv.text = poemModel.name
            itemView.poem_desc_tv.text = poemModel.desc
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_poem, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            onClick.onItemClickListener(list[position], position)
        }
    }

    override fun getItemCount(): Int = list.size
    interface OnClickListener {
        fun onItemClickListener(poemModel: PoemModel, position: Int)
    }
}