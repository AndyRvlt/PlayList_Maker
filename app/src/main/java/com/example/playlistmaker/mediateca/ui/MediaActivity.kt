package com.example.playlistmaker.mediateca.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator
    private lateinit var buttonArrowBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArrowBack = findViewById(R.id.arrowBack)

        buttonArrowBack.setOnClickListener {
            finish()
        }
        binding.viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)
//
        setContentView(binding.root)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Избраные треки"
                1 -> tab.text = "Плейлисты"
            }
        }
        tabMediator.attach()


    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}