package com.example.semestralna_praca_vamz_movie_quiz.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.semestralna_praca_vamz_movie_quiz.GameViewModel
import com.example.semestralna_praca_vamz_movie_quiz.R
import com.example.semestralna_praca_vamz_movie_quiz.databinding.FragmentMenuBinding

/**
 * A simple [Fragment] subclass that represents the menu screen of the movie quiz game.
 * Use the [MenuFragment.newInstance] factory method to create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var buttonSoundPlayer: MediaPlayer
    private var bind: FragmentMenuBinding? = null
    private val binding get() = bind!!

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
        bind = FragmentMenuBinding.inflate(inflater, container, false)
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
        val playButton: Button = binding.playButton
        playButton.setOnClickListener {
            playButtonSound()
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_chooseLevelFragment)
        }
        val settingButton: Button = binding.settingButton
        settingButton.setOnClickListener {
            playButtonSound()
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_settingsFragment)
        }
    }

    /**
     * Plays the button click sound if the game is not muted.
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
        bind = null
    }
}
