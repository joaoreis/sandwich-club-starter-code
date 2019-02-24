package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    private static final String KEY_NAME = "name";
    private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";

    private static final String ERROR_PARSING_JSON = "Failed to parse Json";
    private static final String ERROR_INSTANTIATING_UTILS_CLASS = "Utils class, should not be instantiated.";


    JsonUtils() {
        throw new IllegalStateException(ERROR_INSTANTIATING_UTILS_CLASS);
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
                    case JsonUtils.KEY_NAME:
                        JSONObject nameJsonObject = sandwichData.getJSONObject(key);
                        JSONArray akaJsonArray = nameJsonObject.getJSONArray(KEY_ALSO_KNOWN_AS);

                        mainName = nameJsonObject.getString(KEY_MAIN_NAME);
                        for (int i = 0; i < akaJsonArray.length(); i++) {
                            alsoKnownAs.add(akaJsonArray.getString(i));
                        }
                        break;

                    case KEY_PLACE_OF_ORIGIN:
                        placeOfOrigin = sandwichData.getString(KEY_PLACE_OF_ORIGIN);
                        break;

                    case KEY_DESCRIPTION:
                        description = sandwichData.getString(KEY_DESCRIPTION);
                        break;

                    case KEY_IMAGE:
                        image = sandwichData.getString(KEY_IMAGE);
                        break;

                    case KEY_INGREDIENTS:
                        JSONArray ingredientsArray = sandwichData.getJSONArray(key);
                        for (int i = 0; i < ingredientsArray.length(); i++) {
                            ingredients.add(ingredientsArray.getString(i));
                        }
                        break;

                    default:
                        throw new JSONException(ERROR_PARSING_JSON);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
