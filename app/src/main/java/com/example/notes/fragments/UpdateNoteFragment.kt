package com.example.notes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentUpdateNoteBinding
import com.example.notes.room.Note
import com.example.notes.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var  notesViewModel : NoteViewModel
    private lateinit var currentNote : Note
    // Since the Update Note Fragment contains arguments in nav_graph
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding =FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Changes her will reflect in the parent view
        notesViewModel = (activity as MainActivity).noteViewModel


  /*       Retrieving current note
           Extracts the note object, representing the note to be updated, from the navigation arguments using safe args.
           Safe args ensure the retrieved value matches the expected type (Note in this case),
           avoiding potential crashes due to type mismatches.

           CurrentNote : Note  -> args.note , is an navigation argument helps feed the clicked argument to current note
   */

        currentNote = args.note!!

        // Populating the current note view with the  clicked view fetched by args.note of the update note
        binding.editTextNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.editTextNoteBodyUpdate.setText(currentNote.noteBody)

        // if the user update the note
        binding.fabDone.setOnClickListener {
            val title = binding.editTextNoteTitleUpdate.text.toString().trim()
            val body = binding.editTextNoteBodyUpdate.text.toString().trim()

            if (title.isNotEmpty()){
                //If the title field in not empty , pass the data to Note data class
                val note = Note(currentNote.id,title, body)
                // Pass , instance note to fn update
                notesViewModel.updateNote(note)
                //Redirect back to home page after saving , automatically
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)

                //If title is empty , need o fill title at least
            }
            else{
                Toast.makeText(
                    context,
                    "Please enter note title",
                    Toast.LENGTH_LONG).show()
            }
            }
    }
    private fun deleteNote() {

        //This  will create a alert dialog box
        AlertDialog.Builder(activity).apply {

            //Title of the dialog box
            setTitle("Delete Note")
            //Message of the dialog box
            setMessage("You want to delete this Note?")

            // Button 1 , positive of the box = delete
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)

                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            //Button 2 , negative of the voc = cancel
            setNegativeButton("Cancel", null)
        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
