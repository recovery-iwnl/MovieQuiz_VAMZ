package com.example.semestralna_praca_vamz_movie_quiz.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.semestralna_praca_vamz_movie_quiz.GameViewModel
import com.example.semestralna_praca_vamz_movie_quiz.R
import com.example.semestralna_praca_vamz_movie_quiz.databinding.FragmentChooseLevelBinding


/**
 * A simple [Fragment] subclass that represents the level selection screen of the movie quiz game.
 * Use the [ChooseLevelFragment.newInstance] factory method to create an instance of this fragment.
 */
class ChooseLevelFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private var bind: FragmentChooseLevelBinding? = null
    private val binding get() = bind!!
    private lateinit var buttonSoundPlayer: MediaPlayer

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
        bind = FragmentChooseLevelBinding.inflate(inflater, container, false)
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

        val level1: Button = binding.LEVEL1
        level1.setOnClickListener {
            playButtonSound()
            Navigation.findNavController(view).navigate(R.id.action_chooseLevelFragment_to_levelOneFragment)
        }

        val level2: Button = binding.LEVEL2
        level2.setOnClickListener {
            playButtonSound()
            Navigation.findNavController(view).navigate(R.id.action_chooseLevelFragment_to_levelTwoFragment)
        }

        val level3: Button = binding.LEVEL3
        level3.setOnClickListener {
            playButtonSound()
            if (gameViewModel.getScore() < 1000) {
                val toast = Toast(requireContext())
                val toastView = layoutInflater.inflate(R.layout.toast_notification, null)
                val textViewMessage = toastView.findViewById<TextView>(R.id.toastText)
                textViewMessage.text = "Level 3 is currently locked!"
                toast.view = toastView
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            } else {
                Navigation.findNavController(view).navigate(R.id.action_chooseLevelFragment_to_levelThreeFragment)
            }
        }

        if (gameViewModel.getScore() >= 1000) {
            binding.lock.visibility = View.INVISIBLE
        }

        if (gameViewModel.areLevel1MoviesGuessed()) {
            binding.check1.visibility = View.VISIBLE
        }

        if (gameViewModel.areLevel2MoviesGuessed()) {
            binding.check2.visibility = View.VISIBLE
        }

        if (gameViewModel.areLevel3MoviesGuessed()) {
            binding.check3.visibility = View.VISIBLE
        }

        val back: Button = binding.back
        back.setOnClickListener {
            playButtonSound()
            findNavController().navigateUp()
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
