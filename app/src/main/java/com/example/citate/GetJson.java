package com.example.citate;

import android.util.Log;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;



public class GetJson {

    public static ArrayList<String[]> getAllQuotes(Context context, String fileName, String[] authorsNames) {

        ArrayList<String[]> allQuotes = new ArrayList<String[]>();
        InputStream jsonData = context.getResources().openRawResource(R.raw.quotes_main);

        Reader reader = new InputStreamReader(jsonData);
        BufferedReader streamReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        for(String authorName : authorsNames) {
            String inputStr;
            try {
                while ((inputStr = streamReader.readLine()) != null) {
                    stringBuilder.append(inputStr);
                }

                JSONObject json = new JSONObject(stringBuilder.toString());
                JSONArray resArrJson = json.getJSONArray(authorName);
                int len = resArrJson.length();
                String[] resArr = new String[len];

                // JSONArray to String[]
                for (int i = 0; i < len; ++i) {
                    resArr[i] = resArrJson.getString(i);
                }
                allQuotes.add(resArr);
            }
            catch(Exception e) {
                e.printStackTrace();
                Log.d("Error", e.toString());
            }
        }

        return allQuotes;
    }

}
