package com.example.tp_07_correction

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tp_07_correction.ui.theme.Tp_07_correctionTheme
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class MainActivity : ComponentActivity() {

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        if (results.all { it.value }) {
            requestMedia()
        }
    }

    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
        if (uris.isNotEmpty()) {
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private val createFileLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
        uri?.let {
            saveTextToFile(it)
        }
    }

    private val openFileLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            takePersistablePermission(it)
            val content = readTextFromFile(it)
            content?.let { text ->
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private val count = mutableIntStateOf(0)


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("tp_07_preferences", Context.MODE_PRIVATE)
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "count") {
                count.intValue = getCountFromPreferences()
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        count.intValue = getCountFromPreferences()

        enableEdgeToEdge()
        setContent {
            Tp_07_correctionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.kodee)
                                saveImageToMediaStore(contentResolver, bitmap, "Kodee")
                            }
                        ) {
                            Text(text = "Save Image")
                        }

                        Button(
                            onClick = {
                                deleteImageFromMediaStore(contentResolver, "Kodee")
                            }
                        ) {
                            Text(text = "Delete Image")
                        }

                        Button(
                            onClick = {
                                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                            }
                        ) {
                            Text(text = "Select Image")
                        }

                        Button(
                            onClick = {
                                createTextFile()
                            }
                        ) {
                            Text(text = "Create Document")
                        }

                        Button(
                            onClick = {
                                openTextFile()
                            }
                        ) {
                            Text(text = "Open Document")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    incrementCount()
                                }
                            ) {
                                Text(text = "Increment count")
                            }

                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "${count.intValue}",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }
            }
        }

        // Permission request logic
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_VISUAL_USER_SELECTED))
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
            }
            else -> {
                requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun requestMedia() {
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)

        contentResolver.query(collection, projection, null, null, null)?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val uri = ContentUris.withAppendedId(collection, id)
                println("Image trouvée : $displayName (URI: $uri)")
            }
        }
    }

    private fun saveImageToMediaStore(contentResolver: ContentResolver, imageBitmap: Bitmap, imageName: String) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$imageName.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            contentResolver.update(uri, values, null, null)
        }
    }

    private fun deleteImageFromMediaStore(contentResolver: ContentResolver, imageName: String): Boolean {
        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf("$imageName.png")


        val deletedRows = contentResolver.delete(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            selection, // Clause WHERE
            selectionArgs // Arguments
        )

        return deletedRows > 0
    }

    private fun createTextFile() {
        createFileLauncher.launch("example.txt")
    }

    private fun saveTextToFile(uri: Uri) {
        contentResolver.openOutputStream(uri)?.use { outputStream ->
            BufferedWriter(OutputStreamWriter(outputStream)).use { writer ->
                writer.write("Hello SMB116 !")
            }
        }
    }

    private fun openTextFile() {
        openFileLauncher.launch(arrayOf("text/plain"))
    }

    private fun takePersistablePermission(uri: Uri) {
        try {
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
            Log.d("Permission", "Permission persistante accordée pour l'URI : $uri")
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.e("Permission", "Impossible de prendre la permission persistante : $e")
        }
    }

    private fun readTextFromFile(uri: Uri): String? {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getCountFromPreferences(): Int {
        return sharedPreferences.getInt("count", 0)
    }

    private fun incrementCount() {
        val currentCount = sharedPreferences.getInt("count", 0)
        with(sharedPreferences.edit()) {
            putInt("count", currentCount + 1)
            apply()
        }
    }
}