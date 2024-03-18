package com.stepanov.testsip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.stepanov.testsip.R
import com.stepanov.testsip.databinding.FragmentUsersBinding
import com.stepanov.testsip.utils.KEY_BUNDLE_USER
import com.stepanov.testsip.view.adapter.OnUserClickListener
import com.stepanov.testsip.view.adapter.UsersRecyclerViewAdapter
import com.stepanov.testsip.viewmodel.users.UsersState
import com.stepanov.testsip.viewmodel.users.UsersViewModel

class UsersFragment : Fragment(), OnUserClickListener {
    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() {
            return _binding!!
        }

    private val adapter = UsersRecyclerViewAdapter(this)
    private val usersViewModel: UsersViewModel by lazy {
        ViewModelProvider(this)[UsersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        val observer = { data: UsersState -> renderData(data) }
        usersViewModel.getLiveData().observe(viewLifecycleOwner, observer)
        usersViewModel.getUsers()
    }


    private fun renderData(data: UsersState) {
        when (data) {
            is UsersState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    data.error.message ?: getString(R.string.error),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            is UsersState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is UsersState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.usersList)
            }
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(id: Int) {
       requireActivity().supportFragmentManager
           .beginTransaction()
           .add(R.id.container,UserDetailsFragment.newInstance(Bundle().apply {
               putInt(KEY_BUNDLE_USER, id)
           }))
           .addToBackStack("")
           .commit()
    }
}