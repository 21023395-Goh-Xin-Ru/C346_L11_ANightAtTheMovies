package sg.edu.rp.c346.id21023395.anightatthemovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource,
                         ArrayList<Movie>objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.tvMovieTitle);
        TextView tvGenre = rowView.findViewById(R.id.tvGenre);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        ImageView ivRating = rowView.findViewById(R.id.ivRating);

        // Obtain the Android Version information based on the position
        Movie current = movieList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(current.getTitle());
        tvGenre.setText(current.getGenre());
        tvYear.setText(String.valueOf(current.getYear()));
        ImageView imageViewRating = rowView.findViewById(R.id.ivRating);
        String rating = current.getRating();
        if ("G".equals(rating)){
            imageViewRating.setImageResource(R.drawable.rating_g);
        } else if ("PG".equals(rating)) {
            imageViewRating.setImageResource(R.drawable.rating_pg);
        } else if ("PG13".equals(rating)) {
            imageViewRating.setImageResource(R.drawable.rating_pg13);
        } else if ("NC16".equals(rating)){
            imageViewRating.setImageResource(R.drawable.rating_nc16);
        } else if ("M18".equals(rating)){
            imageViewRating.setImageResource(R.drawable.rating_m18);
        } else if ("R21".equals(rating)){
            imageViewRating.setImageResource(R.drawable.rating_r21);
        }
        return rowView;
    }
}
