package sg.edu.rp.c346.id21023395.anightatthemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etTitle, etGenre, etYear;
    Button btnInsert, btnShow;
    Spinner spnRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etMovieTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);
        spnRating = findViewById(R.id.spnRating);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                String genre = etGenre.getText().toString();
                String rating = spnRating.getSelectedItem().toString(); // Retrieve selected rating from Spinner
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertMovie(title, genre, year, rating);
                db.close();
                Toast.makeText(MainActivity.this, "Movie inserted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}