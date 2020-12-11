package com.example.tugasbesarrpl

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private var editTextName: EditText? = null
    private var editTextEmail:EditText? = null
    private var editTextPassword:EditText? = null
    private var editTextPhone:EditText? = null
    private var editTextAddress:EditText? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextName = findViewById(R.id.tv_name)
        editTextEmail = findViewById(R.id.tv_username)
        editTextPassword = findViewById(R.id.tv_password)
        editTextPhone = findViewById(R.id.tv_phone)
        editTextAddress = findViewById(R.id.tv_address)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth!!.currentUser != null) {
            //handle the already login user
        }
    }

    private fun registerUser() {
        val name = editTextName!!.text.toString()
        val email: String = editTextEmail?.text.toString().trim()
        val password: String = editTextPassword?.text.toString().trim()
        val phone: String = editTextPhone?.text.toString().trim()
        val address: String = editTextAddress?.text.toString().trim()

        if (email.isEmpty()) {
            editTextEmail?.error = "Input your email"
            editTextEmail?.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail?.error = "This email has already been used"
            editTextEmail?.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword?.error = "Input your password"
            editTextPassword?.requestFocus()
            return
        }
        if (name.isEmpty()) {
            editTextName?.error = "Input your name"
            editTextName?.requestFocus()
            return
        }
        if (phone.isEmpty()) {
            editTextPhone?.error = "Input your phone"
            editTextPhone?.requestFocus()
            return
        }
        if (address.isEmpty()) {
            editTextAddress?.error = "Input your address"
            editTextAddress?.requestFocus()
            return
        }
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(
                        name,
                        email,
                        phone,
                        address
                    )
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener(OnCompleteListener<Void?> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    getString(R.string.registration_success),
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {
                                //display a failure message
                            }
                        })
                } else {
                    Toast.makeText(this@SignUpActivity, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.btn_sign_up -> registerUser()
        }
    }
}

