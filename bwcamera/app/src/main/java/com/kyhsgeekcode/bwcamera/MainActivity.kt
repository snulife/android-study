package com.kyhsgeekcode.bwcamera

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import com.kyhsgeekcode.bwcamera.ui.theme.BwcameraTheme
import java.io.File

//class Hello : ActivityResultCallback<Boolean> {
//    override fun onActivityResult(result: Boolean) {
//        Log.d("Hello", "onActivityResult: $result")
//    }
//}

class MainActivity : ComponentActivity() {
    private lateinit var photoUri: Uri
    private val bitmap = mutableStateOf(null as Bitmap?)

    private val expensiveValue by lazy {
        Log.d("MainActivity", "expensiveValue: called")
        4 + 10
    }

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "bw_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, contentValues, null, null)
            }
        }
        return uri
    }


    private val takePictureLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            Log.d("MainActivity", "onActivityResult: $success $photoUri $expensiveValue")
            if (success) {
                val bitmap3 = uriToBitmap(this, photoUri)
                bitmap.value = bitmap3
                bitmap3?.let {
                    saveImageToGallery(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BwcameraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Button(modifier = Modifier.wrapContentHeight(),
                            onClick = {
                                val photoFile = File.createTempFile(
                                    "IMG_",
                                    ".jpg",
                                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                )
                                photoUri = FileProvider.getUriForFile(
                                    this@MainActivity,
                                    "com.kyhsgeekcode.bwcamera.fileprovider",
                                    photoFile
                                )
                                Log.d("MainActivity", "photoUri: $photoUri")
                                takePictureLauncher.launch(photoUri)
                            }) {
                            Text(text = "사진 찍기")
                        }
//                        val bitmap2 = bitmap.value
//                        if (bitmap2 != null) {
//                            val imageBitmap: ImageBitmap = bitmap2.asImageBitmap()
//                            Image(bitmap = imageBitmap, contentDescription = "Image from URI")
//                        }
                        bitmap.value?.let {
                            val imageBitmap: ImageBitmap = it.asImageBitmap()
                            Image(bitmap = imageBitmap, contentDescription = "Image from URI")
                        }
                    }
                }
            }
        }
    }
}

