package com.example.citate;

import android.util.Log;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import java.util.HashMap;

public class GetJson {

    public static ArrayList<QuoteData[]> getAllQuotes(Context context, String fileName, String[] authorsNames) throws IOException {

        ArrayList<QuoteData[]> allQuotes = new ArrayList<QuoteData[]>();
        InputStream jsonData = context.getResources().openRawResource(R.raw.quotes_main);

        Reader reader = new InputStreamReader(jsonData);
        BufferedReader streamReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for(String authorName : authorsNames) {
                String inputStr;

                while ((inputStr = streamReader.readLine()) != null) {
                    stringBuilder.append(inputStr);
                }

                JSONObject json = new JSONObject(stringBuilder.toString());
                JSONArray resArrJson = json.getJSONArray(authorName);
                int len = resArrJson.length();
                QuoteData[] resArr = new QuoteData[len];

                // JSONArray to String[]
                for (int i = 0; i < len; ++i) {
                    JSONObject quote = resArrJson.getJSONObject(i);
                    String quoteText = quote.getString("quote");
                    JSONArray quoteTags = quote.getJSONArray("tags");
                    int quoteTagsLen = quoteTags.length();
                    String[] quoteTagsArr = new String[quoteTagsLen];
                    for(int j = 0; j < quoteTagsLen; ++j) {
                        quoteTagsArr[j] = quoteTags.getString(j);
                    }
                    QuoteData quoteData = new QuoteData(quoteText, quoteTagsArr, authorName);
                    resArr[i] = quoteData;
                }
                allQuotes.add(resArr);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.d("Error", e.toString());
        }
        finally {
            streamReader.close();
            reader.close();
            jsonData.close();
        }

        return allQuotes;
    }

    public static ArrayList<String[]> getAllStartQuotes(Context context, String fileName, String[] authorsNames) throws IOException {

        ArrayList<String[]> allQuotes = new ArrayList<String[]>();
        InputStream jsonData = context.getResources().openRawResource(R.raw.quotes_start);

        Reader reader = new InputStreamReader(jsonData);
        BufferedReader streamReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for(String authorName : authorsNames) {
                String inputStr;

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

        }
        catch(Exception e) {
            e.printStackTrace();
            Log.d("Error", e.toString());
        }
        finally {
            streamReader.close();
            reader.close();
            jsonData.close();
        }

        return allQuotes;
    }

}
