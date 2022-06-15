package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.guessinggame.databinding.FragmentGameBinding
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    //View Binding properties
    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!
    //ADDED W/VIEW MODEL
    lateinit var  viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment with binding
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        //ADDED WITH VIEW MODEL
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        updateScreen()

        //On clicking the guess button
        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if(viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateScreen(){
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "You have ${viewModel.livesLeft} left"
        binding.incorrectGuesses.text = "Incorrect Guesses: ${viewModel.incorrectGuesses}"
    }


}