package com.websoftq.socialnetwork.ui.addTopic

import UserPreferences
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.websoftq.socialnetwork.BuildConfig
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.activities.MenuActivity
import com.websoftq.socialnetwork.camera2API.VideoTrimmerActivity
import com.websoftq.socialnetwork.ui.login.NewLoginViewModel
import com.websoftq.socialnetwork.utils.MarshmallowPermissions
import kotlinx.android.synthetic.main.fragment_connection.*
import kotlinx.android.synthetic.main.fragment_take_video.*
import java.io.File


class TakeVideoFragment : Fragment() {
    private val marshmallowPermissions: MarshmallowPermissions by lazy { MarshmallowPermissions(this) }
    private var isCameraImageSelected: Boolean = false
    private var fileCameraUriVideo: File? = null
    val CAMERA_VIDEO_REQUEST = 1901
    val GALLERY_REQUEST = 101
    var topic :String =""
    var videoValue :String = ""
    val REQUEST_VIDEO_TRIMMER_RESULT = 342

    private lateinit var videoViewModel: VideoViewModel


    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_video, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MenuActivity).hideBottomNavView()
        topic = arguments?.getString(getString(R.string.topic)).toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MenuActivity).showBottoNavView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoViewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)
        userPreferences = UserPreferences(requireContext())


        tv_take_video_kyt.setOnClickListener {
            uploadImageDialog()
        }
        img_back_button.setOnClickListener {
            uploadImageDialog()
        }

        tv_validate_code.setOnClickListener {
            val accesKey = userPreferences.getAccessKey()
            videoViewModel.addTopic(accesKey!!, topic, "Hi this is priya. Lets talk about Coronavirus",
            videoValue)
        }
        getObservers()
    }


    private fun getObservers() {
        videoViewModel.errorResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            CommonUtils.showSnackBar(
                view.findViewById(android.R.id.content),
                it,
                requireContext()
            )
        })

        videoViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                video_loader.visibility = View.VISIBLE
            } else {
                View.VISIBLE
                video_loader.visibility = View.GONE
            }
        })

        videoViewModel.getResponse.observe(requireActivity(), Observer {
            val view = activity ?: return@Observer
            it?.let {
                if (it.statusMessage == "Success") {
                    CommonUtils.showSnackBar(
                        view.findViewById(android.R.id.content),
                        "Topic Added",
                        requireContext()
                    )

                   /* view.findNavController(R.id.menu_nav_host_fragment).navigate(
                        R.id.action_takeVideoFragment_to_destination_community)*/
                } else {

                    it.statusMessage.let { it1 ->
                        CommonUtils.showSnackBar(
                            view.findViewById(android.R.id.content),
                            it1,
                            requireContext()
                        )
                    }
                }
                //  }


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
        val intent = Intent(context, VideoTrimmerActivity::class.java)
        startActivityForResult(intent, REQUEST_VIDEO_TRIMMER_RESULT)
    }

    private fun openCameraImageIntent() {
        try {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            var videoFile: File? = null
           /* try {
                videoFile = createVideoFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }*/
            if (videoFile != null) {
                val videoURI = FileProvider.getUriForFile(
                    requireContext(), BuildConfig.APPLICATION_ID + ".provider",
                    videoFile
                )
                fileCameraUriVideo = videoFile
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                startActivityForResult(intent, CAMERA_VIDEO_REQUEST)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
               if (requestCode == REQUEST_VIDEO_TRIMMER_RESULT) {
                    try {

                        tv_take_video_kyt.visibility = View.GONE
                        select_video_lyt.visibility = View.VISIBLE

                        videoValue = data.extras.getString("INTENT_VIDEO_FILE")

                        Glide.with(requireContext()).asBitmap().load(data.extras.getString("INTENT_VIDEO_FILE")).into(img_topic)


                        Log.e(
                            "TAG",
                            "currentPath : " + data.extras.getString("INTENT_VIDEO_FILE")
                        )

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } else if (requestCode == CAMERA_VIDEO_REQUEST) {
                    try {

                        /* File finalFile1 = new File(picturePath);
                        Uri picUri = FileProvider.getUriForFile(context, getString(R.string.file_provider_authority), finalFile1);
                        Log.e("TAG", "pic uri : " + picUri);
                        String currentPath = RealPathUtil.getFilePathForN(picUri, context);
                        */Log.e(
                            "TAG",
                            "currentPath : " + data.extras
                        )

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_VIDEO_REQUEST) {
                    try {

                        //compressVideo(fileCameraUriVideo.toString())
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }



}