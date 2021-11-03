// -------------------------------------------------------------
//
// This is the Movie Adapter used by the application, in order to
// communicate with the SQLite Database.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.movie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter {

    private SQLiteDatabase db;

    // Adapter initialization constructor.
    public MovieAdapter(Context context) {
        this.db = context.openOrCreateDatabase("Movies", context.MODE_PRIVATE, null);
    }

    // Database initialization method.
    // If 'MOVIES' table doesn't exist, the table is created and default records are generated.
    public Boolean initializeDatabase() {
        Log.i("message","InitializeDatabase method started.");
        try {
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='MOVIES'", null);
            if (cursor.getCount() == 0) {
                Log.i("message","Database table not found. Generating table...");
                StringBuilder queryBuilder = new StringBuilder().append("CREATE TABLE 'MOVIES'(")
                                                                .append("'MOVIE_ID' INTEGER PRIMARY KEY AUTOINCREMENT,")
                                                                .append("'MOVIE_TITLE' TEXT,")
                                                                .append("'MOVIE_DIRECTOR' TEXT,")
                                                                .append("'MOVIE_LENGTH' TEXT,")
                                                                .append("'MOVIE_CATEGORY' TEXT,")
                                                                .append("'MOVIE_YEAR' TEXT)");
                db.execSQL(queryBuilder.toString());
                Log.i("message","Table generated successfully. Generating default records...");
                if (generateDefaultMovieRecords()) {
                    Log.i("message","Default records generated successfully.");
                } else {
                    Log.i("message","There was an error while generating default records.");
                }
            }
            Log.i("message","InitializeDatabase method completed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during InitializeDatabase method:" + e.getMessage());
            return false;
        }
    }

    // Retrieves all Movies records from Database.
    public List<Movie> retrieveMoviesFromDatabase() {
        Log.i("message","RetrieveMoviesFromDatabase method started.");
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM MOVIES",null);
            List<Movie> moviesList = convertMoviesList(cursor);
            Log.i("message","RetrieveMoviesFromDatabase method completed successfully.");
            return moviesList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during RetrieveMoviesFromDatabase method:" + e.getMessage());
            return null;
        }
    }

    // Generates default Movie records.
    private Boolean generateDefaultMovieRecords() {
        Log.i("message","GenerateDefaultMovieRecords method started.");
        try {
            generateMovieRecord("Gangs of New York", "Martin Scorsese", "2h 47min", "Crime, Drama", "2003");
            generateMovieRecord("Chappie", "Neill Blomkamp", "2h", "Action, Crime, Drama", "2015");
            generateMovieRecord("The Dark Knight", "Christopher Nolan", "2h 32min", "Action, Crime, Drama", "2008");
            generateMovieRecord("Pulp Fiction", "Quentin Tarantino", "2h 34min", "Crime, Drama", "1995");
            generateMovieRecord("Seven", "David Fincher", "2h 7min", "Crime, Drama, Mystery", "1996");
            generateMovieRecord("Léon", "Luc Besson", "1h 50min", "Action, Crime, Drama", "1995");
            generateMovieRecord("V for Vendetta", "James McTeigue", "2h 12min", "Action, Drama, Sci-Fi", "2005");
            generateMovieRecord("Independence Day", "Roland Emmerich", "2h 25min", "Action, Adventure, Sci-Fi", "1996");
            generateMovieRecord("Godzilla", "Gareth Edwards", "2h 3min", "Action, Adventure, Sci-Fi", "2014");
            generateMovieRecord("Edge of Tomorrow", "Doug Liman", "1h 53min", "Action, Adventure, Sci-Fi", "2014");
            generateMovieRecord("Deadpool", "Tim Miller", "1h 48min", "Action, Adventure, Comedy", "2016");
            Log.i("message","RetrieveMoviesFromDatabase method completed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during GenerateDefaultMovieRecords method:" + e.getMessage());
            return false;
        }
    }

    // Generates a Movie record and inserts it to the Database.
    public Boolean generateMovieRecord(String movieTitle, String movieDirector, String movieLength, String movieCategory, String movieYear) {
        Log.i("message","GenerateMovieRecord method started.");
        try {
            StringBuilder generatedMovieRecord = new StringBuilder().append("Generating record for Movie: [")
                                                                    .append("MOVIE_TITLE:").append(movieTitle).append(", ")
                                                                    .append("MOVIE_DIRECTOR:").append(movieDirector).append(", ")
                                                                    .append("MOVIE_LENGTH:").append(movieLength).append(", ")
                                                                    .append("MOVIE_CATEGORY:").append(movieCategory).append(", ")
                                                                    .append("MOVIE_YEAR:").append(movieYear).append("]");
            Log.i("message",generatedMovieRecord.toString());
            StringBuilder queryBuilder = new StringBuilder().append("INSERT OR IGNORE INTO 'MOVIES' ('MOVIE_TITLE', 'MOVIE_DIRECTOR', 'MOVIE_LENGTH', 'MOVIE_CATEGORY', 'MOVIE_YEAR') VALUES ('")
                                                            .append(movieTitle).append("', '")
                                                            .append(movieDirector).append("', '")
                                                            .append(movieLength).append("', '")
                                                            .append(movieCategory).append("', '")
                                                            .append(movieYear) .append("')");

            db.execSQL(queryBuilder.toString());
            Log.i("message","GenerateMovieRecord method completed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during GenerateMovieRecord method:" + e.getMessage());
            return false;
        }
    }

    // Modifies an existing Movie record.
    public Boolean modifyMovieRecord(Integer movieId, String movieTitle, String movieDirector, String movieLength, String movieCategory, String movieYear) {
        Log.i("message","GenerateMovieRecord method started.");
        try {
            StringBuilder generatedMovieRecord = new StringBuilder().append("Modifying record for Movie: [")
                                                                    .append("MOVIE_ID:").append(movieId).append(", ")
                                                                    .append("MOVIE_TITLE:").append(movieTitle).append(", ")
                                                                    .append("MOVIE_DIRECTOR:").append(movieDirector).append(", ")
                                                                    .append("MOVIE_LENGTH:").append(movieLength).append(", ")
                                                                    .append("MOVIE_CATEGORY:").append(movieCategory).append(", ")
                                                                    .append("MOVIE_YEAR:").append(movieYear).append("]");
            Log.i("message",generatedMovieRecord.toString());
            StringBuilder queryBuilder = new StringBuilder().append("UPDATE MOVIES SET ")
                                                            .append("MOVIE_TITLE = '").append(movieTitle).append("', ")
                                                            .append("MOVIE_DIRECTOR = '").append(movieDirector).append("', ")
                                                            .append("MOVIE_LENGTH = '").append(movieLength).append("', ")
                                                            .append("MOVIE_CATEGORY = '").append(movieCategory).append("', ")
                                                            .append("MOVIE_YEAR = '").append(movieYear).append("' ")
                                                            .append("WHERE MOVIE_ID = ").append(movieId);

            db.execSQL(queryBuilder.toString());
            Log.i("message","GenerateMovieRecord method completed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during GenerateMovieRecord method:" + e.getMessage());
            return false;
        }
    }

    // Removes an existing Movie record.
    public Boolean removeMovieRecord(Integer movieId) {
        Log.i("message","RemoveMovieRecord method started.");
        try {
            StringBuilder generatedMovieRecord = new StringBuilder().append("Removing record: [").append("MOVIE_ID:").append(movieId).append("]");
            Log.i("message",generatedMovieRecord.toString());
            StringBuilder queryBuilder = new StringBuilder().append("DELETE FROM MOVIES ").append("WHERE MOVIE_ID = ").append(movieId);
            db.execSQL(queryBuilder.toString());
            Log.i("message","RemoveMovieRecord method completed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during RemoveMovieRecord method:" + e.getMessage());
            return false;
        }
    }

    // Retrieves all Movies records from Database matching the criteria.
    public List<Movie> retrieveMoviesFromDatabaseUsingField(String field, String searchTerm) {
        Log.i("message","RetrieveMoviesFromDatabaseUsingField method started.");
        try {
            StringBuilder queryBuilder = new StringBuilder().append("SELECT * FROM MOVIES WHERE ").append(field).append(" = '").append(searchTerm).append("'");
            Cursor cursor = db.rawQuery(queryBuilder.toString(),null);
            List<Movie> moviesList = convertMoviesList(cursor);
            Log.i("message","RetrieveMoviesFromDatabaseUsingField method completed successfully.");
            return moviesList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during RetrieveMoviesFromDatabaseUsingField method:" + e.getMessage());
            return null;
        }
    }

    // Retrieves all Movies records from Database matching the criteria.
    public List<Movie> retrieveMoviesFromDatabaseUsingFieldPart(String field, String searchTerm) {
        Log.i("message","RetrieveMoviesFromDatabaseUsingField method started.");
        try {
            StringBuilder queryBuilder = new StringBuilder().append("SELECT * FROM MOVIES WHERE ").append(field).append(" like '%").append(searchTerm).append("%'");
            Cursor cursor = db.rawQuery(queryBuilder.toString(),null);
            List<Movie> moviesList = convertMoviesList(cursor);
            Log.i("message","RetrieveMoviesFromDatabaseUsingField method completed successfully.");
            return moviesList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("message","Exception during RetrieveMoviesFromDatabaseUsingField method:" + e.getMessage());
            return null;
        }
    }

    // Generates a Movie records list from Database records.
    private List<Movie> convertMoviesList(Cursor cursor) {
        List<Movie> moviesList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            Log.i("message","Database records found. Converting to Movies Class...");
            while (cursor.moveToNext()) {
                Movie movie = new Movie.Builder()
                        .withMovieId(Integer.valueOf(cursor.getString(0)))
                        .withMovieTitle(cursor.getString(1))
                        .withMovieDirector(cursor.getString(2))
                        .withMovieLength(cursor.getString(3))
                        .withMovieCategory(cursor.getString(4))
                        .withMovieYear(cursor.getString(5))
                        .build();
                moviesList.add(movie);
            }
        }
        return moviesList;
    }

}
