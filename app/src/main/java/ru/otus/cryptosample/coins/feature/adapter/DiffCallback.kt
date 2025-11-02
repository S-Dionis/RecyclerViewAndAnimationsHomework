package ru.otus.cryptosample.coins.feature.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

class DiffCallback(private val oldList: List<CoinsAdapterItem>, private  val newList: List<CoinsAdapterItem>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val bool = when (oldItem) {
            is CoinsAdapterItem.CategoryHeader ->
                newItem is CoinsAdapterItem.CategoryHeader && (oldItem.categoryName == newItem.categoryName)

            is CoinsAdapterItem.CoinItem ->
                newItem is CoinsAdapterItem.CoinItem && oldItem.coin.id == newItem.coin.id

            is CoinsAdapterItem.CoinItems -> true
        }
        return bool
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val bool = when (oldItem) {
            is CoinsAdapterItem.CategoryHeader ->
                newItem is CoinsAdapterItem.CategoryHeader && (oldItem.categoryName == newItem.categoryName)

            is CoinsAdapterItem.CoinItem ->
                newItem is CoinsAdapterItem.CoinItem
                        && oldItem.coin.name == newItem.coin.name
                        && oldItem.coin.discount == newItem.coin.discount
                        && oldItem.coin.price == newItem.coin.price
                        && oldItem.coin.highlight == newItem.coin.highlight

            is CoinsAdapterItem.CoinItems -> {
                 newItem is CoinsAdapterItem.CoinItems && oldItem.coins == newItem.coins
            }
        }
        return bool
    }

    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is CoinsAdapterItem.CoinItem && newItem is CoinsAdapterItem.CoinItem) {
            val diffBundle = Bundle()

            if (oldItem.coin.highlight != newItem.coin.highlight) {
                diffBundle.putBoolean("highlight", newItem.coin.highlight)
            }

            return if (diffBundle.size() == 0) null else diffBundle
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}