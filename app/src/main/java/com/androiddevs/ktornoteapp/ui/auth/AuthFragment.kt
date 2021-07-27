package com.androiddevs.ktornoteapp.ui.auth

import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.ui.BaseFragment
import androidx.fragment.app.Fragment


class AuthFragment : Fragment(R.layout.fragment_auth) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToNotesFragment())
        }
    }
}