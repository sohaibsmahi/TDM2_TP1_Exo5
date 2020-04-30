package com.example.tdm2_tp1_exo5

import ListAdapter
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var contacts: MutableList<Contact> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {


            }


            var list = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            if (list != null) {
                while (list.moveToNext()) {
                    val name =
                        list.getString(list.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phone =
                        list.getString(list.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    var contact = Contact(name, phone)
                    contacts.add(contact)
                }
                recyclerView.adapter = ListAdapter(contacts as ArrayList<Contact>)
                list.close()
            }
        }
        spam.setOnClickListener() {
            var sms = SmsManager.getDefault()
            for (item in contacts){
                sms.sendTextMessage(item.phone, null, "Message de publicité !", null,null)
            }
        }
    }

}
