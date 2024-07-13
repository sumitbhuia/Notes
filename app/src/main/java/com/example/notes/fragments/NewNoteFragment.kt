package com.example.notes.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentNewNoteBinding
import com.example.notes.room.Note
import com.example.notes.viewmodel.NoteViewModel



// We don't implement the SearchView.OnQueryTextListener interface as we don't use search-view
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var  notesViewModel : NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Changes here  will be reflected in the parent view
        notesViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }
    private fun saveNote(view: View) {
        val noteTitle = binding.editTextNoteTitle.text.toString().trim()
        val noteBody = binding.editTextNoteBody.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle, noteBody)
            notesViewModel.addNote(note)
            Toast.makeText(mView.context,
                "Saved",
                Toast.LENGTH_LONG).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }
        else{
            Toast.makeText(
                mView.context,
                "Please enter note title",
                Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save->{
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null

    }

}