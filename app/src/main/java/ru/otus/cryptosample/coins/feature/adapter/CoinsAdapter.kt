package ru.otus.cryptosample.coins.feature.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding
import ru.otus.cryptosample.databinding.ItemCoinBinding
import ru.otus.cryptosample.databinding.ItemCoinsBinding

class CoinsAdapter(private val viewPool: RecyclerView.RecycledViewPool) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_COIN = 1
        private const val VIEW_TYPE_COINS = 2
    }

    private var items = listOf<CoinsAdapterItem>()

    fun setData(categories: List<CoinCategoryState>, showAll: Boolean) {
        val adapterItems = mutableListOf<CoinsAdapterItem>()

        categories.forEach { category ->
            adapterItems.add(CoinsAdapterItem.CategoryHeader(category.name))
            if (category.coins.size > 10 && showAll) {
                adapterItems.add(CoinsAdapterItem.CoinItems(category.name, category.coins.map {
                    CoinsAdapterItem.CoinItem(it)
                }))
            } else {
                category.coins.forEach { coin ->
                    adapterItems.add(CoinsAdapterItem.CoinItem(coin))
                }
            }
        }

        val diffCallback = DiffCallback(this.items, adapterItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items = adapterItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CoinsAdapterItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is CoinsAdapterItem.CoinItem -> VIEW_TYPE_COIN
            is CoinsAdapterItem.CoinItems -> VIEW_TYPE_COINS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> CategoryHeaderViewHolder(
                ItemCategoryHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_TYPE_COIN -> CoinViewHolder(
                ItemCoinBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_TYPE_COINS -> CoinsViewHolder(
                ItemCoinsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ).apply {
                binding.coinsRecyclerView.setRecycledViewPool(viewPool)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CoinsAdapterItem.CategoryHeader -> {
                (holder as CategoryHeaderViewHolder).bind(item.categoryName)
            }

            is CoinsAdapterItem.CoinItem -> {
                (holder as CoinViewHolder).bind(item.coin)
            }

            is CoinsAdapterItem.CoinItems -> {
                (holder as CoinsViewHolder).bind(item.coins, viewPool)
            }
        }
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
