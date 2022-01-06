package com.luisansal.jetpack.features.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseFragment
import com.luisansal.jetpack.features.viewpager.TitleListener
import com.luisansal.jetpack.core.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.domain.network.ApiService.Companion.PUSHER_API_CLUSTER
import com.luisansal.jetpack.domain.network.ApiService.Companion.PUSHER_API_KEY
import com.luisansal.jetpack.core.utils.injectFragment
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.util.HttpAuthorizer
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChatFragment : BaseFragment(), TitleListener {

    override fun getViewIdResource() = R.layout.fragment_chat
    private val authSharedPreferences: AuthSharedPreferences by injectFragment()
    private val chatAdapter by lazy {
        ChatAdapter()
    }

    private val chatViewModel by viewModel<ChatViewModel>()

    private val pusher by lazy {
        val options = PusherOptions()
        options.setCluster(PUSHER_API_CLUSTER)

        val authorizer = HttpAuthorizer(ApiService.BROADCAST_URL)
        val headers = HashMap<String, String>()
        val token = authSharedPreferences.token
        headers.put("Authorization", "Bearer $token")
        authorizer.setHeaders(headers)
        options.authorizer = authorizer
        options.isUseTLS = true
        Pusher(PUSHER_API_KEY, options)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBroadcast()
        onClickBtnSend()
        setupRvChat()
        chatViewModel.viewState.observe(::getLifecycle, ::chatObserve)
    }

    fun setupRvChat(){
        val linearLayoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
        rvMessages?.layoutManager = linearLayoutManager
        rvMessages?.adapter = chatAdapter
    }


    fun chatObserve(viewState: ChatViewState) {
        when (viewState) {
            is ChatViewState.LoadingState -> {
                showLoading(true)
            }
            is ChatViewState.MessageSendedState -> {
                showLoading(false)
            }
        }
    }

    fun initBroadcast() {
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
                if (change.currentState == ConnectionState.CONNECTED) {
                    val socketId = pusher.connection.socketId
                    authSharedPreferences.socketId = socketId
                }
            }

            override fun onError(message: String, code: String?, e: Exception?) {
                Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
            }
        }, ConnectionState.ALL)

        try {
            val channel = pusher.subscribePrivate("private-chat")
            channel.bind("App\\Events\\MessageEvent", object : PrivateChannelEventListener {
                override fun onEvent(event: PusherEvent?) {
                    Log.i("event", "$event")
                    requireActivity().runOnUiThread {
                        val json = JSONObject(event?.data ?: "")
                        val message = json.getString("message")
                        chatAdapter.addItem(ChatModel(message, false))
                    }
                }

                override fun onAuthenticationFailure(message: String?, e: java.lang.Exception?) {
                    Log.i("message", "$message")
                }

                override fun onSubscriptionSucceeded(channelName: String?) {
                    Log.i("channelName", "$channelName")

                }
            })
        } catch (e: java.lang.Exception) {
            Log.i("subscribed", "$e")
        }
    }

    fun onClickBtnSend() {
        btnSendMessage?.setOnClickListener {
            val message = etMessage?.text.toString()
            if (message == "")
                return@setOnClickListener

            pusher.connect()
            chatAdapter.addItem(ChatModel(message, true))
            chatViewModel.sendMessage(message)
            etMessage?.text = null
        }
    }

    override fun onStop() {
        super.onStop()
        pusher.disconnect()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override val title: String = "Chat Pusher"
}