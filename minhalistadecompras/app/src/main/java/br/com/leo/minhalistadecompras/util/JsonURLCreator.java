package br.com.leo.minhalistadecompras.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonURLCreator {

    public static String urlCreator(Object object){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(object);
    }

}
