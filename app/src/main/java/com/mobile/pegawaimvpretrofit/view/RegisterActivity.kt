package com.mobile.pegawaimvpretrofit.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobile.pegawai.model.ResultItem
import com.mobile.pegawaimvpretrofit.R
import com.mobile.pegawaimvpretrofit.presenter.edit.EditPresenter
import com.mobile.pegawaimvpretrofit.presenter.edit.EditView
import com.mobile.pegawaimvpretrofit.presenter.register.RegisterPresenter
import com.mobile.pegawaimvpretrofit.presenter.register.RegisterView
import com.mobile.pegawaimvpretrofit.utils.ImageAttachmentListener
import com.mobile.pegawaimvpretrofit.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterActivity : AppCompatActivity(), ImageAttachmentListener, RegisterView, EditView {

    var photo: File? = null;
    var imageUtils: ImageUtils? = null

    //todo 6 (register) deklarasi presenter
    var registerPresenter: RegisterPresenter? = null

    //todo 5(edit) deklarasi presenter
    var editPresenter:EditPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val getData = intent.getParcelableExtra<ResultItem>("data")
        if (getData != null) {
            idUser.setText(getData.id_user)
            edtNama.setText(getData.nama)
            edtEmail.setText(getData.email)
            edtPassword.setText(getData.password)
            val Contants = "http://10.124.4.135/Pegawai/foto_user/"
            Glide.with(this)
                .load(Contants + getData.photo)
                .apply(RequestOptions().error(R.drawable.icon_nopic))
                .into(imgProfile)
            bttnSimpan.text = "Update"
        }

        //todo 7 (register) inisialisasi presenter
        registerPresenter = RegisterPresenter(this)

        //todo 6(edit) inisialisasi presenter
        editPresenter = EditPresenter(this)

        imageUtils = ImageUtils(this)

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
            }
        }

        imgPlus.setOnClickListener {
            imageUtils?.imagepicker(1)
        }

        bttnSimpan.setOnClickListener {
            if (photo != null) {
                //todo 1 (register) ambil view

                val requestBody = RequestBody.create(MediaType.parse("image/*"), photo)

                //id
                val requestBodyId: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    idUser.text.toString()
                )

                //nama
                val requestBodyNama: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtNama.text.toString()
                )

                //email
                val requestBodyEmail: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtEmail.text.toString()
                )

                //pass
                val requestBodyPas: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtPassword.text.toString()
                )

                //photo
                val multipartBodyPhoto =
                    MultipartBody.Part.createFormData("photo", photo?.name, requestBody)

                when (bttnSimpan.text) {
                    "Update" -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Update Data")
                            setMessage("Yakin mau mengupdate ?")
                            setPositiveButton("Update") { dialog, which ->
                                //todo 7(edit) eksekusi presenter
                                editPresenter?.edit(requestBodyId,requestBodyNama,requestBodyNama,requestBodyPas,multipartBodyPhoto)
                            }
                            setNegativeButton("Batal") { dialog, which ->
                                dialog.dismiss()
                            }.show()
                        }
                    }
                    else -> {
                        //todo 9 (register) eksekusi presenter
                        registerPresenter?.register(
                            requestBodyNama,
                            requestBodyEmail,
                            requestBodyPas,
                            multipartBodyPhoto
                        )
                    }
                }
            } else {
                toast("Photo tidak boleh kosong")
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUtils?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        imageUtils?.request_permission_result(requestCode, permissions, grantResults)
    }

    override fun image_attachment(from: Int, filename: String?, file: Bitmap?, uri: Uri?) {
        imgProfile.setImageBitmap(file)

        val path: String? = imageUtils?.getPath(uri)
        photo = File(path)
    }

    //todo 8 (register) implementasi presenter
    override fun onSuccess(message: String) {
        toast("Berhasil")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //todo 6(edit) inisialisasi presenter
    override fun onSuccessEdit(message: String) {
        toast("Berhasil")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onErrorEdit(message: String) {
        toast("Berhasil")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}