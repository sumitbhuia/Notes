package com.example.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding.inflate
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.room.Note
import com.example.notes.viewUI.NoteAdapter
import com.example.notes.viewmodel.NoteViewModel

/*
     All the functional code for what a Home Fragment does
     ------------------------------------------------------

    Yes, a class in Kotlin can have two super interfaces methods , but only one super class.
    This is because Kotlin allows a class to implement multiple interfaces , but ca extend only one super class.
    When a class implements multiple interfaces, it must implement all of the methods declared in those interfaces.
    Yes, a class can inherit from a fragment in Android.


*/

    //  : Fragment(R.layout.fragment_home) indicates that HomeFragment inherits from the Fragment class
    //  , SearchView.OnQueryTextListener means that HomeFragment also implements the SearchView.OnQueryTextListener interface.
    //  This interface allows the fragment to receive and respond to events related to user input in a SearchView widget.

class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener{



    //  private var _binding: FragmentHomeBinding? = null
    //  Declares a private, nullable variable named _binding of type FragmentHomeBinding.
    //  name? means name call hold null values
    //  FragmentHomeBinding is a class generated by Android's View Binding feature for accessing UI elements from the fragment_home layout.
    //  It's initially set to null because the binding hasn't been created yet .
    private var _binding: FragmentHomeBinding? = null


    //  private val binding get() = _binding!!
    //  Creates a private, non-null property named binding.
    //  It acts as a convenient accessor for the _binding property.
    //  It uses the !! operator (non-null assertion operator) to force-unwrap _binding, ensuring it's not null when accessed.
    //  ******** This means you can use binding directly to interact with UI elements without null checks *********
    private val binding get() = _binding!!
    private lateinit var  notesViewModel : NoteViewModel
    private lateinit var  noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Weather need to implement menu option in action bar or not --> boolean
        setHasOptionsMenu(true)
    }

    //What happens when creating a View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
 /*
         Inflate the layout for this fragment -> making a real copy of bluePrint (layout file) on the phone screen

         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        This approach uses the DataBindingUtil class, which is part of the older DataBinding Library.
        It accepts several parameters:
        - inflater: LayoutInflater object to inflate the layout.
        - R.layout.fragment_home: Resource ID of the layout file.
        - container: Optional parent view group where the inflated layout should be attached (usually set to null).
        - A parent view group is that inside which other views are present , like inside constraint layout or relative layout
        - false: Indicates whether the inflated layout should be attached to the parent (usually false).
        It returns a ViewDataBinding object, which provides access to views and data binding expressions declared in the layout.

        The above approach is , not used now , the approach below is what we prefer

 */
       _binding = FragmentHomeBinding.inflate(inflater, container,false)

        //Returns the root view of the inflated layout (the top-most view in the hierarchy).
        //This view becomes the fragment's content view, displayed on the screen.
        return binding.root
    }


    // What to do after view is created , but nothing attached ?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  This line retrieves a shared ViewModel instance called noteViewModel from the parent MainActivity.
        //  ViewModels are designed to hold and manage data across configuration changes.
        // Changes here  will reflect in the parent view
        notesViewModel = (activity as  MainActivity).noteViewModel


        // Custom function to setUp recycler view
        setUpRecyclerView()

        // action for clicking floating action button
        binding.fabAddNote.setOnClickListener {
            // to use navigation actions , use findNavController
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }
    private fun setUpRecyclerView() {

        //This line creates an instance of a NoteAdapter,
        // which is responsible for managing the individual note items within the RecyclerView.
        noteAdapter = NoteAdapter()



 //Configuring the RecyclerView
        binding.recyclerView.apply{
            // Setting the layout for recycler view
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

            //Optimizes performance by indicating that the RecyclerView's item size remains constant.
            setHasFixedSize(true)

            //Attaches the noteAdapter to the RecyclerView, allowing it to populate the list with items.
            adapter = noteAdapter
        }

//Observing Data Changes
        // This block ensures that activity is not null before proceeding
        activity?.let{
            //Observes changes in the note list from the notesViewModel.
            //The observer is attached to the viewLifecycleOwner, ensuring it's only active when the fragment is visible.
            notesViewModel.getAllNotes().observe(viewLifecycleOwner) {
                //Updates the noteAdapter with the new list of notes using a DiffUtil.ItemCallback under the hood for efficient updates.
                note -> noteAdapter.differ.submitList(note)

                //Custom function for updating UI
                updateUI(note)
            }
        }
    }

    private fun updateUI(note: List<Note>?) {
        //This check ensures the note list is not null before proceeding.
        if (note != null) {
            /*
            Feature     |  Not Null	                          | Not Empty
            --------       ----------                         -----------
            Applies to  |	Any data type                     |	Strings, arrays, collections
            Meaning     |	Value exists and has a definition | Contains at least one element */

            // This inner check verifies if the note list contains any elements (i.e., it's not empty).
            if (note.isNotEmpty()) {
                // If note List not empty do this with the cardView and recyclerView of home fragment
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                //Else
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }


// Setting up the  option menu  in the action bar -> here search bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // Removes any existing menu items from the menu before starting fresh.
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        // Getting the search menu option for home fragment
        val searchItem  : MenuItem = menu.findItem(R.id.menu_search)
        //Casts the action view associated with the searchItem as a SearchView
        val searchView : SearchView = searchItem.actionView as SearchView


        searchView.apply {
            // searchView.isSubmitButtonEnabled = false
            // we don't to press any button after searching something
            isSubmitButtonEnabled = false
            // Sets the fragment itself (this@HomeFragment) as the listener for any text changes within the SearchView.
            // This implies the fragment will handle user input in the search bar.
            setOnQueryTextListener(this@HomeFragment)
        }
    }


//Member function auto implement
    //called when the user submits a search query using the "search" button or keyboard enter
    override fun onQueryTextSubmit(query: String?): Boolean {
       //searchNote(query)
        return false
    }

    //  gets called whenever the user types or modifies text within the search bar.
    override fun onQueryTextChange(newText: String?): Boolean {
        // if search field is not empty
        if(newText != null) {
            // custom search note
            searchNote(newText)
        }
        // It always returns true, indicating the event was handled.
        return true
    }

    private fun searchNote(query: String?) {
        // this variable will hold a query string , wildcards are used to widen the area of search withing a database
        val searchQuery = "%$query"
        //It observes the changes to the returned list of notes (presumably filtered based on the query).
        notesViewModel.searchNote(searchQuery).observe(this) {
            //When the observed list changes, it updates the noteAdapter using the differ property for efficient updates.
            list -> noteAdapter.differ.submitList(list)
        }
    }

    //
    override fun onDestroy() {
        // It calls the superclass's onDestroy for standard cleanup.
        super.onDestroy()
            // sets binding member variable null , releasing resources associated with data binding library
        _binding=null

    }
}