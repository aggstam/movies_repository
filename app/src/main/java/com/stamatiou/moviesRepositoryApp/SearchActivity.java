// -------------------------------------------------------------
//
// This Activity is used to search Repository records.
// Implementation includes "search by exact value" for fields 'Title'
// and 'Director' and "search by value part" for field 'Title'.
// User can modify/delete retrieved records.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.moviesRepositoryApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stamatiou.movie.Movie;
import com.stamatiou.movie.MovieAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        Integer mode = (Integer) getIntent().getExtras().get("mode");
        movieAdapter = new MovieAdapter(this);
        if (mode == 2) {
            searchByPart("Title");
        } else {
            String field = (mode == 1) ? "Title" : "Director";
            searchBy(field);
        }
    }

    // Activity initialization method, for "search by exact value" mode.
    private void searchBy(String field) {
        Log.i("message","SearchBy method started.");
        try {
            this.setTitle("Search By");
            ((TextView) findViewById(R.id.searchTextView)).setText(field);
            findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performSearch();
                }
            });
            Log.i("message","SearchBy method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during SearchBy method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Activity initialization method, for "search by value part" mode.
    private void searchByPart(String field) {
        Log.i("message","SearchByPart method started.");
        try {
            this.setTitle("Search By Part");
            ((TextView) findViewById(R.id.searchTextView)).setText(field);
            findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performSearch();
                }
            });
            Log.i("message","SearchByPart method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during SearchByPart method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Application performs the requested search method and generates
    // the Table View for the retrieved records.
    private void performSearch() {
        Log.i("message","PerformSearch method started.");
        try {
            Integer mode = (Integer) getIntent().getExtras().get("mode");
            String searchTerm = ((EditText) findViewById(R.id.searchEditText)).getText().toString();
            List<Movie> moviesList;
            if (mode == 2) {
                moviesList = movieAdapter.retrieveMoviesFromDatabaseUsingFieldPart("MOVIE_TITLE", searchTerm);
            } else {
                String field = (mode == 1) ? "MOVIE_TITLE" : "MOVIE_DIRECTOR";
                moviesList = movieAdapter.retrieveMoviesFromDatabaseUsingField(field, searchTerm);
            }
            TableLayout tableLayout = findViewById(R.id.tableLayout2);
            tableLayout.removeAllViews();
            Utils.generateTableLayoutMoviesRow(this, tableLayout, moviesList);
            Log.i("message","PerformSearch method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during PerformSearch method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        performSearch();
    }

}
