package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvAlsoKnownAs;
    private TextView tvOrigin;
    private TextView tvIngredients;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);

        // tvDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(sandwich.getAlsoKnownAs().size() == 0){
            tvAlsoKnownAs.setVisibility(View.INVISIBLE);
            findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        }

        tvAlsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs() ));
        tvOrigin.setText(sandwich.getPlaceOfOrigin());
        tvIngredients.setText(TextUtils.join(", ", sandwich.getIngredients() ));
        tvDescription.setText(sandwich.getDescription());
    }
}
