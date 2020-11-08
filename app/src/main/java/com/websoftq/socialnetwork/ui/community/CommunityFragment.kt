package com.websoftq.socialnetwork.ui.community

import CommonUtils
import UserPreferences
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MainActivity
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.adapter.NewMembersAdapter
import com.websoftq.socialnetwork.adapter.UsersAdapter
import com.websoftq.socialnetwork.models.NewMemberResponse
import com.websoftq.socialnetwork.models.RandomUserResponse
import com.websoftq.socialnetwork.utils.MarshmallowPermissions
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_CAMERA
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.ic_toolbar.view.*
import java.io.File
import java.net.URI


class CommunityFragment : Fragment(), UsersAdapter.CallBack {

    private val marshmallowPermissions: MarshmallowPermissions by lazy { MarshmallowPermissions(this) }
    private val userList: MutableList<RandomUserResponse.RandomUserList> = ArrayList()
    private var searchList: MutableList<RandomUserResponse.RandomUserList> = ArrayList()
    private val newMemberList: MutableList<NewMemberResponse.Data> = ArrayList()
    private lateinit var communityViewModel: CommunityViewModel
    lateinit var userPreferences: UserPreferences
    private var isCameraImageSelected: Boolean = false
    val GALLERY_REQUEST = 101
    val CAMERA_REQUEST_IMAGE = 102
    private var currentImageUri: Uri? = null

