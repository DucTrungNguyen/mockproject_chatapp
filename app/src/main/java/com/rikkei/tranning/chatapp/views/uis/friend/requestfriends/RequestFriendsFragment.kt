package com.rikkei.tranning.chatapp.views.uis.friend.requestfriends

//import com.rikkei.tranning.chatapp.BR
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentRequestFriendsBinding
import com.rikkei.tranning.chatapp.services.models.AllUserModel
import com.rikkei.tranning.chatapp.views.adapters.RequestFriendAdapter
import com.rikkei.tranning.chatapp.views.adapters.SendFriendAdapter
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel
import java.util.*


class RequestFriendFragment :
    BaseFragment<FragmentRequestFriendsBinding?, SharedFriendViewModel?>() {
    private lateinit var mFragmentRequestFriendsBinding: FragmentRequestFriendsBinding

    //    private lateinit var mRequestFriendViewModel: RequestFriendViewModel
    private var sharedFriendViewModel: SharedFriendViewModel? = null
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

    override fun getViewModel(): SharedFriendViewModel? {
        sharedFriendViewModel =
            ViewModelProviders.of(requireActivity()).get(SharedFriendViewModel::class.java)
        return sharedFriendViewModel
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

//        sendFriendAdapter = SendFriendAdapter(context)
//        mFragmentRequestFriendsBinding.RecyclerSendFriend.layoutManager = layoutManagerSend
//        mFragmentRequestFriendsBinding.RecyclerSendFriend.adapter = sendFriendAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedFriendViewModel!!.allUserListLiveData.observe(
            viewLifecycleOwner,
            Observer { allUserModels ->
                sharedFriendViewModel!!.collectionArray(allUserModels)
                val requestUserModels = ArrayList<AllUserModel>()
                val sendUserModels = ArrayList<AllUserModel>()
                for (i in allUserModels.indices) {
                    if (allUserModels[i].userType == "friendRequest") {
                        requestUserModels.add(allUserModels[i])
                    } else {
                        if (allUserModels[i].userType == "sendRequest") {
                            sendUserModels.add(allUserModels[i])
                        }
                    }
                }

                requestUserModels.addAll(sendUserModels)
                if (requestUserModels.isEmpty()) {
                    mFragmentRequestFriendsBinding.ImageViewNoResultMyFriend.setVisibility(View.VISIBLE)
                } else {
                    mFragmentRequestFriendsBinding.ImageViewNoResultMyFriend.setVisibility(View.GONE)
                }
                requestFriendAdapter.submitList(requestUserModels)
//                sendFriendAdapter.submitList(sendUserModels)
            })


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

//        mRequestFriendViewModel.getSendFriendArray()
//        mRequestFriendViewModel.getRequestFriendArray()
//
//        mRequestFriendViewModel.listSendFriendMutableLiveData.observe(
//            viewLifecycleOwner,
//            Observer { userArrayListSend ->

//                if (userArrayListSend.size == 0){
//                    RecyclerSendFriend.visibility =View.GONE
//                    RecyclerRequestFriend.layoutParams =paramsFill
//
//
//                }else{
//                    RecyclerSendFriend.visibility =View.VISIBLE
//                    RecyclerRequestFriend.visibility = View.VISIBLE
//                    mRequestFriendViewModel.collectionArray(userArrayListSend)
//                sendFriendAdapter.submitList(userArrayListSend)


//                }

//            })
//        mRequestFriendViewModel.listRequestFriendMutableLiveData.observe(
//            viewLifecycleOwner,
//            Observer { userArrayListRequest ->

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
//                    mRequestFriendViewModel.collectionArray(userArrayListRequest)
//                    requestFriendAdapter.submitList(userArrayListRequest)

//
//                }

//            })


    }


}