// -------------------------------------------------------------
//
// This Activity is used to display Repository records.
// User can add/modify/delete records and/or navigate to rest
// application activities using the top right menu.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.moviesRepositoryApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.stamatiou.movie.Movie;
import com.stamatiou.movie.MovieAdapter;

import java.util.List;

public class RepositoryActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_activity);
        initMoviesTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repository_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int mode;
        if (id == R.id.addMovie) {
            mode = 0;
        } else if (id == R.id.searchByTitle) {
            mode = 1;
        } else if (id == R.id.searchByTitlePart) {
            mode = 2;
        } else if (id == R.id.searchByDirector) {
            mode = 3;
        } else {
            mode = 4;
        }
        Intent intent = null;
        if (mode == 0) {
            intent = new Intent(this, ActionActivity.class);
        } else if (mode != 4) {
            intent = new Intent(this, SearchActivity.class);
        } else {
            intent = new Intent(this, PasswordChangeActivity.class);
        }
        intent.putExtra("mode", mode);
        startActivity(intent);
        return true;
    }

    // Activity initialization method.
    // Application initializes the database, retrieves all records
    // and generates the Table View for the retrieved records.
    private void initMoviesTable() {
        Log.i("message","InitMoviesTable method started.");
        try {
            movieAdapter = new MovieAdapter(this);
            Boolean initDb = movieAdapter.initializeDatabase();
            if (initDb) {
                TableLayout tableLayout = findViewById(R.id.tableLayout1);
                List<Movie> moviesList = movieAdapter.retrieveMoviesFromDatabase();
                Utils.generateTableLayoutMoviesRow(this, tableLayout, moviesList);
            } else {
                Log.i("message","Database could not be initialized.");
                Toast.makeText(this,"Database could not be initialized.", Toast.LENGTH_SHORT).show();
            }
            Log.i("message","InitMoviesTable method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during InitMoviesTable method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadMoviesTable();
    }

    // Activity initialization method.
    // When User returns to the Activity, displayed records are reloaded.
    private void reloadMoviesTable() {
        Log.i("message","ReloadMoviesTable method started.");
        try {
            TableLayout tableLayout = findViewById(R.id.tableLayout1);
            tableLayout.removeAllViews();
            List<Movie> moviesList = movieAdapter.retrieveMoviesFromDatabase();
            Utils.generateTableLayoutMoviesRow(this, tableLayout, moviesList);
            Log.i("message","ReloadMoviesTable method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during ReloadMoviesTable method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

}
