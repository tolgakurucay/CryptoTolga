package com.tolgakurucay.cryptotolga.view

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
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentLoginBinding
import com.tolgakurucay.cryptotolga.view.activities.MainActivity
import com.tolgakurucay.cryptotolga.viewmodel.LoginFragmentModel

private lateinit var binding: FragmentLoginBinding
private lateinit var auth:FirebaseAuth
private lateinit var viewModel:LoginFragmentModel



class LoginFragment : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null && auth.currentUser!!.isEmailVerified){


            val intent=Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment




        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        binding= FragmentLoginBinding.bind(view)

        viewModel=ViewModelProvider(this).get(LoginFragmentModel::class.java)

        binding.textInputLayoutMail.helperText="Mail Giriniz"
        binding.textInputLayoutPassword.helperText="Şifre Giriniz"







        binding.uyeOlButton.setOnClickListener {

            val action=LoginFragmentDirections.actionLoginFragment2ToSignupFragment2()
            Navigation.findNavController(it).navigate(action)
        }

        binding.sifremiUnuttumTextView.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragment2ToForgotPasswordFragment2()
            Navigation.findNavController(it).navigate(action)
        }

        binding.girisYapButton.setOnClickListener {
            if(validateMailAndPassword()){
                Log.d("bilgi","Validasyon Geçildi")
                viewModel.signIn(binding.mailEditText.text.toString(), binding.passwordEditText.text.toString())
            }
            else
            {
                Toast.makeText(this.requireContext(),"Girdiğiniz Bilgileri Kontrol Ediniz",Toast.LENGTH_SHORT).show()
                Log.d("bilgi","Validasyon Hatalı")
            }

        }


        mailTextChangeListener()
        passwordChangeListener()

        observeLiveData()







    }

    private fun observeLiveData() {

        viewModel.bool.observe(viewLifecycleOwner, Observer {
           it?.let {

               if(it){
                   ///////
                       if(!auth.currentUser!!.isEmailVerified){
                           Log.d("bilgi","Email doğrulanmadı")
                          //e-mail doğrulansın
                           Toast.makeText(this@LoginFragment.requireContext(),"Lütfen Mail Adresinize Gönderilen Kodu Doğrulayın ",Toast.LENGTH_LONG).show()

                       }
                   else
                       {
                           Log.d("bilgi","Email doğrulandı")
                           
                           val intent=Intent(activity, MainActivity::class.java)
                           startActivity(intent)

                       }

               }
               else
               {
                   Log.d("bilgi","Mail ve-veya Şifre Hatalı")
                   Toast.makeText(this@LoginFragment.context,"Mail ve-veya Şifre Hatalı",Toast.LENGTH_SHORT).show()
               }

           }
        })
    }

    private fun mailTextChangeListener(){
        binding.mailEditText.addTextChangedListener{
            if (binding.mailEditText.text.toString()==""){
                binding.textInputLayoutMail.helperText="Mail Giriniz"
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.mailEditText.text.toString()).matches()!=true){
                binding.textInputLayoutMail.helperText="Geçersiz Mail"
            }
            else
            {
                binding.textInputLayoutMail.helperText=null
            }
        }
    }

    private fun passwordChangeListener(){
        binding.passwordEditText.addTextChangedListener {
            if(binding.passwordEditText.text.toString()==""){
                binding.textInputLayoutPassword.helperText="Şifre Giriniz"
            }

            else
            {
                binding.textInputLayoutPassword.helperText=null
            }
        }

    }

    private fun validateMailAndPassword():Boolean{
        return binding.textInputLayoutPassword.helperText==null && binding.textInputLayoutMail.helperText==null
    }




}