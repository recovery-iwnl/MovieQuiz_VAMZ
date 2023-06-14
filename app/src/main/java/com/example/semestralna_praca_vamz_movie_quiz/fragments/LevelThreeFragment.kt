package com.example.semestralna_praca_vamz_movie_quiz.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.semestralna_praca_vamz_movie_quiz.GameViewModel
import com.example.semestralna_praca_vamz_movie_quiz.R
import com.example.semestralna_praca_vamz_movie_quiz.databinding.FragmentLevelThreeBinding

/**
 * A [Fragment] subclass that represents the third level of the movie quiz game.
 */
class LevelThreeFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private var binding: FragmentLevelThreeBinding? = null
    private lateinit var buttonSoundPlayer: MediaPlayer

    private val moviesToCheck = listOf(
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

    private val bundles = listOf(
        "clockwork",
        "american",
        "django",
        "will",
        "memento",
        "whiplash",
        "seven",
        "taxiDriver",
        "wall"
    )

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
        binding = FragmentLevelThreeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    /**
     * Initializes the UI components and sets up click listeners for buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        buttonSoundPlayer = MediaPlayer.create(requireContext(), R.raw.click)
        binding?.score?.text = gameViewModel.getScore().toString()

        val buttons = listOf(
            binding?.clockwork,
            binding?.americanBeauty,
            binding?.django,
            binding?.goodWill,
            binding?.memento,
            binding?.whiplash,
            binding?.seven,
            binding?.taxi,
            binding?.walle
        )

        buttons.forEachIndexed { index, button ->
            button?.setOnClickListener {
                val movieTitle = moviesToCheck[index]
                bundle.putString(bundles[index], movieTitle)
                playButtonSound()
                Navigation.findNavController(view)
                    .navigate(R.id.action_levelThreeFragment_to_movieGameFragment, bundle)
            }
            if (gameViewModel.isMovieGuessed(moviesToCheck[index])) {
                button?.alpha = 0.4F
            }
        }

        binding?.back?.setOnClickListener {
            playButtonSound()
            findNavController().navigateUp()
        }
    }

    /**
     * Plays the button click sound if the sound is enabled in the game settings.
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
     * Releases resources and cleans up references when the fragment's view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        buttonSoundPlayer.release()
        binding = null
    }
}
