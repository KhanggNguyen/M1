package com.example.tpex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListeInfosActivity extends AppCompatActivity {
    String FILE_NAME = "myContact.txt";
    String nom, prenom, telephone;
    String COUNTER;
    TextView textViewNom, textViewPrenom, textViewTelephone;
    ListView listView_Android_Contacts;
    DatabaseHelper db;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_infos);

        Intent intent = getIntent();

        COUNTER = intent.getStringExtra("text_COUNTER");

        //initialiser les variables
        nom = intent.getStringExtra("text_nom");
        prenom = intent.getStringExtra("text_prenom");
        telephone = intent.getStringExtra("text_telephone");



        //ouvrir un fichier et ecrire dessus
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            Toast.makeText(this, "Fichier créé à " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT).show();
            System.out.println("Fichier créé à " + getFilesDir() + "/" + FILE_NAME);
            fos.write((nom + "\n").getBytes());
            fos.write((prenom + "\n").getBytes());
            fos.write((telephone + "\n").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        db = new DatabaseHelper(this);

        Android_Contact new_contact = new Android_Contact(nom+ " " + prenom, telephone);
        long res = db.addContact(new_contact);
        if(res == -1){
            Toast.makeText(this, "Ce numéro a été déjà ajouté!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ajouté succès!", Toast.LENGTH_SHORT).show();
        }

        //creer liste contact
        listView_Android_Contacts = findViewById(R.id.listview_Android_Contacts);
        get_Android_Contact();

        //récupérer les objets boutons
        Button mButtonOk = findViewById(R.id.buttonOk);
        Button mButtonRetour = findViewById(R.id.buttonRetour);

        mButtonOk.setText(getResources().getString(R.string.ok_key));
        mButtonRetour.setText(getResources().getString(R.string.back_key));

        mButtonOk.setOnClickListener(myOnClickModifierListener);

        mButtonRetour.setOnClickListener(myOnClickRetourListener);
    }

    private View.OnClickListener myOnClickModifierListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(ListeInfosActivity.this, "Cette fonction n'est pas encore implémenté", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener myOnClickRetourListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent();
            i.putExtra("text_COUNTER", COUNTER);
            setResult(RESULT_OK, i);
            finish();
        }
    };

    @SuppressLint("LongLogTag")
    public void get_Android_Contact() {
        ArrayList<Android_Contact> arrayList_Android_Contacts = new ArrayList<Android_Contact>();

        //récuperer tous les contacts
        Cursor cursor_Android_Contact = null;
        ContentResolver contentResolver = getContentResolver();
        try{
            cursor_Android_Contact = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch(Exception e){
            Log.e("Erreur de la liste contact ", e.getMessage());
        }

        //vérif contacts exists et initialisation
        if (cursor_Android_Contact.getCount() > 0){
            while (cursor_Android_Contact.moveToNext()){
                Android_Contact android_contact = new Android_Contact();

                //get
                String contact_id = cursor_Android_Contact.getString(cursor_Android_Contact.getColumnIndex(ContactsContract.Contacts._ID));
                String contact_nom = cursor_Android_Contact.getString(cursor_Android_Contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //String contact_prenom = cursor_Android_Contact.getString(cursor_Android_Contact.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                android_contact.android_contact_id = Integer.parseInt(contact_id);
                android_contact.android_conctact_nom = contact_nom;

                int hasPhone = Integer.parseInt(cursor_Android_Contact.getString(cursor_Android_Contact.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhone > 0){
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{contact_id},
                            null
                    );
                    while (phoneCursor.moveToNext()){
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex((ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        android_contact.android_contact_telephone = phoneNumber;
                    }
                    phoneCursor.close();
                }else{
                    android_contact.android_contact_telephone = "0";
                }
                //mettre dans la liste
                arrayList_Android_Contacts.add(android_contact);
            }
        }
        cursor_Android_Contact.close();

        //show results
        Adapter_for_android_contacts adapter = new Adapter_for_android_contacts(this, arrayList_Android_Contacts);

        listView_Android_Contacts.setAdapter(adapter);
        listView_Android_Contacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView_Android_Contacts.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textViewNom = (TextView) view.findViewById(R.id.textview_android_contact_nom);
                textViewTelephone = (TextView) view.findViewById(R.id.textview_android_contact_telephone);

                Android_Contact contact = new Android_Contact(textViewNom.getText().toString(), textViewTelephone.getText().toString());

                long res;
                res = db.addContact(contact);
                if( res == -1){
                    Toast.makeText(ListeInfosActivity.this, "Numéro existé", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ListeInfosActivity.this, "Ajouté", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public class Adapter_for_android_contacts extends BaseAdapter{
        Context mContext;
        List<Android_Contact> mList_Android_Contacts;

        public Adapter_for_android_contacts(Context mContext, List<Android_Contact> mContact) {
            this.mContext = mContext;
            this.mList_Android_Contacts = mContact;
        }

        @Override
        public int getCount() {
            return mList_Android_Contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return mList_Android_Contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //----< show items >----
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(mContext,R.layout.activity_liste_contact,null);

            //recuperer les controller textView
            textViewNom = (TextView) view.findViewById(R.id.textview_android_contact_nom);
            //textViewPrenom = findViewById(R.id.textview_android_contact_prenom);
            textViewTelephone = (TextView) view.findViewById(R.id.textview_android_contact_telephone);

            //< show values >
            textViewNom.setText(mList_Android_Contacts.get(position).android_conctact_nom);
            //textViewPrenom.setText(mList_Android_Contacts.get(position).android_contact_prenom);
            textViewTelephone.setText(mList_Android_Contacts.get(position).android_contact_telephone);
            //</ show values >

            view.setTag(mList_Android_Contacts.get(position).android_conctact_nom);
            return view;
        }
    }


}
