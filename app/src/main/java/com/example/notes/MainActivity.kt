package com.example.notes

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.room.NoteDatabase
import com.example.notes.room.NoteRepository
import com.example.notes.viewmodel.NoteViewModel
import com.example.notes.viewmodel.NoteViewModelFactory


/*   Packages
     --------
     - Fragments
        - Home Fragment
        - Note fragment
        - New Note fragment

    - ROOM
        - DAO interface
        - Entity class
        - Database
        - Repository

    - View Model
        - ViewModel class
        - ViewModel factory

    - Adapter
        - Adapter class



    MainActivity (Activity)
   |
   +- HomeFragment (Initially displayed)  setup in (nav_graph.xml)
       |
       +- RecyclerView (Shows list of notes)
       |
       +- FloatingActionButton (Navigates to NewNoteFragment)
       |
       +- SearchView (Filters notes based on user input)
   |
   +- NewNoteFragment (Accessible from HomeFragment) setup in (nav_graph.xml)
   |
   +- UpdateNoteFragment (Accessible from HomeFragment) setup in (nav_graph.xml)



*/
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel


    //As we have connected the view model of Activity Main to all fragments , as changes there will reflact here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting up binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setting static navigation and status bar colour
        window.statusBarColor = Color.rgb(19,16,14)
        window.navigationBarColor = Color.rgb(19,16,14)


        // Calling custom function to setup viewModel , that hold the data and views that are safe from config. changes
        setUpViewModel()
    }

    // Function for setting up view-model
    private fun setUpViewModel() {
        // Setting up repo -> feeding database
        val noteRepository = NoteRepository(NoteDatabase(this))
        // Setting up factory
        val viewModelProviderFactory = NoteViewModelFactory(application,noteRepository)
        //Setting up viewModel
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}

