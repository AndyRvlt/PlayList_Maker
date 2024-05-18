package com.example.playlistmaker.mediateca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSavePlaylistsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavePlaylistsFragment : Fragment() {
    private lateinit var binding: FragmentSavePlaylistsBinding

    private val savePlaylistViewModel by viewModel<SavePlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavePlaylistsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createNewPlaylist.setOnClickListener {

//            childFragmentManager.beginTransaction().apply {
//                replace(R.id.savePlaylistsFragment, NewPlayListFragment())
//                addToBackStack(null)
//                commit()
//            }

             findNavController().navigate(R.id.action_savePlaylistsFragment_to_favoriteTracksFragment)
        }
    }

    companion object {

        fun newInstance() = SavePlaylistsFragment()

    }

}
//replace(R.id.fragment_container, secondFragment) // R.id.fragment_container - это ID ViewGroup (например, FrameLayout) в вашей activity_main.xml
//addToBackStack(null) // Добавляем транзакцию в стек возврата
//commit() // Применяем изменения