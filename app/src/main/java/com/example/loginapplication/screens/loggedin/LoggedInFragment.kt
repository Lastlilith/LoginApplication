package com.example.loginapplication.screens.loggedin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loginapplication.R
import com.example.loginapplication.databinding.FragmentLoggedInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoggedInFragment : Fragment() {

    private val viewModel: LoggedInViewModel by viewModels()

    private var _binding: FragmentLoggedInBinding? = null
    private val binding: FragmentLoggedInBinding
        get() = _binding ?: throw RuntimeException("FragmentLoggedInBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logOut -> {
                    viewModel.logOutClicked()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}