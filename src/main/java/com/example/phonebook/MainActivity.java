package com.example.phonebook;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton create_new_contact;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Contact List");
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        create_new_contact = findViewById(R.id.floatbutton_add);
        /*Action for create button in main activity*/
        create_new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_contact.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DatabaseHandler(this);
        int count = db.getContactsCount();
        int increment_count = 0;
        /* create a array list of all contact for id retrieval purpose Example: Jack,1,Ali,2,Ming,3,...*/
        final ArrayList<String> array_list = db.getAllContact();
        /* create a array list without id of all contact Example: Jack,Ali,...*/
        final ArrayList<String> array_list_without_id = new ArrayList<>();
        /* insert the name only*/
        for (int i = 0; increment_count < count; i += 2) {
            array_list_without_id.add(array_list.get(i));
            increment_count++;
        }
        /* Set up the list view*/
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list_without_id);
        final ListView contact_views = findViewById(R.id.contact_view);
        contact_views.setAdapter(arrayAdapter);
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> templist = new ArrayList<>();
                for (String temp : array_list_without_id) /*for each loop to traverse the array element of array_list_without_id*/ {
                    /*If temp has at least one word similar to the inputted text*/
                    if (temp.toLowerCase().contains(newText.toLowerCase())) {
                        templist.add(temp); /* add the matched item into temporary list*/
                    }
                }
                /*set up the search view*/
                ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, templist);
                contact_views.setAdapter(adapter);
                return true;
            }
        });

        /*Action for item  the list view of contact_views in main activity*/
        contact_views.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = contact_views.getItemAtPosition(position).toString();
                int position_array_list = array_list.indexOf(text) + 1;
                /*retrieve id*/
                String String_contact_id = array_list.get(position_array_list);
                int contact_id = Integer.parseInt(String_contact_id);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                /*Put id to carry over to other activity*/
                intent.putExtra("id_to_retrieve", contact_id);
                startActivity(intent);
            }
        });
    }
}



