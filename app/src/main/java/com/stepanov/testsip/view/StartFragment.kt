package com.stepanov.testsip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stepanov.testsip.R
import com.stepanov.testsip.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usersButton.setOnClickListener {
            launchFragment(UsersFragment.newInstance())
        }
        binding.videoButton.setOnClickListener {
            launchFragment(VideoFragment.newInstance())
        }
    }

    private fun launchFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}