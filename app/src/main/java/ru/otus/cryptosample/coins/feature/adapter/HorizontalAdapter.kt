package ru.otus.cryptosample.coins.feature.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.adapter.CoinsAdapterItem.CoinItem
import ru.otus.cryptosample.databinding.ItemCoinBinding

class HorizontalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<CoinItem>()

    fun setData(coinItems: List<CoinItem>) {
        val diffCallback = DiffCallback(this.items, coinItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = coinItems

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CoinViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

        val horizontalMargins = layoutParams.leftMargin + layoutParams.rightMargin

        val screenWidth = holder.itemView.resources.displayMetrics.widthPixels

        val itemWidth = (screenWidth / 2) - horizontalMargins

        layoutParams.width = itemWidth
        layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT
        holder.itemView.layoutParams = layoutParams

        (holder as CoinViewHolder).bind(items[position].coin)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any?>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        val bundle = payloads[0] as Bundle

        if (holder is CoinViewHolder) {
            if (bundle.containsKey("highlight")) {
                holder.updateHighlight(bundle.getBoolean("highlight"))
            }
        }
    }

}