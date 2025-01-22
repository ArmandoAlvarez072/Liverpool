package com.armandoalvarez.liverpool.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.armandoalvarez.liverpool.data.model.VariantsColor
import com.armandoalvarez.liverpool.databinding.ItemColorBinding

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private lateinit var context: Context

    private val callback = object : DiffUtil.ItemCallback<VariantsColor>() {
        override fun areItemsTheSame(oldItem: VariantsColor, newItem: VariantsColor): Boolean {
            return oldItem.colorHex == newItem.colorHex
        }

        override fun areContentsTheSame(oldItem: VariantsColor, newItem: VariantsColor): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    inner class ViewHolder(
        private val binding: ItemColorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(variantsColor: VariantsColor) {

            variantsColor.colorHex?.let {
                if (isValidHexColor(variantsColor.colorHex!!)) {
                    binding.root.setCardBackgroundColor(Color.parseColor(variantsColor.colorHex))
                } else {
                    binding.root.visibility = View.GONE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemColorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color)
    }

    private fun isValidHexColor(hex: String): Boolean {
        val hexColorRegex = Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")
        return hexColorRegex.matches(hex)
    }
}