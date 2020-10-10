package com.mobile.pegawaimvpretrofit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mobile.pegawai.model.ResultItem
import com.mobile.pegawaimvpretrofit.R
import com.mobile.pegawaimvpretrofit.adapter.MainAdapter
import com.mobile.pegawaimvpretrofit.presenter.delete.DeletePresenter
import com.mobile.pegawaimvpretrofit.presenter.delete.DeleteView
import com.mobile.pegawaimvpretrofit.presenter.select.SelectPresenter
import com.mobile.pegawaimvpretrofit.presenter.select.SelectView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SelectView, DeleteView {

    //todo 5 (select) deklarasi presenter
    var selectPresenter:SelectPresenter?=null

    //todo 5(delete) deklarasi presenter
    var deletePresenter:DeletePresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo 6 (select) inisaliasasi presenter
        selectPresenter = SelectPresenter(this)

        //todo 6(delete) inisaliasasi presenter
        deletePresenter = DeletePresenter(this)

        //todo 8 (select) eksekusi presenter
        selectPresenter?.select()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //todo 7 (select) implementasi presenter
    override fun onSuccess(message: String, result: List<ResultItem?>?) {
        val adapter = MainAdapter(result,object : MainAdapter.onClickListener{
            override fun detail(item: ResultItem?) {
                toast("Detail")
            }

            override fun hapus(item: ResultItem?) {

                AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Hapus Data")
                    setMessage("Yakin mau menghapus ?")
                    setPositiveButton("Hapus") { dialog, which ->
                        //todo 8(delete) eksekusi presenter
                        deletePresenter?.delete(item?.id_user)
                    }
                    setNegativeButton("Batal") { dialog, which ->
                        dialog.dismiss()
                    }.show()
                }
            }

            override fun edit(item: ResultItem?) {
                val intent = Intent(this@MainActivity,RegisterActivity::class.java)
                intent.putExtra("data",item)
                startActivity(intent)
            }

        })
        rvList.adapter = adapter
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    fun toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //todo 7(delete) implementasi presenter
    override fun onSuccessDelete(message: String) {
        toast("Berhasil")
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onErrorDelete(message: String) {
        toast("Gagal")
    }
}