package com.lee.cloudfunctiontest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.lee.cloudfunctiontest.Adapter.NotificationsAdapter
import com.lee.cloudfunctiontest.DTOs.NotificationDTO
import com.lee.cloudfunctiontest.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val tag = "MAINACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var notificationDTOList = ArrayList<NotificationDTO>()
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val notificationRef =
            db.collection("Notificaition")
        Log.d(tag + "레퍼런스: ", notificationRef.toString())
        notificationDTOList.clear()

        auth = FirebaseAuth.getInstance()
        val userUid = auth.currentUser?.uid

        Log.d(tag + ": UID" , userUid.toString())
        notificationRef
            .whereEqualTo("uid", userUid)
            .get()
            .addOnSuccessListener { documents ->

                Log.d(tag, documents.toString())
                for (datas in documents) {
                    val timestampDate = datas.get("time").toString().substring(18, 28)
                    val date = convertTimestampToString(timestampDate)
                    val content = datas.get("content").toString()
                    val notificationDTO = NotificationDTO(date, content)
                    Log.d(tag + ": Data" , notificationDTO.toString())
                    notificationDTOList.add(notificationDTO)

                    initRecycler()
                }
            }.addOnFailureListener {
                Log.d(tag, "알림을 읽지 못함")
            }
        initRecycler()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast

            Log.d(tag+ "TOKEN: ", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

    }

    private fun initRecycler() {
        binding.notificationsRecyclerview.adapter = NotificationsAdapter(notificationDTOList)
    }

    private fun convertTimestampToString(timestampDate: String): String {
        val dateFormat = SimpleDateFormat("MM/dd hh:mm")
        return dateFormat.format(Date(timestampDate.toLong() * 1000)).toString()
    }

    private fun refreshData() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
