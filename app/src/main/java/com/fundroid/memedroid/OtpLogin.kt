package com.fundroid.memedroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpLogin : AppCompatActivity() {
    //44:26

    lateinit var number:EditText
    lateinit var otp_Button:Button
    lateinit var auth:FirebaseAuth
    lateinit var callback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_login)
        number = findViewById(R.id.number)
        otp_Button = findViewById(R.id.otp_Button)

        auth = FirebaseAuth.getInstance()
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
               signIN(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

        }
        otp_Button.setOnClickListener {
            var mobile = number.text.toString()
            var updated_mobile ="+91$mobile"
            numberVerification(updated_mobile)
            Toast.makeText(this,"Wait",Toast.LENGTH_LONG).show()

        }


    }

    private fun signIN(p0: PhoneAuthCredential) {
        auth.signInWithCredential(p0).addOnCompleteListener(){
            if (it.isSuccessful){

                 startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else{

                Toast.makeText(this,"Invalid OTP Try Again",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun numberVerification(updatedMobile: String) {
        var option = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(updatedMobile)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callback)
            .build()
            PhoneAuthProvider.verifyPhoneNumber(option)



    }
}