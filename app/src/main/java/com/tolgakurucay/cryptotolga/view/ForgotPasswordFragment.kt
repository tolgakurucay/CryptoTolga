package com.tolgakurucay.cryptotolga.view

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.forgotMailLayout.helperText="Mail Giriniz"

        binding.sifremiUnuttumButton.setOnClickListener {
            if(validateField()==true){
                Log.d("bilgi","Mail Doğrulandı")
                viewModel.forgotPassword(binding.forgotMailText.text.toString())
            }
            else
            {
                Log.d("bilgi","Mail Doğrulanmadı")
            }
        }



        watcher()
        observeLiveDate()

    }

    private fun watcher(){
        binding.forgotMailText.addTextChangedListener {
            if(binding.forgotMailText.text.toString().isEmpty()){
                binding.forgotMailLayout.helperText="Mail Adresi Giriniz"
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.forgotMailText.text.toString()).matches()!=true)
            {
                binding.forgotMailLayout.helperText="Mail Adres Formatı Yanlış"
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
                    Log.d("bilgi","Mail'e gönderildi")
                    val action=ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
                    Navigation.findNavController(this.requireView()).navigate(action)
                }
                else
                {
                    Log.d("bilgi","Mail'e gönderilemedi")
                }
            }
        })

    }


}