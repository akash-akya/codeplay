package com.random.app.codeplay

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log.d
import android.view.View
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Build
import android.widget.Toast
import java.nio.file.Files.size
import android.app.Activity
import android.content.ClipData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.nio.file.Files.size
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.io.FileInputStream


class MainActivity : AppCompatActivity() {

    private val PERMISSION_WRITE_REQUEST = 1
    private val RC_CHOOSE_PHOTO = 1
    private val RC_TAKE_PHOTO = 2
    private lateinit var fabAddGallery: FloatingActionButton
    private lateinit var fabAddCamera: FloatingActionButton

    var PICK_IMAGE_MULTIPLE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()
        initFam()
        initWebView()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            /*if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to access the microphone is required for this app to record audio.")
                        .setTitle("Permission required")

                builder.setPositiveButton("OK"
                ) { dialog, id ->
                    d("Clicked")
                    makeRequest()
                }

                val dialog = builder.create()
                dialog.show()*/
            makeRequest()
        } else {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }

    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                PERMISSION_WRITE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_WRITE_REQUEST -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    d("Main","Permission has been denied by user")
                } else {
                    d( "Main","Permission has been granted by user")
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun initFam() {
        fbAdd.setOnClickListener { EasyImage.openChooserWithDocuments(this,"Choose from",PICK_IMAGE_MULTIPLE) }
//        famAdd.setOnFloatingActionsMenuUpdateListener(object : FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
//            override fun onMenuExpanded() {
//                rlFamBg.visibility = View.VISIBLE
//            }
//
//            override fun onMenuCollapsed() {
//                rlFamBg.visibility = View.GONE
//            }
//        })
//
//
//        fabAddGallery = com.getbase.floatingactionbutton.FloatingActionButton(this)
//        fabAddGallery.title = "Gallery"
//        fabAddGallery.setIcon(R.drawable.ic_collections_black_24dp)
//        fabAddGallery.colorNormal = ContextCompat.getColor(this, R.color.colorAccent)
//        fabAddGallery.setOnClickListener { takePictureFromGallery() }
//        fabAddGallery.size = com.getbase.floatingactionbutton.FloatingActionButton.SIZE_MINI
//
//        fabAddCamera = com.getbase.floatingactionbutton.FloatingActionButton(this)
//        fabAddCamera.title = "Camera"
//        fabAddCamera.setIcon(R.drawable.ic_photo_camera_black_24dp)
//        fabAddCamera.colorNormal = ContextCompat.getColor(this, R.color.colorAccent)
//        fabAddCamera.setOnClickListener{ }
//        fabAddCamera.size = com.getbase.floatingactionbutton.FloatingActionButton.SIZE_MINI
//
//        famAdd.addButton(fabAddGallery)
//        famAdd.addButton(fabAddCamera)
    }

    private fun takeFromCamera() {

    }

    private fun takePictureFromGallery() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            makeRequest()
        } else {

            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            //            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE)
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


//        if (famAdd != null && famAdd.isExpanded)
//            famAdd.collapse()

//        try {
//            // When an Image is picked
//
//
//            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
//                    && null != data) {
//                // Get the Image from data
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//
//                if (data.data != null) {
//
//                    val mImageUri = data.data
////                    val decoder = BitmapRegionDecoder.newInstance(mImageUri.toString(), false)
////                    val region = decoder.decodeRegion(Rect(10, 10, 50, 50), null)
//                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, mImageUri)
//                    processBitmap(bitmap)
//                    Log.d("Result"," uri :$mImageUri")
//
//                    // Get the cursor
//                    val cursor = contentResolver.query(mImageUri,
//                            filePathColumn, null, null, null)
//                    // Move to first row
//
//
//                    cursor?.moveToFirst()
//
//                    val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
//
////                    imageEncoded = cursor.getString(columnIndex)
//                    cursor.close()
//
//                }
//                }
//            } catch (e: Exception) {
//
//            e.printStackTrace()
//
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show()
//        }

        EasyImage.handleActivityResult(requestCode,resultCode,data,this,object :DefaultCallback (){
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
//                val mImageUri = imageFile?.pa
////                    val decoder = BitmapRegionDecoder.newInstance(mImageUri.toString(), false)
////                    val region = decoder.decodeRegion(Rect(10, 10, 50, 50), null)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeStream(FileInputStream(imageFile),null,options)
//                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, mImageUri)
                    processBitmap(bitmap)
            }

        })


        super.onActivityResult(requestCode, resultCode, data)
    }



    private fun processBitmap(bitmap: Bitmap) {
        Log.d("Result"," uri :${bitmap.height}")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        codeView.settings.javaScriptEnabled = true

    }

    private fun displayCode(code: String) {
        val url = "javascript:loadCode($code)"
        codeView.loadUrl(url)
    }


}
