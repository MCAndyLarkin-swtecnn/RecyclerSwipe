package com.example.recycleswipe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SwipeAdapter(var values: List<ContactData>) :  RecyclerView.Adapter<SwipeAdapter.SwipeHolder>() {
    object const{
        val SCROLL_REACTION = 20
    }
    class SwipeHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView ?: throw object : Exception("HolderError") {}
    ) {
        var _itemView: View? = null
        init {
            this._itemView = itemView
        }
        fun setNumber(s: String){
            (_itemView as? SwipeView)?.phonenNumber = s
        }
        fun setNameSurname(s: String){
            (_itemView as? SwipeView)?.nameSurname = s
        }
        fun setPhoto(p: Int){
            (_itemView as? SwipeView)?.updatePhoto(p)
        }

        fun setLast(s: String) {
            (_itemView as? SwipeView)?.lastCall = "Last: $s"
        }

        fun setOperator(s: String) {
            (_itemView as? SwipeView)?.operator = "Oper: $s"
        }
    }
    //onCreateViewHolder creates holder ant init view for list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeHolder = SwipeHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.contact,
            parent, false
        )
    )//The hard

    //Bind (Svyazivat') View with values
    override fun onBindViewHolder(holder: SwipeHolder, position: Int) {
        Log.d("RecyclerFillin", "${values[position]}")
        holder.setNameSurname(values[position].nameSurname)
        holder.setNumber(values[position].phoneNumber)
        holder.setLast(values[position].lastCall)
        holder.setOperator(values[position].operator)
        holder.setPhoto(values[position].photo)
    }

    override fun getItemCount() = values.size

    ///!!!!
    /// notifyDataSetChanged() - is funk, which call adapter to update data

}
