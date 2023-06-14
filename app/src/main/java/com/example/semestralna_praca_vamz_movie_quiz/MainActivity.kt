package com.example.semestralna_praca_vamz_movie_quiz

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * The main activity of the movie quiz game.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /**
         * The media player used for background music.
         */
        lateinit var mediaPlayer: MediaPlayer
    }

    private lateinit var gameViewModel: GameViewModel

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create and configure the media player
        mediaPlayer = MediaPlayer.create(this, R.raw.smile)
        mediaPlayer.isLooping = true

        // Initialize the game view model
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Load game progress
        gameViewModel.loadGameProgress(this)

        // Start the media player if not muted
        if (!gameViewModel.getMute()) {
            mediaPlayer.start()
        }
    }

    /**
     * Called when the activity is stopped.
     */
    override fun onStop() {
        super.onStop()
        // Save game progress
        gameViewModel.saveGameProgress(this)
    }

    /**
     * Called when the activity is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        // Release the media player
        mediaPlayer.release()
    }
}
