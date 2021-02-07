package com.example.adivinanumero;

import android.graphics.Bitmap;

public class Result {
    public String name;
    public String tries;
    public String time;
    public Bitmap photo;

    public Result(String name, String tries, String time, Bitmap photo){
        this.name = name;
        this.tries = tries;
        this.time = time;
        this.photo = photo;
    }

    public String getName(){
        return name;
    }
    public String getTries(){
        return tries;
    }
    public String getTime(){
        return time;
    }
}
