package com.websoftq.socialnetwork

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.adapter.NewMembersAdapter
import com.websoftq.socialnetwork.adapter.SeeMoreAdapter
import com.websoftq.socialnetwork.models.NewMemberResponse
import com.websoftq.socialnetwork.ui.community.CommunityViewModel
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_see_more.*
import kotlinx.android.synthetic.main.ic_toolbar.view.*


class SeeMoreFragment : Fragment(), SeeMoreAdapter.CallBack {
    private val newMemberList: MutableList<NewMemberResponse.Data> = ArrayList()
    private var searchList: MutableList<NewMemberResponse.Data> = ArrayList()
    private lateinit var communityViewModel: CommunityViewModel
    private val newMemberAdapter: SeeMoreAdapter by lazy {
        SeeMoreAdapter(requireContext(),this, newMemberList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_see_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityViewModel = ViewModelProviders.of(this).get (CommunityViewModel::class.java)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_see_more.layoutManager = linearLayoutManager
        rv_see_more.setHasFixedSize(true)
        rv_see_more.adapter = newMemberAdapter

        initViews()

        getObservers()
        getPost()

        (activity as MenuActivity).hideBottomNavView()
    }

    private fun initViews(){
        toolbar_search.img_search.setOnClickListener {
            if (toolbar_search.toolBar_search.visibility == View.VISIBLE) {
                toolbar_search.toolbar_title.visibility = View.VISIBLE
                toolbar_search.toolBar_search.visibility = View.GONE
                toolbar_search.toolBar_search.setText("")

            } else {
                toolbar_search.toolbar_title.visibility = View.GONE
                toolbar_search.toolBar_search.visibility = View.VISIBLE

            }
        }


        toolbar_search.toolBar_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                try {
                    if (charSequence.length > 2) {

                        for ( displayedList in newMemberList) {
                            if (displayedList.firstName.toLowerCase().contains(charSequence.toString().toLowerCase())) {

                                searchList.add(displayedList)
                            }
                        }

                        newMemberAdapter.updateData(searchList)
                    } else {
                        searchList = ArrayList()
                        newMemberAdapter.updateData(newMemberList)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
            }
        })
    }


    private fun getPost() {
        communityViewModel.getNewMembers()
    }




    override fun onDestroy() {
        super.onDestroy()
        (activity as MenuActivity).showBottoNavView()
    }


    private fun setViewNewMember() {
        newMemberAdapter.notifyDataSetChanged()
    }

    private fun getObservers() {
        communityViewModel.errorResponse.observe(this.viewLifecycleOwner, Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        communityViewModel.isLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                loader_see_more.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_see_more.visibility = View.GONE
            }
        })



        communityViewModel.getNewMemberResponse.observe(this.viewLifecycleOwner, Observer {
            newMemberList.clear()
            it?.let {
                if (it.statusCode == "200") {
                    if (it.data.isEmpty()) {
                        //  emptyText.visibility = View.VISIBLE
                    } else {
                        //    emptyText.visibility = View.GONE
                        newMemberList.addAll(it.data)
                        setViewNewMember()
                    }

                }
            }
        })
    }

    override fun itemClick(view: View, userId: String) {
        val bundle = bundleOf(
            getString(R.string.user_id) to userId
        )
        view.findNavController()
            .navigate(R.id.action_seeMoreFragment_to_myProfileFragment, bundle)
    }


}