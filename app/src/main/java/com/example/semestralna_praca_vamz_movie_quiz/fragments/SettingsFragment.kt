package com.example.semestralna_praca_vamz_movie_quiz.fragments

import android.content.DialogInterface
import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.semestralna_praca_vamz_movie_quiz.GameViewModel
import com.example.semestralna_praca_vamz_movie_quiz.MainActivity.Companion.mediaPlayer
import com.example.semestralna_praca_vamz_movie_quiz.R
import com.example.semestralna_praca_vamz_movie_quiz.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass that represents the settings screen of the movie quiz game.
 * Use the [MenuFragment.newInstance] factory method to create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var buttonSoundPlayer: MediaPlayer
    private var bind: FragmentSettingsBinding? = null
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
        bind = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Initializes the UI components and sets up click listeners for buttons and switches.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSoundPlayer = MediaPlayer.create(requireContext(), R.raw.click)
        binding.score.text = gameViewModel.getScore().toString()
        val switch: SwitchCompat = binding.mute
        switch.isChecked = gameViewModel.getMute()

        switch.setOnCheckedChangeListener { _, isChecked ->
            playButtonSound()
            gameViewModel.setMute(isChecked)
            if (isChecked) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }

        val reset: Button = binding.resetScore
        reset.setOnClickListener {
            playButtonSound()
            showConfirmationDialog()
        }

        val back: Button = binding.back
        back.setOnClickListener {
            playButtonSound()
            findNavController().navigateUp()
        }
    }

    /**
     * Shows a confirmation dialog for resetting the score.
     */
    private fun showConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Warning!")
        alertDialogBuilder.setIcon(R.drawable.warning)
        alertDialogBuilder.setMessage("Do you really want to reset your game? All your progress will be lost!")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            playButtonSound()
            gameViewModel.resetScore()
            gameViewModel.resetGuessed()
            binding.score.text = gameViewModel.getScore().toString()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            playButtonSound()
            dialog.dismiss()
        }
        alertDialogBuilder.show()
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
