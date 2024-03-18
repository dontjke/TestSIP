package com.stepanov.testsip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.stepanov.testsip.R
import com.stepanov.testsip.databinding.FragmentUserDetailsBinding
import com.stepanov.testsip.utils.KEY_BUNDLE_USER
import com.stepanov.testsip.viewmodel.details.UserDetailsViewModel
import com.stepanov.testsip.viewmodel.users.UsersState

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding: FragmentUserDetailsBinding
        get() {
            return _binding!!
        }

    private val userDetailsViewModel: UserDetailsViewModel by lazy {
        ViewModelProvider(this)[UserDetailsViewModel::class.java]
    }
    private var userIdBundle: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userIdBundle = requireArguments().getInt(KEY_BUNDLE_USER)

        val observer = { data: UsersState -> renderData(data) }
        userDetailsViewModel.getLiveData().observe(viewLifecycleOwner, observer)
        userDetailsViewModel.getUser()
    }

    private fun renderData(data: UsersState) {
        when (data) {
            is UsersState.Error -> {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    data.error.message ?: getString(R.string.error),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            is UsersState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is UsersState.Success -> {
                binding.progressBar.visibility = View.GONE
                with(binding) {
                    val userData = data.usersList[userIdBundle]
                    nameTextView.text = userData.name
                    phoneTextView.text = buildString {
                        append(getString(R.string.phone_details))
                        append(userData.phone)
                    }
                    usernameTextView.text = buildString {
                        append(getString(R.string.username))
                        append(userData.username)
                    }
                    websiteTextView.text = buildString {
                        append(getString(R.string.website))
                        append(userData.website)
                    }
                    addressTextView.text = userData.address.city
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            UserDetailsFragment().apply {
                arguments = bundle
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}