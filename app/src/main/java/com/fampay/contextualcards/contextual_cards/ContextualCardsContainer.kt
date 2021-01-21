package com.fampay.contextualcards.contextual_cards

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.fampay.contextualcards.R
import com.fampay.contextualcards.ServiceLocator
import com.fampay.contextualcards.util.*

class ContextualCardsContainer(context: Context, atts: AttributeSet) : FrameLayout(context, atts),
    LifecycleObserver {

    companion object {
        const val TAG = "ContextualCardsContainer"
    }

    private var mainViewModel: ContextualContainerViewModel? = null
    private var mActivity: AppCompatActivity? = null
    private var mFragment: Fragment? = null

    init {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey))
        LayoutInflater.from(getContext()).inflate(R.layout.content_contextual_cards, this, true)
    }

    fun onCreate(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(this)
        mainViewModel = ServiceLocator.getContextualCardsViewModel(activity)
        this.mActivity = activity
    }

    fun onCreate(fragment: Fragment) {
        fragment.lifecycle.addObserver(this)
        mainViewModel = ServiceLocator.getContextualCardsViewModel(fragment)
        this.mFragment = fragment
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        addObservers()
        mainViewModel?.getCardGroups()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {

    }

    private fun addObservers() {

        mActivity?.let { activity ->
            mainViewModel?.cardGroupUiState?.observe(activity) {
                when (it) {
                    Loading -> {
                        //main_progress.visibility = View.VISIBLE
                    }

                    is Success<*> -> {
                        /*main_progress.visibility = View.GONE
                        swipe_refresh.isRefreshing = false

                        val cardGroupResponse = it.data as CardGroupResponse

                        Log.i(TAG, "CardGroupResponse count ${cardGroupResponse.cardGroups.size}")
                        rv_main.layoutManager = LinearLayoutManager(this)
                        rv_main.adapter = ContextualRvAdapter(this, cardGroupResponse.cardGroups,
                                { url ->
                                    openUrl(this, url)
                                },
                                { which ->
                                    //Card dismissed listener
                                    setCardDismissed(this, which)
                                })*/
                    }

                    Failed -> {
                        //main_progress.visibility = View.GONE
                        //swipe_refresh.isRefreshing = false
                    }
                    else -> {
                        //main_progress.visibility = View.GONE
                        //swipe_refresh.isRefreshing = false
                    }
                }
            }
        }
    }
}