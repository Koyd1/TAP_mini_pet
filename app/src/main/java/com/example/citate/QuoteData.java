package com.example.citate;

import android.os.Parcelable;

import java.io.Serializable;

public class QuoteData implements Serializable {

    public String quoteText;
    public String[] quoteTags;
    public String authorName;

    public QuoteData(String quoteText, String[] quoteTags, String authorName) {
        this.quoteText = quoteText;
        this.quoteTags = quoteTags;
        this.authorName = authorName;
    }

}
