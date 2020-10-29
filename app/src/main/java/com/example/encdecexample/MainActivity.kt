package com.example.encdecexample

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity() {
//    private val REQUEST_IMAGE_CAPTURE: Int = 100
    private val link = "https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val streamThread = Thread {
            val url = URL(link)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            Log.i("encrypting","done 1 ")


            val fis: InputStream = connection.inputStream
            Log.i("encrypting","done 2 ")
            val bitmap = decrypt(fis)
            Log.i("encrypting","done 3 ")

            runOnUiThread {
                encImage.setImageBitmap(bitmap)
                Log.i("encrypting","done 4 ")

            }
            fis.close()
            connection.disconnect()

        }
        streamThread.start()


//        val thread = Thread {
//            try {
//                "https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ".saveTo(
//                    Environment.getExternalStorageDirectory().toString()+"/alutheka.crypt/"
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        thread.start()

//
//        val thread = Thread {
//            try {
//                val downloadmanager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                val uri: Uri = Uri.parse("https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ")
//
//                val request = DownloadManager.Request(uri)
//                request.setTitle("new1")
//                request.setDescription("Downloading")
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                request.setVisibleInDownloadsUi(false)
//                request.setDestinationUri(Uri.parse("file://"+ Environment.getExternalStorageDirectory().toString()+"/new1.crypt/"))
//
//                downloadmanager.enqueue(request)
//            }catch (e :Exception){
//               Log.i("encrypting",e.toString())
//            }
//        }
//
//        thread.start()



//        getPermission()

//        val path = Environment.getExternalStorageDirectory().toString() +"/Eee.jpg/"
//        val file = File(path)
//
//        Log.i("encrypting", (file.length().toFloat()/1024).toString())
//
//
//        val compressedImageFile =  Compressor(this).compressToFile(file,"cpmpressed.jpg");
//        Log.i("encrypting", (compressedImageFile.length().toFloat()/1024).toString())



    }


//    private fun getPermission() {
//        //if the system is marshmallow or above get the run time permission
//        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
//            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
//        ) {
//            //permission was not enabled
//            val permission = arrayOf(
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//            //show popup to request permission
//            requestPermissions(permission, REQUEST_IMAGE_CAPTURE)
//
//        } else {
//            //permission already granted
////            val path = Environment.getExternalStorageDirectory().toString() +"/Eee.jpg/"
//
////            try {
////                val fileOutputStream = FileInputStream(path)
////                Log.i("encrypting", "image found")
////
////                val suc = Encrypt().encryptFile(
////                    fileOutputStream,
////                    Environment.getExternalStorageDirectory().toString()
////                )
////                Log.i("encrypting", suc.toString())
////
////            }catch (e: Exception){
////                Log.i("encrypting", e.toString())
////            }
//            try {
////                val suc = encryptFile(
////                    FileInputStream(path),
////                    Environment.getExternalStorageDirectory().toString()
////                )
//
////                val bitmap = decrypt(
////                    Environment.getExternalStorageDirectory().toString() + "/alutheka.crypt/"
////                )
////                encImage.setImageBitmap(bitmap)
//                Log.i(
//                    "encrypting", File(
//                        Environment.getExternalStorageDirectory().toString() + "/test123.crypt/"
//                    ).exists().toString()
//                )
//            }catch (e: Exception) {
//                Log.i("encrypting", e.toString())
//            }
//
//
//        }
//    }

//    @Throws(
//        IOException::class,
//        NoSuchAlgorithmException::class,
//        NoSuchPaddingException::class,
//        InvalidKeyException::class
//    )
//    private fun encryptFile(inputStream: InputStream, path: String): Boolean {
//        val fileOutputStream = FileOutputStream(
//            Environment.getExternalStorageDirectory().toString() + "/Eee.crypt/"
//        )
//        var key: ByteArray = ("123456789").toByteArray(charset("UTF-8"))
//        val messageDigest = MessageDigest.getInstance("SHA-1")
//        key = messageDigest.digest(key)
//        key = key.copyOf(16)
//        val secretKeySpec = SecretKeySpec(key, "AES")
//        val cipher = Cipher.getInstance("AES")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
//        val cipherOutputStream = CipherOutputStream(fileOutputStream, cipher)
//        var b: Int
//        val d = ByteArray(8)
//        while (inputStream.read(d).also { b = it } != -1) {
//            cipherOutputStream.write(d, 0, b)
//        }
//        cipherOutputStream.flush()
//        cipherOutputStream.close()
//        inputStream.close()
//        val encryptedFile = File(
//            Environment.getExternalStorageDirectory().toString() + "/Eee.crypt/"
//        )
//        return encryptedFile.exists()
//    }

    @Throws(
        IOException::class,
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class
    )
    fun decrypt(fis: InputStream): Bitmap? {
        var key: ByteArray = ("123456789").toByteArray(charset("UTF-8"))
        val sha = MessageDigest.getInstance("SHA-1")
        key = sha.digest(key)
        key = key.copyOf(16)
        val sks = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, sks)
        val cis = CipherInputStream(fis, cipher)
        val bitmap = BitmapFactory.decodeStream(cis)
        cis.close()
        return bitmap
    }


}