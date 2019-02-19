package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichData = null;

        String mainName = "";
        List<String> alsoKnownAs = null;
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = null;

        try {
            sandwichData = new JSONObject(json);
            Iterator<String> keys = sandwichData.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                switch (key) {
                    case "name":
                        JSONObject nameJsonObject = sandwichData.getJSONObject(key);
                        JSONArray akaJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");

                        mainName = nameJsonObject.getString("mainName");
                        break;

                    case "placeOfOrigin":
                        placeOfOrigin = sandwichData.getString("placeOfOrigin");
                        break;

                    case "description":
                        description = sandwichData.getString("description");
                        break;

                    case "image":
                        image = sandwichData.getString("image");
                        break;

                    case "ingredients":
                        JSONArray ingredientList = sandwichData.getJSONArray(key);
                        break;
                }

                System.out.println(">>>>>>>   " + key);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
    }
}
