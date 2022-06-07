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

        binding.layoutMail.helperText="Mail Giriniz"
        binding.layoutName.helperText="İsim Giriniz"
        binding.layoutPassword.helperText="Şifre Giriniz"
        binding.layoutPasswordTry.helperText="Şifreyi Tekrardan Giriniz"



        binding.uyeOlButton2.setOnClickListener {
           if(validation()){
               viewModel.signup(binding.signupName.text.toString(),binding.signupMail.text.toString(),binding.signUpPassword.text.toString())

           }
            else
           {
               Toast.makeText(this.context,"Lütfen Gerekli İşlemleri Yapın",Toast.LENGTH_SHORT).show()
               Log.d("bilgi","Lütfen Gerekli İşlemleri Yapın")
           }
        }




        textsChangeListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.signUp.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Log.d("bilgi","Kayıt Başarılı")
                    val action=SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                    Navigation.findNavController(this.requireView()).navigate(action)
                }
                else
                {
                    Log.d("bilgi","Başarısız")
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
                binding.layoutName.helperText="İsim Giriniz"
            }
            else
            {
                binding.layoutName.helperText=null
            }

        }
        binding.signupMail.addTextChangedListener {
            if (binding.signupMail.text.toString()==""){
                binding.layoutMail.helperText="Mail Gir"
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.signupMail.text.toString()).matches()!=true){
                binding.layoutMail.helperText="Geçersiz"
            }
            else
            {
                binding.layoutMail.helperText=null
            }

        }
        binding.signUpPassword.addTextChangedListener {
            if(binding.signUpPassword.text.toString()==""){
                binding.layoutPassword.helperText="Boş Şifre"
            }
            else if(binding.signUpPassword.text.toString().length<8){
                binding.layoutPassword.helperText="En Az 8 Karakter"
            }

            else
            {
                binding.layoutPassword.helperText=null
            }

        }
        binding.signUpPasswordTry.addTextChangedListener {
            if(binding.signUpPasswordTry.text.toString()==""){
                binding.layoutPasswordTry.helperText="Boş Şifre"
            }
            else if(binding.signUpPasswordTry.text.toString().length<8){
                binding.layoutPasswordTry.helperText="En Az 8 Karakter"
            }


            else
            {
                binding.layoutPasswordTry.helperText=null
            }

        }


    }





}