package com.example.study

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.example.study.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L;
    var pauseTime = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.start.setOnClickListener {
            binding.timer.base = SystemClock.elapsedRealtime() + pauseTime;
            binding.start.isEnabled = false;
            binding.stop.isEnabled = true;
            binding.reset.isEnabled = false;
            binding.timer.start();
        }
        binding.reset.setOnClickListener {
            pauseTime = 0L;
            binding.timer.base = SystemClock.elapsedRealtime();
            binding.start.isEnabled = true;
            binding.stop.isEnabled = false;
            binding.reset.isEnabled = false;
        }
        binding.stop.setOnClickListener {
            pauseTime = binding.timer.base - SystemClock.elapsedRealtime();
            binding.timer.stop();
            binding.start.isEnabled = true;
            binding.stop.isEnabled = false;
            binding.reset.isEnabled = true;
        }
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } */

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - initTime > 3000) {
                    Toast.makeText(this@MainActivity, "종료하려면 한 번 더 누르세요",
                        Toast.LENGTH_SHORT).show()
                    initTime = System.currentTimeMillis();
                }
                else {
                    finish();
                }
            }
        })
    }
}

