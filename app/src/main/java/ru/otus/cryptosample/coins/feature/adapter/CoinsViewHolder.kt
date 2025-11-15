package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.adapter.CoinsAdapterItem.CoinItem
import ru.otus.cryptosample.databinding.ItemCoinsBinding

class CoinsViewHolder(
    val binding: ItemCoinsBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val horizontalAdapter: HorizontalAdapter = HorizontalAdapter()

    init {
        binding.coinsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalAdapter
            isNestedScrollingEnabled = false
        }
    }

    fun bind(coins: List<CoinItem>, pool: RecyclerView.RecycledViewPool) {
        binding.coinsRecyclerView.setRecycledViewPool(pool)
        horizontalAdapter.setData(coins)

    }

}