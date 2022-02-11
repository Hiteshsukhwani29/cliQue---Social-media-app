package com.hitesh.clique

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : Fragment() {

    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var btn_login: Button

    lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance();
        et_email = v.findViewById(R.id.login_email);
        et_password = v.findViewById(R.id.login_password);
        btn_login = v.findViewById(R.id.loginbtn);
        db = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(View.OnClickListener { login() })

        return v
    }

    private fun login() {
        if (et_email.text.toString().isEmpty()) {
            et_email.setError("Email is required")
            et_email.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()) {
            et_email.setError("Please provide a valid email");
            et_email.requestFocus();
            return;
        }

        if (et_password.text.isEmpty()) {
            et_password.setError("Password is required");
            et_password.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
                .addOnCompleteListener { task ->

                    if (task.isSuccessful()) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container1, Home()).commit()
                    } else {
                        Toast.makeText(
                            getActivity(),
                            "Invalid email address or password",
                            Toast.LENGTH_LONG
                        ).show();

                    }

                }
        }
    }

}