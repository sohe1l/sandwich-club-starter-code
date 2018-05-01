package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {


        try{
            JSONObject jsonObj = new JSONObject(json);

            return new Sandwich(
                    jsonObj.getJSONObject("name").getString("mainName"),
                    toArrayList ( jsonObj.getJSONObject("name").getJSONArray("alsoKnownAs") ),
                    jsonObj.getString("placeOfOrigin"),
                    jsonObj.getString("description"),
                    jsonObj.getString("image"),
                    toArrayList( jsonObj.getJSONArray("ingredients") )
                    );

        }catch(Exception e){
            return null;
        }

    }

    private static ArrayList<String> toArrayList(JSONArray jsonArray) throws JSONException{

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }

        return result;
    }

}
