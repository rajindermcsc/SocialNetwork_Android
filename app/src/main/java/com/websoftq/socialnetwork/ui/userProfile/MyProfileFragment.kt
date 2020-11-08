package com.websoftq.socialnetwork.ui.userProfile

import IMAGE_URL
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.adapter.PhotosAdapter
import com.websoftq.socialnetwork.models.UserDetailResponse
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.android.synthetic.main.fragment_my_profile.empty_photo
import kotlinx.android.synthetic.main.fragment_my_profile.tv_user_description
import kotlinx.android.synthetic.main.fragment_my_profile.tv_user_image
import kotlinx.android.synthetic.main.fragment_my_profile.tv_user_name
import kotlinx.android.synthetic.main.fragment_profile.*


class MyProfileFragment : Fragment() {

    private lateinit var myProfileViewModel: MyProfileViewModel
    var userId: Int =0
    private val imageList: MutableList<UserDetailResponse.Data.StudentImages.Image> = ArrayList()
    private val photosAdapter: PhotosAdapter by lazy {
        PhotosAdapter(requireContext(), imageList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getString(getString(R.string.user_id)).toString().toInt()

        Log.e("UserId",userId.toString())

        myProfileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        (activity as MenuActivity).hideBottomNavView()


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_photos.layoutManager = linearLayoutManager
        rv_photos.setHasFixedSize(true)
        rv_photos.adapter = photosAdapter

        getObservers()

        myProfileViewModel.getUserDetails(userId)
    }


    private fun getObservers() {
        myProfileViewModel.errorResponse.observe(this.viewLifecycleOwner, Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        myProfileViewModel.isLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                loader_user_profile.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_user_profile.visibility = View.GONE
            }
        })

        myProfileViewModel.getResponse.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                if (it.statusCode == "200") {
                    tv_user_image.setImageURI(IMAGE_URL + it.data.studentInfo.image)
                    tv_user_name.text =
                        it.data.studentInfo.firstName + " " + it.data.studentInfo.lastName
                    tv_user_description.text = it.data.studentInfo.description
                    it.data.studentImages?.let {
                        if (it.images.isNullOrEmpty()){
                            empty_photo.visibility = View.VISIBLE
                            rv_my_photos.visibility = View.GONE
                        }else{
                            empty_photo.visibility = View.GONE
                            rv_my_photos.visibility = View.VISIBLE
                            imageList.addAll(it.images)
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