package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmptyActivity extends AppCompatActivity {
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Intent intent = getIntent();

        TextView textViewTelephone = findViewById(R.id.textViewTelephoneEmpty);
        Button mButtonAppeler = findViewById(R.id.buttonAppeler);

        phone = intent.getStringExtra("text_telephone");
        textViewTelephone.setText(intent.getStringExtra("text_telephone"));
        mButtonAppeler.setText(getResources().getString(R.string.appeler_key));

        mButtonAppeler.setOnClickListener(myOnClickAppelerListener);

    }

    private View.OnClickListener myOnClickAppelerListener = new View.OnClickListener() {
        @SuppressLint("MissingPermission")
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone ));
            startActivity(intent);
        }
    };
}
