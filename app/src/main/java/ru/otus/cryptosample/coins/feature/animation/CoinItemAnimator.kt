package ru.otus.cryptosample.coins.feature.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.animation.BounceInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.R

class CoinItemAnimator(
    private val context: Context
): DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val animator = AnimatorInflater.loadAnimator(context, R.animator.item_add_animation)
        animator.interpolator = BounceInterpolator()
        animator.setTarget(holder.itemView)

        animator.addListener(object: AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
            }

        })

        animator.start()
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val animator = AnimatorInflater.loadAnimator(context, R.animator.item_remove_animation)
        animator.interpolator = BounceInterpolator()
        animator.setTarget(holder.itemView)

        animator.addListener(object: AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
            }

        })
        animator.start()
        return true
    }


}