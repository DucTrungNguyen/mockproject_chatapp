package com.rikkei.tranning.chatapp.views.uis.friend.requestfriends
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentRequestFriendsBinding
import com.rikkei.tranning.chatapp.services.models.AllUserModel
import com.rikkei.tranning.chatapp.views.adapters.RequestFriendAdapter
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel
import java.util.*


class RequestFriendFragment :
    BaseFragment<FragmentRequestFriendsBinding?, SharedFriendViewModel?>() {

    private lateinit var requestFriendAdapter: RequestFriendAdapter

    override fun getBindingVariable(): Int {
        return BR.requestFriendViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_request_friends
    }

    override fun getViewModel(): SharedFriendViewModel? {
        return ViewModelProviders.of(requireActivity()).get(SharedFriendViewModel::class.java)
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManagerRequest = LinearLayoutManager(context)
        layoutManagerRequest.orientation = LinearLayoutManager.VERTICAL

        val layoutManagerSend = LinearLayoutManager(context)
        layoutManagerSend.orientation = LinearLayoutManager.VERTICAL

        requestFriendAdapter = RequestFriendAdapter(context)
        mViewDataBinding!!.RecyclerRequestFriend.layoutManager = layoutManagerRequest
        val itemAnimator =
            (mViewDataBinding!!.RecyclerRequestFriend.itemAnimator as SimpleItemAnimator)
        itemAnimator.supportsChangeAnimations = false
        mViewDataBinding!!.RecyclerRequestFriend.adapter = requestFriendAdapter
        requestFriendAdapter.setOnItemClickListener { userModel: AllUserModel ->
            val dialog =
                AlertDialog.Builder(context)
            dialog.setTitle("Thông báo")
            dialog.setIcon(R.drawable.icon_notification)
            dialog.setMessage("Trở thành bạn bè với " + userModel.userName + " để có thể gửi tin nhắn cho nhau nhé :3")
            dialog.setNegativeButton(
                "OK"
            ) { dialog1: DialogInterface, which: Int -> dialog1.dismiss() }
            dialog.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel!!.allUserListLiveData.observe(
            viewLifecycleOwner,
            Observer { allUserModels ->
                mViewModel!!.collectionArray(allUserModels)
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
                    mViewDataBinding!!.ImageViewNoResultMyFriend.visibility = View.VISIBLE
                } else {
                    mViewDataBinding!!.ImageViewNoResultMyFriend.visibility = View.GONE
                }
                requestFriendAdapter.submitList(requestUserModels)
            })
    }
}