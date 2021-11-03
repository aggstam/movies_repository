// -------------------------------------------------------------
//
// This is the Movie Structure used by the application.
// Movie data: ID, Title, Director, Length, Category and Year.
//
// Author: Aggelos Stamatiou, May 2020
//
// --------------------------------------------------------------

package com.stamatiou.movie;

public class Movie {

    private Integer movieId;
    private String movieTitle;
    private String movieDirector;
    private String movieLength;
    private String movieCategory;
    private String movieYear;

    public static class Builder {

        private Integer movieId;
        private String movieTitle;
        private String movieDirector;
        private String movieLength;
        private String movieCategory;
        private String movieYear;

        public Builder() {}

        public Builder withMovieId(Integer movieId){
            this.movieId = movieId;
            return this;
        }

        public Builder withMovieTitle(String movieTitle){
            this.movieTitle = movieTitle;
            return this;
        }

        public Builder withMovieDirector(String movieDirector){
            this.movieDirector = movieDirector;
            return this;
        }

        public Builder withMovieLength(String movieLength){
            this.movieLength = movieLength;
            return this;
        }

        public Builder withMovieCategory(String movieCategory){
            this.movieCategory = movieCategory;
            return this;
        }

        public Builder withMovieYear(String movieYear){
            this.movieYear = movieYear;
            return this;
        }

        public Movie build(){
            Movie movie = new Movie();
            movie.movieId = this.movieId;
            movie.movieTitle = this.movieTitle;
            movie.movieDirector = this.movieDirector;
            movie.movieLength = this.movieLength;
            movie.movieCategory = this.movieCategory;
            movie.movieYear = this.movieYear;
            return movie;
        }
    }

    private Movie() {}

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public String getMovieYear() {
        return movieYear;
    }

}
