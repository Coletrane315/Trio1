package com.example.trio1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trio1.databinding.ActivityMainBinding;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
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
    TextView restIDField;
    TextView restPhotoMetaField;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        com.example.trio1.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Places.initialize(getApplicationContext(), "AIzaSyBl1AN-bqofscbl7mm9irwfIwIAolJGHYI");
        PlacesClient placeClient = Places.createClient(this);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        searchField = findViewById(R.id.searchbar);
        restNameField = findViewById(R.id.restName);
        restAddressField = findViewById(R.id.restAddress);
        restRatingField = findViewById(R.id.restRating);
        restIDField = findViewById(R.id.restID);
    }

    protected void onStart(){
        super.onStart();
        searchField.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.RATING, Place.Field.ID, Place.Field.PHOTO_METADATAS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(searchRestaurant.this);
            startActivityForResult(intent, 100);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            restNameField.setText(place.getName());
            restAddressField.setText(place.getAddress());
            restRatingField.setText(String.valueOf(place.getRating()));
            restIDField.setText(place.getId());
        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            assert data != null;
            Status status =  Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
