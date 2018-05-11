package com.plexosysconsult.homemart;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by senyer on 9/4/2016.
 */
public class UsefulFunctions {

    Context context;


    public UsefulFunctions(Context c){

        context = c;
    }

    public String stripHtml(String html) {

        if (Build.VERSION.SDK_INT < 24) {

            return Html.fromHtml(html).toString();
        } else {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        }
    }

    public void mCreateAndSaveFile(String fileName, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/" + context.getApplicationContext().getPackageName() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String mReadJsonData(String fileName) {
        try {
            File f = new File("/data/data/" + context.getApplicationContext().getPackageName() + "/" + fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);

            return mResponse;


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkForJsonFile(String fileName) {

        File f = new File("/data/data/" + context.getApplicationContext().getPackageName() + "/" + fileName);

        if (f.exists()) {

            return true;
        } else {
            return false;
        }


    }

}
