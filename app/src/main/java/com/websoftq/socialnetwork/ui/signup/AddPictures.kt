package com.websoftq.socialnetwork.ui.signup

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.websoftq.socialnetwork.R
import com.websoftq.socialnetwork.utils.MarshmallowPermissions
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_CAMERA
import com.websoftq.socialnetwork.utils.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import kotlinx.android.synthetic.main.fragment_add_pictures.*


class AddPictures : Fragment() {


    private val marshmallowPermissions: MarshmallowPermissions by lazy { MarshmallowPermissions(this) }
    private var currentImageUriCover: Uri? = null
    private var currentImageUri: Uri? = null
    val GALLERY_REQUEST = 101
    val CAMERA_REQUEST_IMAGE = 102
    private var isCameraImageSelected: Boolean = false
    private var imageArray: ArrayList<String> = ArrayList()
    private var imageClicked: String? = "img1"
    var userName: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var dateOfBirth: String = ""
    var schoolName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pictures, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userName = arguments?.getString(getString(R.string.user_name)).toString()
        firstName = arguments?.getString(getString(R.string.first_name)).toString()
        lastName = arguments?.getString(getString(R.string.last_name)).toString()
        dateOfBirth = arguments?.getString(getString(R.string.date_of_birthaday)).toString()
        schoolName = arguments?.getString(getString(R.string.school_name)).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view1 = activity?: return
        initViews()
        tv_pictures_continue.setOnClickListener {

            if(imageArray.size<=0){
                CommonUtils.showSnackBar(
                    view1.findViewById(android.R.id.content),
                    getString(R.string.selct_one_image),
                    requireContext()
                )
            }else{
                val bundle = bundleOf(
                    getString(R.string.user_name) to userName,
                    getString(R.string.first_name) to firstName,
                    getString(R.string.last_name) to lastName,
                    getString(R.string.date_of_birthaday) to dateOfBirth,
                    getString(R.string.school_name) to schoolName,
                    getString(R.string.add_pictures) to imageArray.toString()
                )

                view.findNavController()
                    .navigate(R.id.action_addPictures_to_descriptionFragment, bundle)
            }


        }
    }

    fun initViews() {
        main_img_lyt.setOnClickListener {
            if (img_add1.visibility == View.VISIBLE) {
                imageClicked = "img1"
                uploadImageDialog()
            }
        }

        img_lyt_2.setOnClickListener {
            if (img_add2.visibility == View.VISIBLE) {
                if (imageArray.size < 1) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.select_image_order),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    imageClicked = "img2"
                    uploadImageDialog()
                }
            }
        }

        img_lyt_3.setOnClickListener {

            if (img_add3.visibility == View.VISIBLE) {
                if (imageArray.size < 2) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.select_image_order),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    imageClicked = "img3"
                    uploadImageDialog()
                }
            }
        }

        img_lyt_4.setOnClickListener {
            if (img_add4.visibility == View.VISIBLE) {
                if (imageArray.size < 3) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.select_image_order),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    imageClicked = "img4"
                    uploadImageDialog()
                }
            }
        }

        img_remove1.setOnClickListener {
            img_add1.visibility = View.VISIBLE
            img_remove1.visibility = View.GONE
            img1.setImageURI("")
            imageArray.removeAt(0)
        }

        img_remove2.setOnClickListener {
            img_add2.visibility = View.VISIBLE
            img_remove2.visibility = View.GONE
            img2.setImageURI("")
            imageArray.removeAt(1)
        }

        img_remove3.setOnClickListener {
            img_add3.visibility = View.VISIBLE
            img_remove3.visibility = View.GONE
            img3.setImageURI("")
            imageArray.removeAt(2)
        }

        img_remove4.setOnClickListener {
            img_add4.visibility = View.VISIBLE
            img_remove4.visibility = View.GONE
            img4.setImageURI("")
            imageArray.removeAt(3)
        }
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
                    showImages(currentImageUri!!)
                }
            }
            if (requestCode == CAMERA_REQUEST_IMAGE) {
                if (currentImageUri != null) {
                    showImages(currentImageUri!!)
                }
            }
        }
    }

    private fun showImages(selectedURI: Uri) {
        if (imageClicked.equals("img1")) {
            imageArray.add(0, selectedURI.toString())
            img1.setImageURI(selectedURI)
            img_add1.visibility = View.GONE
            img_remove1.visibility = View.VISIBLE
        } else if (imageClicked.equals("img2")) {
            imageArray.add(1, selectedURI.toString())
            img2.setImageURI(selectedURI)
            img_add2.visibility = View.GONE
            img_remove2.visibility = View.VISIBLE
        } else if (imageClicked.equals("img3")) {
            imageArray.add(2, selectedURI.toString())
            img3.setImageURI(selectedURI)
            img_add3.visibility = View.GONE
            img_remove3.visibility = View.VISIBLE
        } else if (imageClicked.equals("img4")) {
            imageArray.add(3, selectedURI.toString())
            img4.setImageURI(selectedURI)
            img_add4.visibility = View.GONE
            img_remove4.visibility = View.VISIBLE
        }

    }
}