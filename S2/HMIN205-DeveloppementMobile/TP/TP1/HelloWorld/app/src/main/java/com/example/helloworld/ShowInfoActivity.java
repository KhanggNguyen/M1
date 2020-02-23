package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowInfoActivity extends AppCompatActivity {
    String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        Intent intent = getIntent();

        TextView textViewNom = findViewById(R.id.textViewNom);
        TextView textViewPrenom = findViewById(R.id.textViewPrenom);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewCompetences = findViewById(R.id.textViewCompetences);
        TextView textViewTelephone = findViewById(R.id.textViewTelephone);
        textViewNom.setText(intent.getStringExtra("text_nom"));
        textViewPrenom.setText(intent.getStringExtra("text_prenom"));
        textViewAge.setText(intent.getStringExtra("text_age"));
        textViewCompetences.setText(intent.getStringExtra("text_competences"));
        textViewTelephone.setText(intent.getStringExtra("text_telephone"));
        telephone = intent.getStringExtra("text_telephone");

        Button mButtonOk =  findViewById(R.id.buttonOk);
        Button mButtonRetour = findViewById(R.id.buttonRetour);

        mButtonOk.setText(getResources().getString(R.string.ok_key));
        mButtonRetour.setText(getResources().getString(R.string.back_key));

        mButtonOk.setOnClickListener(myOnClickOkListener);

        mButtonRetour.setOnClickListener(myOnClickRetourListener);
    }

    private View.OnClickListener myOnClickOkListener = new View.OnClickListener() {
        public void onClick(View v) {

            Intent intent = new Intent(ShowInfoActivity.this, EmptyActivity.class);
            intent.putExtra("text_telephone", telephone);
            startActivity(intent);

        }
    };

    private View.OnClickListener myOnClickRetourListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent = new Intent(ShowInfoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };
}
