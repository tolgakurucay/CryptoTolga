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
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentLoginBinding
import com.tolgakurucay.cryptotolga.viewmodel.LoginFragmentModel

private lateinit var binding: FragmentLoginBinding
private lateinit var auth:FirebaseAuth
private lateinit var viewModel:LoginFragmentModel



class LoginFragment : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()


        arguments?.let {

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




       /* if(auth.currentUser!=null){
            val action=LoginFragmentDirections.actionLoginFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(action)
        }*/


        binding.uyeOlButton.setOnClickListener {

            val action=LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.sifremiUnuttumTextView.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.girisYapButton.setOnClickListener {
            if(validateMailAndPassword()){
                Log.d("bilgi","Validasyon Geçildi")
                viewModel.signIn(binding.mailEditText.text.toString(), binding.passwordEditText.text.toString())
            }
            else
            {
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
                   val action=LoginFragmentDirections.actionLoginFragmentToFeedFragment()
                   Navigation.findNavController(this.requireView()).navigate(action)
               }
               else
               {
                   Toast.makeText(this@LoginFragment.context,"Mail ve-veya Şifre Hatalı",Toast.LENGTH_SHORT).show()
               }

           }
        })
    }

    private fun mailTextChangeListener(){
        binding.mailEditText.addTextChangedListener{
            if (binding.mailEditText.text.toString()==""){
                binding.textInputLayoutMail.helperText="Mail Gir"
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.mailEditText.text.toString()).matches()!=true){
                binding.textInputLayoutMail.helperText="Geçersiz"
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
                binding.textInputLayoutPassword.helperText="Boş Şifre"
            }
            else if(binding.passwordEditText.text.toString().length<8){
                binding.textInputLayoutPassword.helperText="En Az 8 Karakter"
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