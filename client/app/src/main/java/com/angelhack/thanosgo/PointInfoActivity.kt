package com.angelhack.thanosgo

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_point_info2.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.internal.Constants
import java.util.concurrent.TimeUnit


class PointInfoActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    var currentPhotoPath: String = ""


    companion object {
        const val ACTIVITY = "PointInfoActivity:activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_info2)

        AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {
            override fun onResult(userStateDetails: UserStateDetails) {
                Log.i("Point information", "AWSMobileClient initialized. User State is " + userStateDetails.userState)
            }

            override fun onError(e: Exception) {
                Log.e("Point information", "Initialization error.", e)
            }
        })

      startWorkBtn.setOnClickListener {
          timerTv.visibility = View.VISIBLE
          completeTaskTv.visibility = View.VISIBLE

           startCountDown()
      }
    }

    private fun startCountDown() {
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timerTv.text = ""+String.format("%d min: %d sec",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))            }

            override fun onFinish() {
                timerTv.text = "done!"
                doneBtn.visibility = View.VISIBLE

                val alertDialog: AlertDialog? = this@PointInfoActivity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton(R.string.ok
                        ) { dialog, id ->
                            // User clicked OK button
                            dispatchTakePictureIntent()
                        }
                        setNegativeButton(R.string.cancel
                        ) { dialog, id ->
                            // User cancelled the dialog
                        }
                    }
                    builder?.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title)
                    // Create the AlertDialog
                    builder.create()
                }

                alertDialog?.show()
            }
        }.start()

    }

    private var imageFilePath  = ""

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.angelhack.thanosgo.fileprovider",
                        it
                    )

                    imageFilePath = photoFile.absolutePath


                    Log.d("Point Info", "${photoFile.absolutePath}")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           toast("success")
            uploadWithTransferUtility()
        }
    }

    fun uploadWithTransferUtility() {
        val transferUtility = TransferUtility.builder()
            .context(applicationContext)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(AmazonS3Client(AWSMobileClient.getInstance()))
            .build()
        val uploadObserver = transferUtility.upload(
            "public/${File(imageFilePath).name}",
            File(imageFilePath))
        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(object: TransferListener {
            override fun onStateChanged(id:Int, state: TransferState) {
                if (TransferState.COMPLETED === state)
                {
                    // Handle a completed upload.
                    try {
                        val s3AvatarURL = AWSS3Util().getS3Client(applicationContext).getResourceUrl("thanosgoapp3e716f86386f42b9952ddffbb9dfcb16-dev", File(imageFilePath).name)
                        Log.i("Point Info", s3AvatarURL)
                    } catch (e: Exception) {
//                        if (avatarURL != null) {
//                            s3AvatarURL = avatarURL!!
//                        }
                    }
                }
            }
            override fun onProgressChanged(id:Int, bytesCurrent:Long, bytesTotal:Long) {
                val percentDonef = (bytesCurrent.toFloat() / bytesTotal.toFloat()) * 100
                val percentDone = percentDonef.toInt()
                Log.d("YourActivity", ("ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%"))
            }
            override fun onError(id:Int, ex:Exception) {
                // Handle errors
            }
        })
        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED === uploadObserver.state)
        {
            // Handle a completed upload.
        }
        Log.d("YourActivity", "Bytes Transferred: " + uploadObserver.getBytesTransferred())
        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal())
    }


}
