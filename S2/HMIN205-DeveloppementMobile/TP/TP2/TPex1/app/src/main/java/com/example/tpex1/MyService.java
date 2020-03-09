package com.example.tpex1;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyService extends IntentService {
    private static final int READ_REQUEST_CODE = 42;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;

    DatabaseHelper db;

    public MyService() {
        super("MyService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        db = new DatabaseHelper(this);

        String nomFichier = ListeInfosActivity.FILE_NAME;
        String myStringLine[] = openFile(getFilesDir() + "/" + nomFichier).split("\n");
        for(int i=0; i<myStringLine.length; i++){
            String data[] = myStringLine[i].split("-");
            if(!db.isExist(data[0]+ " " + data[1], data[2])){
                Android_Contact contact = new Android_Contact(data[0].trim() + " " + data[1].trim(), data[2].trim());
                db.addContact(contact);
            }
        }
    }

    private String openFile(String file_name){
        File file = new File(file_name);
        StringBuilder my_text_builder =  new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                my_text_builder.append(line);
                my_text_builder.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return my_text_builder.toString();
    }

}
