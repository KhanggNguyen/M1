package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity<adapter> extends AppCompatActivity {
    EditText mEditTextNom, mEditTextPrenom, mEditTextAge, mEditTextTelephone, mEditTextCompetences;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner mLanguage = (Spinner) findViewById(R.id.spLanguage);
        final ArrayAdapter mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        mEditTextNom = (EditText) findViewById(R.id.editText);
        mEditTextPrenom = (EditText) findViewById(R.id.editText2);
        mEditTextAge = (EditText) findViewById(R.id.editText3);
        mEditTextTelephone = (EditText) findViewById(R.id.editText6);
        mEditTextCompetences = (EditText) findViewById(R.id.editText5);
        final Button mBouton = (Button) findViewById(R.id.button);

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String lang = mLanguage.getItemAtPosition(i).toString();
                String languageToLoad = null;
                switch (i) {
                    case 0:
                        languageToLoad="fr";
                        break;
                    case 1:
                        languageToLoad="en";
                        break;
                }

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                mEditTextNom.setHint(getResources().getString(R.string.nom_key));
                mEditTextPrenom.setHint(getResources().getString(R.string.prenom_key));
                mEditTextAge.setHint(getResources().getString(R.string.age_key));
                mEditTextTelephone.setHint(getResources().getString(R.string.telephone_key));
                mEditTextCompetences.setHint(getResources().getString(R.string.competences_key));
                mBouton.setHint(getResources().getString(R.string.competences_key));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBouton.setOnClickListener(myOnClickListener);


    }

    private View.OnClickListener myOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.app_name);
            builder.setMessage(getResources().getString(R.string.Confirmation_message_key));

            builder.setPositiveButton(getResources().getString(R.string.yes_key), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    Intent intent = new Intent(MainActivity.this, ShowInfoActivity.class);
                    intent.putExtra("text_nom", mEditTextNom.getText().toString());
                    intent.putExtra("text_prenom", mEditTextPrenom.getText().toString());
                    intent.putExtra("text_age", mEditTextAge.getText().toString());
                    intent.putExtra("text_competences", mEditTextCompetences.getText().toString());
                    intent.putExtra("text_telephone", mEditTextTelephone.getText().toString());

                    startActivity(intent);
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no_key), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }
    };


}
