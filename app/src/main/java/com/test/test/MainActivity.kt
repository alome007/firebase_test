package com.test.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.gson.Gson
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var firstName:EditText
    lateinit var lastName:EditText
    lateinit var saveData:Button
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var firebaseInstance:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        saveData.setOnClickListener{
            var users = users(firstName.text.toString(),lastName.text.toString(), email.text.toString(), password.text.toString())
            firebaseInstance.collection("users").document(email.text.toString()).set(users)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, it.message,  Toast.LENGTH_LONG).show()
                    }
        }
        readData()
    }

    fun initUI(){
        firstName = findViewById(R.id.f_name);
        lastName = findViewById(R.id.l_name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        saveData = findViewById(R.id.save);
        firebaseInstance = FirebaseFirestore.getInstance()
    }

    fun readData(){
        firebaseInstance.collection("users").document("astroboy@gmail.com")
                .addSnapshotListener{ documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                    var user = documentSnapshot?.toObject(users::class.java)
                    Log.d("UserData", user?.firstName.toString())
                }
    }
}