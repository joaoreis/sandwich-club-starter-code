package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    JsonUtils() {
        throw new IllegalStateException("Utils class, should not be instantiated.");
    }

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichData;

        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = new ArrayList<>();

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
                        for (int i = 0; i < akaJsonArray.length(); i++) {
                            alsoKnownAs.add(akaJsonArray.getString(i));
                        }
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
                        JSONArray ingredientsArray = sandwichData.getJSONArray(key);
                        for (int i = 0; i < ingredientsArray.length(); i++) {
                            ingredients.add(ingredientsArray.getString(i));
                        }
                        break;

                    default:
                        throw new JSONException("Failed to parse Json");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
