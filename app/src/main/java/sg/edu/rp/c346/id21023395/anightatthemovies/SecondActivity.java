package sg.edu.rp.c346.id21023395.anightatthemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView lvMovie;
    ArrayList<Movie> movieList;
    CustomAdapter aa;
    Button btnShowPG13, btnBack;
    ArrayList<String> ratingSpinnerList = new ArrayList<>();;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvMovie = findViewById(R.id.lvMovieList);
        btnShowPG13 = findViewById(R.id.btnSHowPG13);
        btnBack = findViewById(R.id.btnBack);
        spn = findViewById(R.id.spnRatingg);

        // Retrieve the items from strings.xml and add them to ratingSpinnerList
        Resources res = getResources();
        String[] ratingsArray = res.getStringArray(R.array.spinnerItems);
        for (String rating : ratingsArray) {
            ratingSpinnerList.add(rating);
        }

        // Add "Select a Rating" as the first item in ratingSpinnerList
        ratingSpinnerList.add(0, "Select a Rating");

        // Create an ArrayAdapter for the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ratingSpinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spinnerAdapter);

        // Set an OnItemSelectedListener for the Spinner
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(SecondActivity.this);
                String selectedRating = parent.getItemAtPosition(position).toString();
                if (selectedRating.equals("Select a Rating")) {
                    spn.setSelection(0);
                    movieList.clear();
                    movieList.addAll(db.getMovies());
                    aa.notifyDataSetChanged();

                } else {
                    // Handle the case when a specific rating is selected
                    db = new DBHelper(SecondActivity.this);
                    movieList = db.getMoviesByRatings(selectedRating);
                    db.close();
                    aa = new CustomAdapter(SecondActivity.this, R.layout.row, movieList);
                    lvMovie.setAdapter(aa);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected (optional)
            }
        });
        // Display listView
        DBHelper db = new DBHelper(SecondActivity.this);
        movieList = db.getMovies();
        db.close();
        aa = new CustomAdapter(SecondActivity.this,
                R.layout.row, movieList);
        lvMovie.setAdapter(aa);

        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie data = movieList.get(position);
                Intent i = new Intent(SecondActivity.this,
                        ThirdActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnShowPG13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(SecondActivity.this);
                movieList = db.getMoviesPG13("PG13");
                db.close();
                aa = new CustomAdapter(SecondActivity.this,
                        R.layout.row, movieList);
                lvMovie.setAdapter(aa);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(SecondActivity.this);
        movieList.clear();
        movieList.addAll(dbh.getMovies());
        aa.notifyDataSetChanged();
    }
}