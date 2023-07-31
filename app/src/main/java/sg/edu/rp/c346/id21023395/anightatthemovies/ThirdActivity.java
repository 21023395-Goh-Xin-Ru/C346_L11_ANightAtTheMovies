package sg.edu.rp.c346.id21023395.anightatthemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ThirdActivity extends AppCompatActivity {

    TextInputEditText id, etTitle, etGenre, etYear;
    Button btnUpdate, btnDelete, btnCancel;
    Spinner spnRating;
    Movie data;
    String[] ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        id = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etMovieTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        spnRating = findViewById(R.id.spnRating);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        id.setText(String.valueOf(data.getId()));
        id.setKeyListener(null);
        id.setTextColor(Color.GRAY);
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));

        ratings = getResources().getStringArray(R.array.spinnerItems);
        // Set up the adapter for the Spinner using the retrieved ratings array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRating.setAdapter(adapter);

        // Find the index of the rating in the adapter and set the selection of the Spinner
        int ratingIndex = adapter.getPosition(data.getRating());
        spnRating.setSelection(ratingIndex);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                String newRating = spnRating.getSelectedItem().toString(); // Get the newly selected rating
                data.setRating(newRating); // Update the rating with the newly selected rating

                dbh.updateMovie(data);
                dbh.close();
                finish(); // Destroys Current activity
                Toast.makeText(ThirdActivity.this, "Movie updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteMovie(data.getId());
                finish(); // Destroys Current activity
                Toast.makeText(ThirdActivity.this, "Movie deleted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Destroys Current activity
            }
        });
    }
}