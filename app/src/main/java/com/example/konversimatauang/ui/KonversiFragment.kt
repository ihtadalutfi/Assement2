package com.example.konversimatauang.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.konversimatauang.R
import com.example.konversimatauang.databinding.FragmentKonversiBinding
import com.example.konversimatauang.model.MainViewModel
import com.example.konversimatauang.network.UangApi

const val KEY_HASIL = "hasil_key"
const val KEY_KONVERSI = "konversi_key"

class KonversiFragment : Fragment() {


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentKonversiBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentKonversiBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener { hitungUsd() }
        binding.negara.setOnClickListener { view ->
            view.findNavController().navigate(
            R.id.action_konversiFragment_to_negaraFragment
        )
        }
        if (savedInstanceState != null){
            binding.hasil.text = savedInstanceState.getString(KEY_HASIL)
            binding.konversiii.text = savedInstanceState.getString(KEY_KONVERSI)
            binding.button5.visibility = View.VISIBLE
        }

        binding.button5.setOnClickListener { share() }
        setHasOptionsMenu(true)
        return binding.root

        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }

    }

    private fun updateProgress(status: UangApi.ApiStatus) {
        when (status) {
            UangApi.ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            UangApi.ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            UangApi.ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val hsl = binding.hasil.text.toString()
        outState.putString(KEY_HASIL, hsl)
        val text = binding.konversiii.text.toString()
        outState.putString(KEY_KONVERSI, text)
    }


    private fun hitungUsd(){
        val idr = binding.uang.text.toString()
        if (TextUtils.isEmpty(idr)){
            Toast.makeText(context, R.string.idr_kosong, Toast.LENGTH_LONG).show()
            return
        }
        val usd = 14471
        val konversi = idr.toFloat()/ usd
        binding.hasil.text = getString(R.string.hasil, konversi)
        binding.konversiii.text = getString(R.string.idrusd)
        binding.button5.visibility = View.VISIBLE
    }

    private fun share (){
        val pesan = getString(R.string.bagikan_template, binding.uang.text,
            binding.konversiii.text, binding.hasil.text)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, pesan)
        if (shareIntent.resolveActivity(requireActivity().packageManager) !=null) {
            startActivity(shareIntent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about){
            findNavController().navigate(R.id.action_konversiFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}