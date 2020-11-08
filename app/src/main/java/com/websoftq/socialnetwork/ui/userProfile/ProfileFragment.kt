package com.websoftq.socialnetwork.ui.userProfile

import CommonUtils
import IMAGE_URL
import UserPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.adapter.MyPhotosAdapter
import com.websoftq.socialnetwork.models.MyPhotosResponse
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    lateinit var userPreferences: UserPreferences
    private lateinit var profileViewModel: ProfileViewModel
    private val imageList: MutableList<MyPhotosResponse.Data.Image> = ArrayList()
    private val photosAdapter: MyPhotosAdapter by lazy {
        MyPhotosAdapter(requireContext(), imageList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        tv_user_name.text = userPreferences.getUserName()
        tv_user_description.text = userPreferences.getUserDescription()
        tv_user_image.setImageURI(IMAGE_URL + userPreferences.getUserImage())

        (activity as MenuActivity).hideBottomNavView()


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_my_photos.layoutManager = linearLayoutManager
        rv_my_photos.setHasFixedSize(true)
        rv_my_photos.adapter = photosAdapter



        getObservers()
        profileViewModel.getMyPhotos(userPreferences.getAccessKey())

    }

    private fun getObservers() {
        profileViewModel.errorResponse.observe(this.viewLifecycleOwner, Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        profileViewModel.isLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                loader_my_profile.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_my_profile.visibility = View.GONE
            }
        })

        profileViewModel.getResponse.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                if (it.statusCode == "200") {

                    it.data.images.let {
                        if (it.isEmpty()){
                            empty_photo.visibility = View.VISIBLE
                            rv_my_photos.visibility = View.GONE
                        }else{
                            empty_photo.visibility = View.GONE
                            rv_my_photos.visibility = View.VISIBLE
                            imageList.addAll(it)
                            photosAdapter.notifyDataSetChanged()
                        }

                    }


                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as MenuActivity).showBottoNavView()
    }

}