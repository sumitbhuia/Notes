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





        1. Fragments
        -------------

        - Add navHost to activityMain.xml
        - Open make a navigation package
            -  A navigation resource file - nav_graph.xml
                - Inside it add fragments and set home fragment




       2. ROOM
       -------

       -
*/
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.rgb(19,16,14)
        window.navigationBarColor = Color.rgb(19,16,14)


        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application,noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}

