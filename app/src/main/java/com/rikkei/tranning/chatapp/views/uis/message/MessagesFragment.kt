package com.rikkei.tranning.chatapp.views.uis.message

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikkei.tranning.chatapp.BR
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentMessageBinding
import com.rikkei.tranning.chatapp.views.adapters.MessageAdapter

class MessagesFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>() {


    private lateinit var messageViewModel: MessageViewModel
    private lateinit var messageFriendAdapter: MessageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManagerRequest = LinearLayoutManager(context)
        layoutManagerRequest.orientation = LinearLayoutManager.VERTICAL

        messageFriendAdapter = MessageAdapter(context)
        mViewDataBinding.recyclerMessage.layoutManager = layoutManagerRequest
        mViewDataBinding.recyclerMessage.adapter = messageFriendAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        messageViewModel.listMessageFiendViewModelLiveData.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun getBindingVariable(): Int {
        return BR.messageViewmodel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_message
    }

    override fun getViewModel(): MessageViewModel {
        messageViewModel =
            ViewModelProviders.of(requireActivity()).get(MessageViewModel::class.java)
        return messageViewModel
    }

}