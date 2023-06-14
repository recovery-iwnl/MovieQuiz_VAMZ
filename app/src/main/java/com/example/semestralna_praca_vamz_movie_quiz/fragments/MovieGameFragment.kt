package com.example.semestralna_praca_vamz_movie_quiz.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.semestralna_praca_vamz_movie_quiz.GameViewModel
import com.example.semestralna_praca_vamz_movie_quiz.R
import com.example.semestralna_praca_vamz_movie_quiz.databinding.FragmentMovieGameBinding


/**
 * A [Fragment] subclass representing the movie guessing game.
 */
class MovieGameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var binding: FragmentMovieGameBinding
    private lateinit var buttonSoundPlayer: MediaPlayer

    private val handler = Handler(Looper.getMainLooper())

    private var currentMovie: String = "XXXX"
    private var currentMovieShuffled: String = "XXXX"

    /**
     * Inflates the fragment's layout and initializes the view model.
     *
     * @param inflater The LayoutInflater used to inflate the fragment's layout.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_game, container, false)
        return binding.root
    }

    /**
     * Initializes the UI components and sets up click listeners for buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.score.text = gameViewModel.getScore().toString()
        buttonSoundPlayer = MediaPlayer.create(requireContext(), R.raw.click)
        val args = arguments
        args?.let {
            val movieKey = when {
                it.containsKey("darkKnight") -> {
                    currentMovie = it.getString("darkKnight") ?: ""
                    R.drawable.dark_knight
                }
                it.containsKey("lotr") -> {
                    currentMovie = it.getString("lotr") ?: ""
                    R.drawable.lotr
                }
                it.containsKey("matrix") -> {
                    currentMovie = it.getString("matrix") ?: ""
                    R.drawable.matrix
                }

                it.containsKey("shrek") -> {
                    currentMovie = it.getString("shrek") ?: ""
                    R.drawable.shrek
                }
                it.containsKey("forrest") -> {
                    currentMovie = it.getString("forrest") ?: ""
                    R.drawable.forrest
                }
                it.containsKey("psycho") -> {
                    currentMovie = it.getString("psycho") ?: ""
                    R.drawable.psycho
                }

                it.containsKey("wow") -> {
                    currentMovie = it.getString("wow") ?: ""
                    R.drawable.wow
                }
                it.containsKey("godfather") -> {
                    currentMovie = it.getString("godfather") ?: ""
                    R.drawable.godfather
                }
                it.containsKey("interstellar") -> {
                    currentMovie = it.getString("interstellar") ?: ""
                    R.drawable.interstellar
                }

                it.containsKey("pulp") -> {
                    currentMovie = it.getString("pulp") ?: ""
                    R.drawable.pulp
                }
                it.containsKey("fightClub") -> {
                    currentMovie = it.getString("fightClub") ?: ""
                    R.drawable.fightclub
                }
                it.containsKey("joker") -> {
                    currentMovie = it.getString("joker") ?: ""
                    R.drawable.joker
                }

                it.containsKey("inception") -> {
                    currentMovie = it.getString("inception") ?: ""
                    R.drawable.inception
                }
                it.containsKey("alien") -> {
                    currentMovie = it.getString("alien") ?: ""
                    R.drawable.alien
                }
                it.containsKey("parasite") -> {
                    currentMovie = it.getString("parasite") ?: ""
                    R.drawable.parasite
                }

                it.containsKey("killbill") -> {
                    currentMovie = it.getString("killbill") ?: ""
                    R.drawable.killbill
                }
                it.containsKey("goodfellas") -> {
                    currentMovie = it.getString("goodfellas") ?: ""
                    R.drawable.goodfellas
                }
                it.containsKey("greenmile") -> {
                    currentMovie = it.getString("greenmile") ?: ""
                    R.drawable.greenmile
                }

                it.containsKey("clockwork") -> {
                    currentMovie = it.getString("clockwork") ?: ""
                    R.drawable.a_clockwork_orange
                }
                it.containsKey("american") -> {
                    currentMovie = it.getString("american") ?: ""
                    R.drawable.american_beauty
                }
                it.containsKey("django") -> {
                    currentMovie = it.getString("django") ?: ""
                    R.drawable.django
                }
                it.containsKey("will") -> {
                    currentMovie = it.getString("will") ?: ""
                    R.drawable.good_will_hunting
                }
                it.containsKey("memento") -> {
                    currentMovie = it.getString("memento") ?: ""
                    R.drawable.memento
                }
                it.containsKey("whiplash") -> {
                    currentMovie = it.getString("whiplash") ?: ""
                    R.drawable.whiplash
                }

                it.containsKey("seven") -> {
                    currentMovie = it.getString("seven") ?: ""
                    R.drawable.seven
                }
                it.containsKey("taxiDriver") -> {
                    currentMovie = it.getString("taxiDriver") ?: ""
                    R.drawable.taxi_driver
                }
                it.containsKey("wall") -> {
                    currentMovie = it.getString("wall") ?: ""
                    R.drawable.wall_e
                }

                else -> {
                    currentMovie = ""
                    0
                }
            }
            shuffleWord()
            binding.movieTitle.text = currentMovieShuffled
            binding.moviePicture.setImageResource(movieKey)
        }

        val back: Button = binding.back
        val submit: Button = binding.submit
        back.setOnClickListener {
            playButtonSound()
            findNavController().navigateUp()
        }

        if (gameViewModel.isMovieGuessed(currentMovie)) {
            binding.submit.isEnabled = false
            binding.checkmark.visibility = View.VISIBLE
            binding.guess.setText(currentMovie)
            binding.guess.isEnabled = false
        }

        submit.setOnClickListener {
            playButtonSound()
            onSubmitWord()
        }
    }

    /**
     * Submits the user's guessed word and handles the logic based on whether it is correct or not.
     */
    private fun onSubmitWord() {
        val playerWord = binding.guess.text.toString()
        if (isUserWordCorrect(playerWord)) {
            updateScore(100)
            gameViewModel.setGuessed(currentMovie)
            binding.submit.isEnabled = false
            binding.checkmark.visibility = View.VISIBLE
            binding.guess.isEnabled = false

            handler.postDelayed({
                findNavController().navigateUp()
            }, 1000)
        } else {
            showToast("The Guessed Name Is Incorrect", R.drawable.incorrect)
        }
    }

    /**
     * Checks if the user's guessed word matches the current movie's title.
     *
     * @param playerWord The word guessed by the player.
     * @return `true` if the player's word is correct, `false` otherwise.
     */
    private fun isUserWordCorrect(playerWord: String): Boolean {
        return playerWord.equals(currentMovie, true)
    }

    /**
     * Shuffles the characters of the current movie's title.
     */
    private fun shuffleWord() {
        val words = currentMovie.split(" ")
        val shuffledWords = words.map { word ->
            val shuffledChars = word.toCharArray().toList().shuffled().toCharArray()
            String(shuffledChars)
        }
        currentMovieShuffled = shuffledWords.joinToString(" ")
    }

    /**
     * Updates the score with the given points and displays the updated score in the UI.
     *
     * @param points The points to add to the score.
     */
    private fun updateScore(points: Int) {
        gameViewModel.updateScore(points)
        binding.score.text = gameViewModel.getScore().toString()
    }

    /**
     * Plays the button click sound if the sound is not muted.
     */
    private fun playButtonSound() {
        if (!gameViewModel.getMute()) {
            if (buttonSoundPlayer.isPlaying) {
                buttonSoundPlayer.stop()
                buttonSoundPlayer.reset()
            }
            buttonSoundPlayer.start()
        }
    }

    /**
     * Displays a toast message with the specified message and icon.
     *
     * @param message The message to display in the toast.
     * @param iconResId The resource ID of the icon to display in the toast.
     */
    private fun showToast(message: String, iconResId: Int) {
        val toast = Toast(requireContext())
        val toastView = layoutInflater.inflate(R.layout.toast_notification, null)
        val textViewMessage = toastView.findViewById<TextView>(R.id.toastText)
        val imageViewIcon = toastView.findViewById<ImageView>(R.id.toastIcon)
        textViewMessage.text = message
        imageViewIcon.setImageResource(iconResId)
        imageViewIcon.visibility = View.VISIBLE
        toast.view = toastView
        toast.setGravity(Gravity.CENTER, 0, -350)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    /**
     * Releases resources and cleans up references when the fragment's view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        buttonSoundPlayer.release()
    }
}
