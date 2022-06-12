package com.tolgakurucay.cryptotolga.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentSignupBinding
import com.tolgakurucay.cryptotolga.viewmodel.SignupFragmentModel


class SignupFragment : Fragment() {

    private lateinit var binding:FragmentSignupBinding
    private lateinit var viewModel:SignupFragmentModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_signup, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentSignupBinding.bind(view)

        viewModel=ViewModelProvider(this).get(SignupFragmentModel::class.java)

        binding.layoutMail.helperText="Enter a Mail"
        binding.layoutName.helperText="Enter a Name"
        binding.layoutPassword.helperText="Enter a Password"
        binding.layoutPasswordTry.helperText="Enter Password Again"



        binding.uyeOlButton2.setOnClickListener {
           if(validation()){
               viewModel.signup(binding.signupName.text.toString(),binding.signupMail.text.toString(),binding.signUpPassword.text.toString())

           }
            else
           {
               Toast.makeText(this.context,"Check The Information You Have Entered",Toast.LENGTH_SHORT).show()

           }
        }




        textsChangeListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.signUp.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){



                    val alertDialog= AlertDialog.Builder(this@SignupFragment.requireContext())

                    alertDialog.setTitle("Register Successful")
                    alertDialog.setMessage("You can login to the application after clicking the verification link that comes to your e-mail address.")
                    alertDialog.setPositiveButton("Yes"){a,b->
                        val action=SignupFragmentDirections.actionSignupFragment2ToLoginFragment2()
                        Navigation.findNavController(this@SignupFragment.requireView()).navigate(action)



                    }

                    alertDialog.setIcon(R.drawable.mailbox)
                    alertDialog.create().show()



                }
                else
                {
                    Log.d("bilgi","Başarısız")
                    Toast.makeText(this@SignupFragment.requireContext(),"Register Failed\nCould Be An Account Registered With This Email Address",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validation() : Boolean{
        if(binding.signUpPassword.text.toString().equals(binding.signUpPasswordTry.text.toString())){
            val name=binding.layoutName.helperText
            val mail=binding.layoutMail.helperText
            val password=binding.layoutPassword.helperText
            val passwordTry=binding.layoutPasswordTry.helperText

            return name==null && mail==null && password==null && passwordTry==null
        }
        else
        {
            return false
        }
    }

    private fun textsChangeListener(){

        binding.signupName.addTextChangedListener {
            if(binding.signupName.text.toString().isEmpty()){
                binding.layoutName.helperText="Enter a Name"
            }
            else
            {
                binding.layoutName.helperText=null
            }

        }
        binding.signupMail.addTextChangedListener {
            if (binding.signupMail.text.toString()==""){
                binding.layoutMail.helperText="Enter a Mail"
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.signupMail.text.toString()).matches()!=true){
                binding.layoutMail.helperText="Invalid Mail"
            }
            else
            {
                binding.layoutMail.helperText=null
            }

        }
        binding.signUpPassword.addTextChangedListener {
            if(binding.signUpPassword.text.toString()==""){
                binding.layoutPassword.helperText="Enter a Password"
            }
            else if(binding.signUpPassword.text.toString().length<8){
                binding.layoutPassword.helperText="Minimum 8 Character"
            }

            else
            {
                binding.layoutPassword.helperText=null
            }

        }
        binding.signUpPasswordTry.addTextChangedListener {
            if(binding.signUpPasswordTry.text.toString()==""){
                binding.layoutPasswordTry.helperText="Enter a Password"
            }
            else if(binding.signUpPasswordTry.text.toString().length<8){
                binding.layoutPasswordTry.helperText="Minimum 8 Character"
            }


            else
            {
                binding.layoutPasswordTry.helperText=null
            }

        }


    }






}
