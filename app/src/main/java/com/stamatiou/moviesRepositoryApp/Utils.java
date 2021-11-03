// -------------------------------------------------------------
//
// This is a utility class, used by application activities,
// in order generate table Views or hash passwords.
// Available methods: hashPassword and generateTableLayoutMoviesRow.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.moviesRepositoryApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.stamatiou.movie.Movie;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Utils {

    // Generates a password hash string, using SHA256 algorithm.
    public static String hashPassword(Context context, String password) {
        Log.i("message","HashPassword method started.");
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA256");
            digest.update(password.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            Log.i("message","HashPassword method completed successfully.");
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.i("message","Exception during HashPassword method:" + e.getMessage());
            Toast.makeText(context, "Exception occurred, check log file for more information.", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    // Generates Movies Table header row.
    private static void setTableLayoutHeaderRow(Context context, TableLayout tableLayout) {
        TableRow headerRow = new TableRow(context);
        headerRow.setBackgroundColor(Color.parseColor("#DEEDFA"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Movie ID"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Title"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Director"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Length"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Category"));
        headerRow.addView(generateTextView(context, Color.BLACK, "Year"));
        tableLayout.addView(headerRow);
    }

    // Generates Movies Table layout.
    // Each generated Row (except header row), can be long-clicked,
    // in order to edit or delete row contents.
    public static void generateTableLayoutMoviesRow(final Context context, TableLayout tableLayout, List<Movie> moviesList) {
        if (moviesList != null && !moviesList.isEmpty()) {
            setTableLayoutHeaderRow(context, tableLayout);
            for (Movie movie : moviesList) {
                TableRow movieRow = new TableRow(context);
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieId().toString()));
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieTitle()));
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieDirector()));
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieLength()));
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieCategory()));
                movieRow.addView(generateTextView(context, Color.WHITE, movie.getMovieYear()));
                movieRow.setOnLongClickListener( new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Context wrapper = new ContextThemeWrapper(v.getContext(), R.style.PopupMenuStyle);
                        PopupMenu popupMenu = new PopupMenu(wrapper, v);
                        popupMenu.setGravity(Gravity.RIGHT);
                        popupMenu.getMenu().add(0, 1, 0,"Edit Movie").setActionView(v);
                        popupMenu.getMenu().add(0, 2, 1,"Delete Movie").setActionView(v);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                TableRow row = (TableRow) item.getActionView();
                                Intent intent = new Intent(row.getContext(), ActionActivity.class);
                                intent.putExtra("mode", item.getItemId());
                                intent.putExtra("id", Integer.valueOf(((TextView) row.getVirtualChildAt(0)).getText().toString()));
                                intent.putExtra("title", ((TextView) row.getVirtualChildAt(1)).getText().toString());
                                intent.putExtra("director", ((TextView) row.getVirtualChildAt(2)).getText().toString());
                                intent.putExtra("length", ((TextView) row.getVirtualChildAt(3)).getText().toString());
                                intent.putExtra("category", ((TextView) row.getVirtualChildAt(4)).getText().toString());
                                intent.putExtra("year", ((TextView) row.getVirtualChildAt(5)).getText().toString());
                                context.startActivity(intent);
                                return true;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                } );
                tableLayout.addView(movieRow);
            }
        } else {
            Log.i("message","Movies List is empty or null.");
            Toast.makeText(context, "Movies List is empty or null.", Toast.LENGTH_SHORT).show();
        }
    }

    // Generates a Movies Table row column TextView.
    private static TextView generateTextView(Context context, Integer textColor, String text) {
        TextView textView = new TextView(context);
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(10, 0, 10,0);
        textView.setText(text);
        return textView;
    }

}
