package com.example.quoteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)

        setQuote(mainViewModel.getQuote())

        binding.next.setOnClickListener{
            if(mainViewModel.index < mainViewModel.quoteList.size - 1) {
                setQuote(mainViewModel.nextQuote())
            }
        }

        binding.back.setOnClickListener{
            if(mainViewModel.index > 0) {
                setQuote(mainViewModel.previousQuote())
            }
            else{
                Toast.makeText(this@MainActivity, "You reached at the Start", Toast.LENGTH_SHORT).show()
            }
        }

        binding.share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().text)
            startActivity(intent)
        }

    }

    private fun setQuote(quote: Quotes) {

        binding.txtQuote.text = quote.text
        binding.txtAuthor.text = quote.author

    }
}