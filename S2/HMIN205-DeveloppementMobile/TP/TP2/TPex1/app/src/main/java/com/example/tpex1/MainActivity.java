package com.example.tpex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String nom, prenom, telephone;
    EditText mEditTextNom, mEditTextPrenom, mEditTextTelephone;
    TextView textViewCompteur;
    int COUNTER;
    Button mBouton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCompteur = findViewById(R.id.textViewCompteur);
        mEditTextNom = findViewById(R.id.editText1);
        mEditTextPrenom = findViewById(R.id.editText2);
        mEditTextTelephone = findViewById(R.id.editText3);

        //reLoad content from old instance
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("textView_nom")
                && savedInstanceState.containsKey("textView_prenom")
                && savedInstanceState.containsKey("textView_telephone")
                && savedInstanceState.containsKey("textView_counter"))
        {

            nom = savedInstanceState.getString("textView_nom");
            prenom = savedInstanceState.getString("textView_prenom");
            telephone = savedInstanceState.getString("textView_telephone");
            COUNTER = savedInstanceState.getInt("textView_counter") + 1;

            mEditTextNom.setText(nom);
            mEditTextPrenom.setText(prenom);
            mEditTextTelephone.setText(telephone);
            textViewCompteur.setText(String.valueOf(COUNTER));
            Toast.makeText(this, "Ancienne instance", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Nouvelle instance", Toast.LENGTH_SHORT).show();
        }

        mBouton = (Button) findViewById(R.id.button);
        mBouton.setOnClickListener(myOnClickListener);
    };

    private View.OnClickListener myOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(mEditTextNom.getText().toString().matches("")
                    || mEditTextPrenom.getText().toString().matches("")
                    || mEditTextTelephone.getText().toString().matches("")){
                return;
            }

            Intent intent = new Intent(MainActivity.this, ListeInfosActivity.class);

            nom = mEditTextNom.getText().toString();
            prenom = mEditTextPrenom.getText().toString();
            telephone = mEditTextTelephone.getText().toString();
            COUNTER = Integer.parseInt(textViewCompteur.getText().toString());

            intent.putExtra("text_nom", mEditTextNom.getText().toString());
            intent.putExtra("text_prenom", mEditTextPrenom.getText().toString());
            intent.putExtra("text_telephone", mEditTextTelephone.getText().toString());
            intent.putExtra("text_COUNTER", textViewCompteur.getText().toString());

            addContactToPhone(nom, prenom, telephone);

            startActivityForResult((intent), 999);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("textView_nom",nom );
        savedInstanceState.putString("textView_prenom", prenom);
        savedInstanceState.putString("textView_telephone", telephone);
        savedInstanceState.putInt("textView_counter", COUNTER);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 999 && resultCode == RESULT_OK){
            COUNTER = Integer.parseInt(data.getStringExtra("text_COUNTER")) + 1;
            System.out.println(COUNTER);
            textViewCompteur.setText(String.valueOf(COUNTER));
        }
    }

    public void addContactToPhone(String nom, String prenom, String telephone){
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nom+ " " + prenom)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, nom)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, prenom)
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone)
                .build());

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            Log.e("getContentResolver() : ", e.getMessage());
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            // TODO Auto-generated catch block
            Log.e("getContentResolver() : ", e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            Log.e("getContentResolver() : ", e.getMessage());
        }
    }

}
