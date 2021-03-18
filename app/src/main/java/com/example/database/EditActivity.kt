package com.example.database

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.database.db.MyDbManager
import com.example.database.db.MyIntentConstants
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {

    val imageRequestCode = 10
    var tempImageUrl = "empty"
    val myDbManager = MyDbManager(this)
    var id = 0
    var isEditState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntents()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == imageRequestCode)
        {
            im_Main.setImageURI(data?.data)
            tempImageUrl = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!,Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    fun onClickSave(view: View) {
        val myTitle = et_Title.text.toString()
        val myDisc = et_Discription.text.toString()

        if (myTitle!="" && myDisc!="") {
            if (isEditState) myDbManager.updateItemInDb(myTitle,myDisc,tempImageUrl, id)
            else myDbManager.insertToDb(myTitle,myDisc,tempImageUrl)
            finish()
        }


    }

    fun onClickAddImg(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        fb_addImg.visibility = View.GONE
    }

    fun onClickDeleteIm(view: View) {
        mainImageLayout.visibility = View.GONE
        fb_addImg.visibility = View.VISIBLE
    }

    fun onClickChangeIm(view: View)
    {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"

        startActivityForResult(intent, imageRequestCode)
    }

    fun onEditEnable(view: View)
    {
        et_Title.isEnabled = true
        et_Discription.isEnabled = true
        fb_addImg.visibility = View.VISIBLE
        fb_edit.visibility = View.GONE
    }

    fun getMyIntents()
    {
        val i = intent

        if (i != null) {
            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {

                fb_addImg.visibility = View.GONE
                et_Discription.setText(i.getStringExtra(MyIntentConstants.I_DISC_KEY))
                et_Title.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)
                isEditState = true
                et_Title.isEnabled = false
                et_Discription.isEnabled = false
                fb_edit.visibility = View.VISIBLE
                if (i.getStringExtra(MyIntentConstants.I_URL_KEY) != "empty") {
                    mainImageLayout.visibility = View.VISIBLE
                    imb_deleteImg.visibility = View.GONE
                    imb_editImage.visibility = View.GONE
                    im_Main.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.I_URL_KEY)))
                }
            }
        }
    }



}