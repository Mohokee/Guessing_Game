package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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

        //set XML var that holds the viewmodel equal to the viewmodel
        binding.gameViewModel = viewModel
        //set the lifecycle owner to viewlifecycleowner so the layout has direct
        //access to live data
        binding.lifecycleOwner = viewLifecycleOwner

        /*updateScreen() -unneeded with Observer to watch for mutable Live Data
        NO LONGER NECESSARY thanks to binding.viewlifecycleowner being set to viewLifeCycleOwner
        //How to add observers to listen for changes in the viewModel Value
        viewModel.incorrectGuesses.observe(viewLifecycleOwner,Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect Guesses: $newValue"
        })

        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "Lives Left: $newValue"
        })
        */

        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })
        //end observers

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if(newValue){
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)

            }
        })

        //On clicking the guess button
        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            //updateScreen() -unneeded with live data
            /*if(viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }*/
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   /*
    This fun is unneeded with live data observer
    fun updateScreen(){
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "You have ${viewModel.livesLeft} left"
        binding.incorrectGuesses.text = "Incorrect Guesses: ${viewModel.incorrectGuesses}"
    }*
    */


}