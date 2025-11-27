package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demo.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        Intent intent=getIntent();
        ActivityDetailBinding binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageView.setImageResource(intent.getIntExtra("IMAGE",R.mipmap.ic_launcher));
        int position=intent.getIntExtra("ID",0);
        String desc="up:"+position+"\n粉丝数：10086";
        binding.up.setText(desc);


        binding.btn.setOnClickListener(v->{
            setResult(RESULT_OK,new Intent().putExtra("ID",position));
            finish();
        });

        binding.back.setOnClickListener(v->finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}