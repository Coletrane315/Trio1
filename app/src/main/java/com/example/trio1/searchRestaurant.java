package com.example.trio1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trio1.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class searchRestaurant extends AppCompatActivity {

    EditText searchField;
    TextView restNameField;
    TextView restAddressField;
    TextView restRatingField;
    String placeID;
    PlacesClient placeClient;
    ImageView resImage;
    Button reviewB;
    TextView temp1;
    TextView temp2;
    Place place;
    Button tempB;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.trio1.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Places.initialize(getApplicationContext(), "AIzaSyBl1AN-bqofscbl7mm9irwfIwIAolJGHYI");
        placeClient = Places.createClient(this);

        searchField = findViewById(R.id.searchbar);
        restNameField = findViewById(R.id.restName);
        restAddressField = findViewById(R.id.restAddress);
        restRatingField = findViewById(R.id.restRating);
        resImage = findViewById(R.id.restImage);
        resImage.setImageResource(0);
        reviewB = findViewById(R.id.reviewButton);
    }

    protected void onStart() {
        super.onStart();
        searchField.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.RATING, Place.Field.ID, Place.Field.PHOTO_METADATAS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(searchRestaurant.this);
            startActivityForResult(intent, 100);

        });

        reviewB.setOnClickListener(view -> {
            setContentView(R.layout.review_layout);
            temp1 = findViewById(R.id.textView4);
            temp2 = findViewById(R.id.textView5);
            if(placeID != null){
                temp1.setText(place.getName());
                temp2.setText(placeID);
            }else{
                temp1.setText(R.string.nullText);
                temp2.setText(R.string.nullText);
            }
            tempB = findViewById(R.id.backtoRestButton);
            tempB.setOnClickListener(view2 -> {
                setContentView(R.layout.search_fragment);
            });
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            assert data != null;
            place = Autocomplete.getPlaceFromIntent(data);
            restNameField.setText(place.getName());
            restAddressField.setText(place.getAddress());
            restRatingField.setText(String.valueOf(place.getRating()));
            placeID = place.getId();

            // Get the photo metadata.
            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                Log.w(TAG, "No photo metadata.");
                return;
            }
            final PhotoMetadata photoMetadata = metadata.get(0);

            // Create a FetchPhotoRequest.
            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxHeight(500)
                    .setMaxWidth(500)
                    .build();
            placeClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap bitmap = fetchPhotoResponse.getBitmap();
                resImage.setImageBitmap(bitmap);
            });

        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            assert data != null;
            Status status =  Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
