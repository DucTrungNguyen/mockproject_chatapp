package com.rikkei.tranning.chatapp.views.uis.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikkei.tranning.chatapp.BR
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentMessageBinding
import com.rikkei.tranning.chatapp.views.adapters.MessageAdapter
import com.rikkei.tranning.chatapp.views.adapters.RequestFriendAdapter
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel

class MessagesFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>() {


    lateinit var mFragmentMessageBinding: FragmentMessageBinding;
    lateinit var messageViewModel: MessageViewModel;
    private lateinit var messageFriendAdapter: MessageAdapter;


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_message, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentMessageBinding = viewDataBinding!!
        val layoutManagerRequest = LinearLayoutManager(context)
        layoutManagerRequest.orientation = LinearLayoutManager.VERTICAL

        messageFriendAdapter = MessageAdapter(context)
        mFragmentMessageBinding.recyclerMessage.layoutManager = layoutManagerRequest
        mFragmentMessageBinding.recyclerMessage.adapter = messageFriendAdapter
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