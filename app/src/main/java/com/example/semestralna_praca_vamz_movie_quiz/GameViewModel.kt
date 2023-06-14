package com.example.semestralna_praca_vamz_movie_quiz

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager

/**
 * ViewModel class for managing the game state and logic.
 */
class GameViewModel : ViewModel() {
    private var score: Int = 0
    private var isMuted: Boolean = false

    /**
     * Data class representing a movie with its title and guessed status.
     *
     * @property movieTitle The title of the movie.
     * @property guessed Indicates whether the movie has been guessed or not.
     */
    data class Movie(
        val movieTitle: String,
        var guessed: Boolean
    )

    private var movies: MutableList<Movie> = mutableListOf(
        Movie("THE DARK KNIGHT", false),
        Movie("THE LORD OF THE RINGS", false),
        Movie("THE MATRIX", false),
        Movie("SHREK", false),
        Movie("FORREST GUMP", false),
        Movie("PSYCHO", false),
        Movie("THE WOLF OF WALL STREET", false),
        Movie("THE GODFATHER", false),
        Movie("INTERSTELLAR", false),
        Movie("KILL BILL", false),
        Movie("GOODFELLAS", false),
        Movie("GREEN MILE", false),
        Movie("INCEPTION", false),
        Movie("ALIEN", false),
        Movie("PARASITE", false),
        Movie("PULP FICTION", false),
        Movie("FIGHT CLUB", false),
        Movie("JOKER", false),
        Movie("A CLOCKWORK ORANGE", false),
        Movie("AMERICAN BEAUTY", false),
        Movie("DJANGO UNCHAINED", false),
        Movie("GOOD WILL HUNTING", false),
        Movie("MEMENTO", false),
        Movie("WHIPLASH", false),
        Movie("SEVEN", false),
        Movie("TAXI DRIVER", false),
        Movie("WALL-E", false)
    )

    /**
     * Returns the movie with the specified title.
     *
     * @param movieName The title of the movie to retrieve.
     * @return The Movie object if found, null otherwise.
     */
    fun getMovie(movieName: String): Movie? {
        return movies.find { it.movieTitle.equals(movieName, ignoreCase = true) }
    }

    /**
     * Sets the guessed status of the movie with the specified title to true.
     *
     * @param movieName The title of the movie to set as guessed.
     */
    fun setGuessed(movieName: String) {
        val index = movies.indexOfFirst { it.movieTitle.equals(movieName, ignoreCase = true) }
        if (index != -1) {
            movies[index] = movies[index].copy(guessed = true)
        }
    }

    /**
     * Resets the guessed status of all movies to false.
     */
    fun resetGuessed() {
        for (movie in movies) {
            movie.guessed = false
        }
    }

    /**
     * Checks if the movie with the specified title has been guessed.
     *
     * @param movieName The title of the movie to check.
     * @return True if the movie has been guessed, false otherwise.
     */
    fun isMovieGuessed(movieName: String): Boolean {
        val movie = getMovie(movieName)
        return movie?.guessed ?: false
    }

    /**
     * Checks if all movies in level 1 have been guessed.
     *
     * @return True if all level 1 movies have been guessed, false otherwise.
     */
    fun areLevel1MoviesGuessed(): Boolean {
        val moviesToCheck = listOf(
            "THE DARK KNIGHT",
            "THE LORD OF THE RINGS",
            "THE MATRIX",
            "SHREK",
            "FORREST GUMP",
            "PSYCHO",
            "THE WOLF OF WALL STREET",
            "THE GODFATHER",
            "INTERSTELLAR"
        )

        for (movieTitle in moviesToCheck) {
            val movie = getMovie(movieTitle)
            if (movie?.guessed != true) {
                return false
            }
        }
        return true
    }

    /**
     * Checks if all movies in level 2 have been guessed.
     *
     * @return True if all level 2 movies have been guessed, false otherwise.
     */
    fun areLevel2MoviesGuessed(): Boolean {
        val moviesToCheck = listOf(
            "KILL BILL",
            "GOODFELLAS",
            "GREEN MILE",
            "INCEPTION",
            "ALIEN",
            "PARASITE",
            "PULP FICTION",
            "FIGHT CLUB",
            "JOKER"
        )

        for (movieTitle in moviesToCheck) {
            val movie = getMovie(movieTitle)
            if (movie?.guessed != true) {
                return false
            }
        }
        return true
    }

    /**
     * Checks if all movies in level 3 have been guessed.
     *
     * @return True if all level 3 movies have been guessed, false otherwise.
     */
    fun areLevel3MoviesGuessed(): Boolean {
        val moviesToCheck = listOf(
            "A CLOCKWORK ORANGE",
            "AMERICAN BEAUTY",
            "DJANGO UNCHAINED",
            "GOOD WILL HUNTING",
            "MEMENTO",
            "WHIPLASH",
            "SEVEN",
            "TAXI DRIVER",
            "WALL-E"
        )

        for (movieTitle in moviesToCheck) {
            val movie = getMovie(movieTitle)
            if (movie?.guessed != true) {
                return false
            }
        }
        return true
    }

    /**
     * Updates the score by the specified value.
     *
     * @param value The value to add to the score.
     */
    fun updateScore(value: Int) {
        score += value
    }

    /**
     * Returns the current score.
     *
     * @return The current score.
     */
    fun getScore(): Int {
        return score
    }

    /**
     * Returns the mute status.
     *
     * @return True if the game is muted, false otherwise.
     */
    fun getMute(): Boolean {
        return isMuted
    }

    /**
     * Sets the mute status.
     *
     * @param value The value to set for the mute status.
     */
    fun setMute(value: Boolean) {
        isMuted = value
    }

    /**
     * Resets the score to zero.
     */
    fun resetScore() {
        score = 0
    }

    /**
     * Saves the game progress to shared preferences.
     *
     * @param context The application context.
     */
    fun saveGameProgress(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()

        editor.putInt("score", score)

        for (movie in movies) {
            editor.putBoolean(movie.movieTitle, movie.guessed)
        }

        editor.putBoolean("isMuted", isMuted)

        editor.apply()
    }

    /**
     * Loads the game progress from shared preferences.
     *
     * @param context The application context.
     */
    fun loadGameProgress(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        score = sharedPreferences.getInt("score", 0)

        for (movie in movies) {
            movie.guessed = sharedPreferences.getBoolean(movie.movieTitle, false)
        }

        isMuted = sharedPreferences.getBoolean("isMuted", false)
    }
}
