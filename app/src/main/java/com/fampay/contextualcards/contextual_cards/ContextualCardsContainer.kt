package com.fampay.contextualcards.contextual_cards

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fampay.contextualcards.R
import com.fampay.contextualcards.contextual_cards.data.network.response.CardGroupResponse
import com.fampay.contextualcards.contextual_cards.util.*
import kotlinx.android.synthetic.main.content_contextual_cards.view.*

class ContextualCardsContainer(context: Context, atts: AttributeSet) : FrameLayout(context, atts),
    LifecycleObserver {

    companion object {
        const val TAG = "ContextualContainer"
    }

    private var mainViewModel: ContextualContainerViewModel? = null
    private var mActivity: AppCompatActivity? = null
    private var mFragment: Fragment? = null

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    init {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey))
        LayoutInflater.from(getContext()).inflate(R.layout.content_contextual_cards, this, true)
    }

    fun onCreate(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(this)
        mainViewModel = ContextualServiceLocator.getContextualCardsViewModel(activity)
        this.mActivity = activity
        initViews()
    }

    fun onCreate(fragment: Fragment) {
        fragment.lifecycle.addObserver(this)
        mainViewModel = ContextualServiceLocator.getContextualCardsViewModel(fragment)
        this.mFragment = fragment
    }

    private fun initViews() {
        swipeRefresh = swipe_refresh_contextual
        recyclerView = recycler_view_contextual
        progressBar = progress_contextual
        swipeRefresh.setOnRefreshListener {
            mainViewModel?.getCardGroups(refreshing = true)
        }
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
                        progressBar.visibility = View.VISIBLE
                    }

                    is Success<*> -> {
                        progressBar.visibility = View.GONE
                        swipeRefresh.isRefreshing = false

                        val cardGroupResponse = it.data as CardGroupResponse

                        Log.i(TAG, "CardGroupResponse count ${cardGroupResponse.cardGroups.size}")
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter =
                            ContextualRvAdapter(context, cardGroupResponse.cardGroups,
                                { url ->
                                    openUrl(context, url)
                                },
                                { which ->
                                    //Card dismissed listener
                                    setCardDismissed(context, which)
                                })
                    }

                    Failed -> {
                        progressBar.visibility = View.GONE
                        swipeRefresh.isRefreshing = false
                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        swipeRefresh.isRefreshing = false
                    }
                }
            }
        }
    }
}