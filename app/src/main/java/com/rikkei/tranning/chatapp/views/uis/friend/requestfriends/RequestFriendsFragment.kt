package com.rikkei.tranning.chatapp.views.uis.friend.requestfriends

//import com.rikkei.tranning.chatapp.BR
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.ViewModelProviderFactory
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentRequestFriendsBinding
import com.rikkei.tranning.chatapp.views.adapters.RequestFriendAdapter
import com.rikkei.tranning.chatapp.views.adapters.SendFriendAdapter
import kotlinx.android.synthetic.main.fragment_request_friends.*
import kotlin.properties.Delegates


class RequestFriendFragment : BaseFragment<FragmentRequestFriendsBinding?, RequestFriendViewModel?>() {
    private lateinit var mFragmentRequestFriendsBinding: FragmentRequestFriendsBinding
    private lateinit var mRequestFriendViewModel: RequestFriendViewModel
    private lateinit var requestFriendAdapter: RequestFriendAdapter
    private lateinit var sendFriendAdapter: SendFriendAdapter

    private lateinit var paramsFill: LinearLayout.LayoutParams
    lateinit var paramsHide: LinearLayout.LayoutParams
//    var recyclerView by Delegates.notNull<Int>()
    override fun getBindingVariable(): Int {
        return BR.requestFriendViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_request_friends
    }

    override fun getViewModel(): RequestFriendViewModel {
        this.mRequestFriendViewModel =
            ViewModelProviders.of(this, ViewModelProviderFactory()).get(
                RequestFriendViewModel::class.java
            )
        return mRequestFriendViewModel as RequestFriendViewModel
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentRequestFriendsBinding = viewDataBinding!!
        val layoutManagerRequest = LinearLayoutManager(context)
        layoutManagerRequest.orientation = LinearLayoutManager.VERTICAL

        val layoutManagerSend = LinearLayoutManager(context)
        layoutManagerSend.orientation = LinearLayoutManager.VERTICAL

        requestFriendAdapter = RequestFriendAdapter(context)
        mFragmentRequestFriendsBinding.RecyclerRequestFriend.layoutManager = layoutManagerRequest
        mFragmentRequestFriendsBinding.RecyclerRequestFriend.adapter = requestFriendAdapter

        sendFriendAdapter = SendFriendAdapter(context)
        mFragmentRequestFriendsBinding.RecyclerSendFriend.layoutManager = layoutManagerSend
        mFragmentRequestFriendsBinding.RecyclerSendFriend.adapter = sendFriendAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        paramsHide = LinearLayout.LayoutParams(
//            LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0
//        )
//        paramsHide.weight = 0f
//
//
//        paramsFill= LinearLayout.LayoutParams(
//            LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0
//        )
//        paramsFill.weight = 2f


        mRequestFriendViewModel.getRequestFriendArray()
        mRequestFriendViewModel.getSendFriendArray()

        mRequestFriendViewModel.listRequestFriendMutableLiveData.observe(
            viewLifecycleOwner,
            Observer { userArrayListRequest ->

//
//                if (userArrayListRequest.size == 0){
//
//                    RecyclerRequestFriend.visibility = View.GONE
//                    RecyclerSendFriend.layoutParams =paramsFill
//
//
//
//                }else {
//                    RecyclerSendFriend.visibility =View.VISIBLE
//                    RecyclerRequestFriend.visibility = View.VISIBLE
                    mRequestFriendViewModel.collectionArray(userArrayListRequest)
                    requestFriendAdapter.submitList(userArrayListRequest)

//
//                }

            })

        mRequestFriendViewModel.listSendFriendMutableLiveData.observe(
            viewLifecycleOwner,
            Observer { userArrayListSend ->

//                if (userArrayListSend.size == 0){
//                    RecyclerSendFriend.visibility =View.GONE
//                    RecyclerRequestFriend.layoutParams =paramsFill
//
//
//                }else{
//                    RecyclerSendFriend.visibility =View.VISIBLE
//                    RecyclerRequestFriend.visibility = View.VISIBLE
                    mRequestFriendViewModel.collectionArray(userArrayListSend)
                    sendFriendAdapter.submitList(userArrayListSend)


//                }

            })
    }
}