    private val userAdapter: UsersAdapter by lazy {
        UsersAdapter(requireContext(), this, userList)
    }
    private val newMemberAdapter: NewMembersAdapter by lazy {
        NewMembersAdapter(requireContext(), newMemberList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        (activity as MenuActivity).updateStatusBarColor("#00ff00")
        userPreferences = UserPreferences(requireContext())

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_new_members.layoutManager = linearLayoutManager
        rv_new_members.setHasFixedSize(true)
        rv_new_members.adapter = newMemberAdapter


        val linearLayoutManager1 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_users.layoutManager = linearLayoutManager1
        rv_users.adapter = userAdapter


        new_member_lyt.setOnClickListener {
            logout_option.visibility = View.GONE
            view.findNavController().navigate(R.id.action_destination_community_to_seeMoreFragment)
        }

        curnt_topic_lyt.setOnClickListener {
            logout_option.visibility = View.GONE
            view.findNavController()
                .navigate(R.id.action_destination_community_to_currentTopicFragment)
        }

        lyt_myprofile.setOnClickListener {
            logout_option.visibility = View.GONE
            view.findNavController()
                .navigate(R.id.action_destination_community_to_profileFragment)
        }

        img_curnt_topic.setOnClickListener {
            logout_option.visibility = View.GONE
            view.findNavController().navigate(R.id.action_destination_community_to_topicFragment)
        }

        toolbar_lyt.img_setting.setOnClickListener {
            if (logout_option.visibility == View.VISIBLE) {
                logout_option.visibility = View.GONE
            } else {
                logout_option.visibility = View.VISIBLE
            }
        }
        lyt_logout.setOnClickListener {
            val accessKey = userPreferences.getAccessKey()
            communityViewModel.getLogout(accessKey)
        }
        toolbar_lyt.img_search.setOnClickListener {
            if (toolbar_lyt.toolBar_search.visibility == View.VISIBLE) {
                toolbar_lyt.toolbar_title.visibility = View.VISIBLE
                toolbar_lyt.toolBar_search.visibility = View.GONE
                toolbar_lyt.toolBar_search.setText("")

            } else {
                toolbar_lyt.toolbar_title.visibility = View.GONE
                toolbar_lyt.toolBar_search.visibility = View.VISIBLE

            }
        }

        lyt_add_temp_image.setOnClickListener {
            uploadImageDialog()
        }

        initView()

        getObservers()
        getPost()

    }


    private fun initView() {

        toolbar_lyt.toolBar_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                try {
                    if (charSequence.length > 2) {

                        for (displayedList in userList) {
                            if (displayedList.firstName.toLowerCase()
                                    .contains(charSequence.toString().toLowerCase())
                            ) {

                                searchList.add(displayedList)
                            }
                        }

                        userAdapter.updateData(searchList)
                    } else {
                        searchList = ArrayList()
                        userAdapter.updateData(userList)
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

    private fun uploadImageDialog() {
        val getArray = arrayOf("Gallery", "Camera")
        AlertDialog.Builder(requireContext())
            .setItems(getArray) { dialog, which ->
                run {
                    when (which) {
                        0 -> {
                            isCameraImageSelected = false
                            if (!marshmallowPermissions.isPermissionGrantedForStorage()) {
                                marshmallowPermissions.requestPermissionForStorage()
                            } else {
                                openGallery()
                            }
                        }
                        1 -> {
                            isCameraImageSelected = true
                            openCamera()
                        }
                        else -> {
                        }
                    }
                    dialog.dismiss()
                }
            }
            .create().show()
    }



    private fun openCamera() {
        if (marshmallowPermissions.isPermissionGrantedForCamera()) {
            if (marshmallowPermissions.isPermissionGrantedForStorage()) {
                openCameraImageIntent()
            } else {
                marshmallowPermissions.requestPermissionForStorage()
            }
        } else {
            marshmallowPermissions.requestPermissionForCamera()
        }
    }

    private fun openGallery() {
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), GALLERY_REQUEST
        )
    }

    private fun openCameraImageIntent() {
        try {
            val value = ContentValues()
            value.put(MediaStore.Images.Media.TITLE, "New Picture")
            value.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
            currentImageUri = activity!!.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                value
            )
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_IMAGE)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isCameraImageSelected) {
                        openCamera()
                    }
                } else {
                    Toast.makeText(requireContext(), "Permission are Needed", Toast.LENGTH_LONG)
                        .show()
                }
            }
            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isCameraImageSelected) {
                        marshmallowPermissions.requestPermissionForCamera()
                    } else {
                        openGallery()
                    }
                } else {
                    Toast.makeText(requireContext(), "Storage Permission Needed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                if (data != null) {
                    val selectedUri = data.data
                    currentImageUri = selectedUri
                    Log.e("File", currentImageUri.toString())
                   /* var path = selectedUri?.toString() // "/mnt/sdcard/FileName.mp3"
                    val file =  File( URI(path))*/

                  //  Log.e("File", file.toString())

                  communityViewModel.tempImageResponse(userPreferences.getAccessKey(), "2", currentImageUri.toString())
                }
            }
            if (requestCode == CAMERA_REQUEST_IMAGE) {
                if (currentImageUri != null) {
                    communityViewModel.tempImageResponse(userPreferences.getAccessKey(), "2", currentImageUri.toString())
                }
            }
        }
    }



    private fun getPost() {
        communityViewModel.getRandomUsers()
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
                loader_community.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                loader_community.visibility = View.GONE
            }
        })

        communityViewModel.getResponse.observe(this.viewLifecycleOwner, Observer {

            userList.clear()
            it?.let {
                if (it.statusCode == "200") {
                    if (it.randomUserList.isEmpty()) {
                        //  emptyText.visibility = View.VISIBLE
                    } else {
                        //    emptyText.visibility = View.GONE
                        userList.addAll(it.randomUserList)
                        setView()
                    }

                }
            }
            communityViewModel.getNewMembers()
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

        communityViewModel.getLogoutResponse.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                if (it.statusCode == "200") {
                    userPreferences.clearPrefs()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                }
            }
        })
    }

    private fun setView() {
        userAdapter.notifyDataSetChanged()
    }

    private fun setViewNewMember() {
        newMemberAdapter.notifyDataSetChanged()
    }


    override fun itemClicks(view: View, userId: String) {
        val bundle = bundleOf(
            getString(R.string.user_id) to userId
        )
        view.findNavController()
            .navigate(R.id.action_destination_community_to_myProfileFragment, bundle)
    }
}