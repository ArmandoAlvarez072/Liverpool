package com.armandoalvarez.liverpool.presentation.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armandoalvarez.liverpool.R
import com.armandoalvarez.liverpool.data.model.Record
import com.armandoalvarez.liverpool.databinding.ItemProductBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private lateinit var context: Context

    private val callback = object : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {

            val colorAdapter = ColorAdapter()
            binding.rvColors.apply {
                adapter = colorAdapter
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
            }

            colorAdapter.differ.submitList(record.variantsColor)

            binding.tvTitle.text = record.productDisplayName

            if (record.promoPrice != null && record.listPrice != null && record.promoPrice!! < record.listPrice!!) {
                binding.tvOfferPrice.text = context.getString(R.string.price_text, record.promoPrice)
                binding.tvPrice.text = context.getString(R.string.price_text, record.listPrice)
                binding.tvPrice.paintFlags = binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                binding.tvOfferPrice.text = context.getString(R.string.price_text, record.listPrice)
                binding.tvOfferPrice.setTextAppearance(context, R.style.TextViewBody)
                binding.tvPrice.visibility = View.GONE
            }

            Glide.with(binding.imgProduct.context)
                .load(record.smImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgProduct)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(record)
                }
            }
        }
    }

    private var onItemClickListener: ((Record) -> Unit)? = null

    fun setOnItemClickListener(listener: (Record) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = differ.currentList[position]
        holder.bind(record)
    }

}