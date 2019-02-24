package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView tvAlsoKnown;
    TextView tvOrigin;
    TextView tvDescription;
    TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        initializeViews();
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.sad_sandwhich)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initializeViews() {
        tvAlsoKnown = findViewById(R.id.tv_also_known);
        tvOrigin = findViewById(R.id.tv_origin);
        tvDescription = findViewById(R.id.tv_description);
        tvIngredients = findViewById(R.id.tv_ingredients);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String alsoKnownText = getStringFromList(sandwich.getAlsoKnownAs());
        showStringOrError(tvAlsoKnown, alsoKnownText, R.string.error_no_other_names);

        String ingredientsText = getStringFromList(sandwich.getIngredients());
        showStringOrError(tvIngredients, ingredientsText, R.string.error_no_ingredients);

        showStringOrError(tvOrigin, sandwich.getPlaceOfOrigin(), R.string.error_no_place_of_origin);

        showStringOrError(tvDescription, sandwich.getDescription(), R.string.error_no_description);

    }

    private void showStringOrError(TextView textView, String shownString, int resId) {
        if ("".equals(shownString)) {
            textView.setText(getText(resId));
        } else {
            textView.setText(shownString);
        }
    }

    private String getStringFromList(List<String> list) {
        if (list.isEmpty())
            return "";
        else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i));
                builder.append(", ");
            }

            builder.deleteCharAt(builder.length() - 2); //removing last comma
            return builder.toString();
        }

    }

}
