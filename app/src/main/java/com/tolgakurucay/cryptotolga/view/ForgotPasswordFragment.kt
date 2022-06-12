package com.tolgakurucay.cryptotolga.view

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
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentForgotPasswordBinding
import com.tolgakurucay.cryptotolga.viewmodel.ForgotPasswordFragmentModel


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding:FragmentForgotPasswordBinding
    private lateinit var viewModel:ForgotPasswordFragmentModel

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

        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentForgotPasswordBinding.bind(view)

        viewModel=ViewModelProvider(this).get(ForgotPasswordFragmentModel::class.java)

        binding.forgotMailLayout.helperText="Enter a Mail"

        binding.sifremiUnuttumButton.setOnClickListener {
            if(validateField()==true){

                viewModel.forgotPassword(binding.forgotMailText.text.toString())
            }
            else
            {
                Toast.makeText(this@ForgotPasswordFragment.requireContext(),"Please Fill Required Field Properly",Toast.LENGTH_SHORT).show()
            }
        }



        watcher()
        observeLiveDate()

    }

    private fun watcher(){
        binding.forgotMailText.addTextChangedListener {
            if(binding.forgotMailText.text.toString().isEmpty()){
                binding.forgotMailLayout.helperText="Enter a Mail"
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(binding.forgotMailText.text.toString()).matches())
            {
                binding.forgotMailLayout.helperText="Invalid Mail"
            }
            else
            {
                binding.forgotMailLayout.helperText=null
            }
        }
    }

    private fun validateField() : Boolean{
        return binding.forgotMailLayout.helperText==null
    }

    private fun observeLiveDate(){

        viewModel.isMailTrue.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Toast.makeText(this@ForgotPasswordFragment.requireContext(),"Password Reset Link Sent to Email!",Toast.LENGTH_SHORT).show()
                    val action=ForgotPasswordFragmentDirections.actionForgotPasswordFragment2ToLoginFragment2()
                    Navigation.findNavController(this.requireView()).navigate(action)
                }
                else
                {
                    Toast.makeText(this@ForgotPasswordFragment.requireContext(),"There Is No Account Registered To This Email Address",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


}