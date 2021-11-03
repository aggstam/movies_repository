// -------------------------------------------------------------
//
// This Activity is used to add, modify and delete Repository records.
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
import android.widget.TextView;
import android.widget.Toast;

import com.stamatiou.movie.MovieAdapter;

public class ActionActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity);
        Integer mode = (Integer) getIntent().getExtras().get("mode");
        movieAdapter = new MovieAdapter(this);
        if (mode == 0) {
            addMovie();
        } else if (mode == 1) {
            editMovie();
        } else {
            deleteMovie();
        }
    }

    // Activity initialization method, for "Add Movie" mode.
    private void addMovie() {
        Log.i("message","AddMovie method started.");
        try {
            this.setTitle("Add Movie");
            findViewById(R.id.deleteMessageTextView).setVisibility(View.GONE);
            findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addMovieSubmitAction();
                }
            });
            Log.i("message","AddMovie method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during AddMovie method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Application validates submitted fields.
    // On successful fields validation, a new Movie Repository record is created.
    private void addMovieSubmitAction() {
        Log.i("message","AddMovieSubmitAction method started.");
        try {
            Boolean formValidation = validateFormFields();
            if (formValidation) {
                Log.i("message","Form Fields validation succeeded. Generating record...");
                Boolean isGenerateSuccessful = movieAdapter.generateMovieRecord(((EditText) findViewById(R.id.titleEditText)).getText().toString(),
                                                                              ((EditText)findViewById(R.id.directorEditText)).getText().toString(),
                                                                              ((EditText)findViewById(R.id.lengthEditText)).getText().toString(),
                                                                              ((EditText)findViewById(R.id.categoryEditText)).getText().toString(),
                                                                              ((EditText)findViewById(R.id.yearEditText)).getText().toString());
                if (isGenerateSuccessful) {
                    Log.i("message","Record generated successfully!");
                    Toast.makeText(this,"Record generated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("message","There was an error during record creation.");
                    Toast.makeText(this,"There was an error during record creation.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i("message","Form Fields validation failed.");
            }
            Log.i("message","AddMovieSubmitAction method completed successfully.");
            if (formValidation) finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during AddMovieSubmitAction method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Activity initialization method, for "Edit Movie" mode.
    private void editMovie() {
        Log.i("message","EditMovie method started.");
        try {
            this.setTitle("Edit Movie");
            ((EditText) findViewById(R.id.titleEditText)).setText((String) getIntent().getExtras().get("title"));
            ((EditText) findViewById(R.id.directorEditText)).setText((String) getIntent().getExtras().get("director"));
            ((EditText) findViewById(R.id.lengthEditText)).setText((String) getIntent().getExtras().get("length"));
            ((EditText) findViewById(R.id.categoryEditText)).setText((String) getIntent().getExtras().get("category"));
            ((EditText) findViewById(R.id.yearEditText)).setText((String) getIntent().getExtras().get("year"));
            findViewById(R.id.deleteMessageTextView).setVisibility(View.GONE);
            findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editMovieSubmitAction();
                }
            });
            Log.i("message","EditMovie method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during EditMovie method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Application validates submitted fields.
    // On successful fields validation, requested Movie Repository record is modified.
    private void editMovieSubmitAction() {
        Log.i("message","EditMovieSubmitAction method started.");
        try {
            Boolean formValidation = validateFormFields();
            if (formValidation) {
                Log.i("message","Form Fields validation succeeded. Modifying record...");
                Boolean isEditSuccessful = movieAdapter.modifyMovieRecord((Integer) getIntent().getExtras().get("id"),
                                                                          ((EditText) findViewById(R.id.titleEditText)).getText().toString(),
                                                                          ((EditText)findViewById(R.id.directorEditText)).getText().toString(),
                                                                          ((EditText)findViewById(R.id.lengthEditText)).getText().toString(),
                                                                          ((EditText)findViewById(R.id.categoryEditText)).getText().toString(),
                                                                          ((EditText)findViewById(R.id.yearEditText)).getText().toString());
                if (isEditSuccessful) {
                    Log.i("message","Record modified successfully!");
                    Toast.makeText(this,"Record modified successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("message","There was an error during record modification.");
                    Toast.makeText(this,"There was an error during record modification.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i("message","Form Fields validation failed.");
            }
            Log.i("message","EditMovieSubmitAction method completed successfully.");
            if (formValidation) finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during EditMovieSubmitAction method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Activity initialization method, for "Delete Movie" mode.
    private void deleteMovie() {
        Log.i("message","DeleteMovie method started.");
        try {
            this.setTitle("Delete Movie");
            StringBuilder messageBuilder = new StringBuilder().append("Are you sure that you want to delete Movie: ")
                                                              .append((String) getIntent().getExtras().get("title"))
                                                              .append(" (").append((String) getIntent().getExtras().get("year")).append(")")
                                                              .append(" by ").append((String) getIntent().getExtras().get("director")).append("?");
            ((TextView) findViewById(R.id.deleteMessageTextView)).setText(messageBuilder.toString());
            findViewById(R.id.titleTextView).setVisibility(View.GONE);
            findViewById(R.id.titleEditText).setVisibility(View.GONE);
            findViewById(R.id.directorTextView).setVisibility(View.GONE);
            findViewById(R.id.directorEditText).setVisibility(View.GONE);
            findViewById(R.id.lengthTextView).setVisibility(View.GONE);
            findViewById(R.id.lengthEditText).setVisibility(View.GONE);
            findViewById(R.id.categoryTextView).setVisibility(View.GONE);
            findViewById(R.id.categoryEditText).setVisibility(View.GONE);
            findViewById(R.id.yearTextView).setVisibility(View.GONE);
            findViewById(R.id.yearEditText).setVisibility(View.GONE);
            findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMovieSubmitAction();
                }
            });
            Log.i("message","DeleteMovie method completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during DeleteMovie method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Application validates submitted fields.
    // On successful fields validation, requested Movie Repository record is removed.
    private void deleteMovieSubmitAction() {
        Log.i("message","DeleteMovieSubmitAction method started.");
        try {
            Boolean isRemoveSuccessful = movieAdapter.removeMovieRecord((Integer) getIntent().getExtras().get("id"));
            if (isRemoveSuccessful) {
                Log.i("message","Record removed successfully!");
                Toast.makeText(this,"Record removed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("message","There was an error during record removal.");
                Toast.makeText(this,"There was an error during record removal.", Toast.LENGTH_SHORT).show();
            }
            Log.i("message","DeleteMovieSubmitAction method completed successfully.");
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during DeleteMovieSubmitAction method:" + e.getMessage());
            Toast.makeText(this, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Validates User submitted Movie fields.
    // Fields cannot be empty and must have a specific length.
    // 'Length' and 'Year' field must also follow specific patterns.
    private Boolean validateFormFields() {
        Boolean valid = true;
        EditText titleEditText = findViewById(R.id.titleEditText);
        if (titleEditText.getText().toString().trim().length() == 0) {
            titleEditText.setError("Title field is required!");
            valid = false;
        } else if (titleEditText.getText().toString().trim().length() > 150) {
            titleEditText.setError("This field max length is 150 characters!");
            valid = false;
        }
        EditText directorEditText = findViewById(R.id.directorEditText);
        if (directorEditText.getText().toString().trim().length() == 0) {
            directorEditText.setError("Director field is required!");
            valid = false;
        } else if (directorEditText.getText().toString().trim().length() > 50) {
            directorEditText.setError("This field max length is 50 characters!");
            valid = false;
        }
        EditText lengthEditText = findViewById(R.id.lengthEditText);
        if (lengthEditText.getText().toString().trim().length() == 0) {
            lengthEditText.setError("Length field is required!");
            valid = false;
        } else if (!lengthEditText.getText().toString().matches("\\d+h \\d+min")) {
            lengthEditText.setError("This field must be a Pattern (\\\\d+h \\\\d+min) !");
            valid = false;
        } else if (lengthEditText.getText().toString().trim().length() > 30) {
            lengthEditText.setError("This field max length is 30 characters!");
            valid = false;
        }
        EditText categoryEditText = findViewById(R.id.categoryEditText);
        if (categoryEditText.getText().toString().trim().length() == 0) {
            categoryEditText.setError("Category field is required!");
            valid = false;
        } else if (categoryEditText.getText().toString().trim().length() > 250) {
            categoryEditText.setError("This field max length is 250 characters!");
            valid = false;
        }
        EditText yearEditText = findViewById(R.id.yearEditText);
        if (yearEditText.getText().toString().trim().length() == 0) {
            yearEditText.setError("Year field is required!");
            valid = false;
        } else if (!yearEditText.getText().toString().matches("^[0-9]{4}$")) {
            yearEditText.setError("This field must be a year!");
            valid = false;
        }
        return valid;
    }
}
