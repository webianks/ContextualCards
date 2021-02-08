package com.webianks.contextualcards.contextual_cards

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
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroupResponse
import com.webianks.contextualcards.contextual_cards.util.*
import com.google.android.material.button.MaterialButton
import com.webianks.contextualcards.contextual_cards.list.ContextualRecyclerViewAdapter
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
    private lateinit var errorView: View
    private lateinit var retryButton: MaterialButton

    init {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey))
        LayoutInflater.from(getContext()).inflate(R.layout.content_contextual_cards, this, true)
    }

    fun init(activity: AppCompatActivity) {
        mainViewModel = ContextualServiceLocator.getContextualCardsViewModel(activity)
        this.mActivity = activity
        initViews()
        activity.lifecycle.addObserver(this)
    }

    fun init(fragment: Fragment) {
        mainViewModel = ContextualServiceLocator.getContextualCardsViewModel(fragment)
        this.mFragment = fragment
        initViews()
        fragment.lifecycle.addObserver(this)
    }

    private fun initViews() {
        swipeRefresh = swipe_refresh_contextual
        recyclerView = recycler_view_contextual
        progressBar = progress_contextual
        errorView = contextual_error
        retryButton = contextual_retry
        swipeRefresh.setOnRefreshListener {
            mainViewModel?.getCardGroups(refreshing = true)
        }
        retryButton.setOnClickListener {
            mainViewModel?.getCardGroups()
        }

        addObservers()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        mainViewModel?.getCardGroups()
        Log.d(TAG,"onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Log.d(TAG,"onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        Log.d(TAG,"onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        this.mActivity = null
        this.mFragment = null
    }


    private fun addObservers() {

        if (mActivity != null) {
            mActivity?.let { activity ->
                mainViewModel?.cardGroupUiState?.observe(activity) {
                  onObserverStateChange(it)
                }
            }
        } else {
            mFragment?.let { fragment ->
                mainViewModel?.cardGroupUiState?.observe(fragment) {
                    onObserverStateChange(it)
                }
            }
        }
    }

    private fun onObserverStateChange(it: UiState) {
        when (it) {
            Loading -> {
                loadingStateBehaviour()
            }
            is Success<*> -> {
                setSuccessBehaviour(it)
            }
            Failed -> {
                failedStateBehaviour()
            }
            else -> {
                defaultStateBehaviour()
            }
        }
    }

    private fun loadingStateBehaviour(){
        progressBar.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    private fun defaultStateBehaviour() {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        errorView.visibility = View.GONE
    }

    private fun failedStateBehaviour() {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        errorView.visibility = View.VISIBLE
    }

    private fun setSuccessBehaviour(uiState: Success<*>) {

        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false

        val cardGroupResponse = uiState.data as CardGroupResponse

        Log.i(TAG, "CardGroupResponse count ${cardGroupResponse.cardGroups.size}")
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ContextualRecyclerViewAdapter(context, cardGroupResponse.cardGroups,
                { url ->
                    openUrl(context, url)
                },
                { which ->
                    //Card dismissed listener
                    setCardDismissed(context, which)
            })
    }
}