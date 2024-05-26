package com.example.playlistmaker.mediateca.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentNewPlaylistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class NewPlayListFragment : Fragment() {

    private lateinit var binding: FragmentNewPlaylistBinding

    private val newPlayListViewModel by viewModel<NewPlayListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // работа с картинками в галереии фотоПикер
        val pickImage =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.addImagePlaylistCover.setImageURI(uri)
                    saveImageToStorage(uri)
                } else {
// расписать тоаст
                }
            }


        binding.addImagePlaylistCover.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.createNewPlaylist.setOnClickListener {
            val title = binding.nameNewPlaylistEdittext.text.toString()
            val description = binding.descriptionPlaylistEditText.text.toString()
            val filePath = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "imagealbom"
            )
            val imagePlaylistUri = File(filePath, "cover.jpg").toString()

            newPlayListViewModel.createNewPlaylist(title, description, imagePlaylistUri)

        }

        binding.arrowBack.setOnClickListener {

            findNavController().navigate(R.id.action_newPlayListFragment_to_mediaFragment)
        }
    }


    //в этом методе я сохраняю и достаю картинку из папки
    private fun saveImageToStorage(uri: Uri) {
        val filePath = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "image_album"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "cover_album.JPEG")

        val inputStream = requireContext().contentResolver.openInputStream(uri)

        val outputStream = FileOutputStream(file)

        BitmapFactory.decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }

    companion object {

        fun newInstance() = NewPlayListFragment()

    }
}
