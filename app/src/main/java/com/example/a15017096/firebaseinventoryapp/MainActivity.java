package com.example.a15017096.firebaseinventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvInventory;
    private ArrayList<Inventory> alInventory;
    private ArrayAdapter<Inventory> aaInventory;
    Spinner spinner;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studentListRef;
    private ArrayList<Inventory> alInventoryNew;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvInventory = (ListView)findViewById(R.id.lvInventory);
        alInventory = new ArrayList<Inventory>();
        alInventoryNew = new ArrayList<Inventory>();
        aaInventory = new ArrayAdapter<Inventory>(this, android.R.layout.simple_list_item_1, alInventoryNew);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        lvInventory.setAdapter(aaInventory);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        firebaseDatabase = FirebaseDatabase.getInstance();
        studentListRef = firebaseDatabase.getReference("inventory");

        studentListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("MainActivity", "onChildAdded()");
                Inventory inven1 = dataSnapshot.getValue(Inventory.class);
                if (inven1 != null) {
                    inven1.setId(dataSnapshot.getKey());
                    alInventory.add(inven1);
                    alInventoryNew = alInventory;
                    aaInventory.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("MainActivity", "onChildChanged()");
                String selectedId = dataSnapshot.getKey();
                Inventory inven1 = dataSnapshot.getValue(Inventory.class);
                if (inven1 != null) {
                    for (int i = 0; i < alInventory.size(); i++) {
                        if (alInventory.get(i).getId().equals(selectedId)) {
                            inven1.setId(selectedId);
                            alInventory.set(i, inven1);
                        }
                    }
                    alInventoryNew = alInventory;
                    aaInventory.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("MainActivity", "onChildRemoved()");
                String selectedId = dataSnapshot.getKey();
                for(int i= 0; i < alInventory.size(); i++) {
                    if (alInventory.get(i).getId().equals(selectedId)) {
                        alInventory.remove(i);
                    }
                }
                alInventoryNew = alInventory;
                aaInventory.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("MainActivity", "onChildMoved()");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", "Database error occurred", databaseError.toException());
            }
        });

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Inventory s1 = alInventoryNew.get(position);
                Intent intent = new Intent(MainActivity.this, InventoryDetailActivity.class);
                intent.putExtra("Inventory", s1);
                startActivityForResult(intent, 1);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getBaseContext(), AddInventoryActivity.class);
                startActivity(i);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                alInventoryNew = new ArrayList<Inventory>();
                String brand = parentView.getItemAtPosition(position).toString();
                for(int i = 0;i<alInventory.size();i++){
                    if(alInventory.get(i).getBrand().equals(brand)){
                        alInventoryNew.add(alInventory.get(i));
                    }
                }
                if(!brand.equals("")) {
                    aaInventory = new ArrayAdapter<Inventory>(MainActivity.this, android.R.layout.simple_list_item_1, alInventoryNew);
                    lvInventory.setAdapter(aaInventory);
                    aaInventory.notifyDataSetChanged();
                } else {
                    alInventoryNew = alInventory;
                    aaInventory = new ArrayAdapter<Inventory>(MainActivity.this, android.R.layout.simple_list_item_1, alInventoryNew);
                    lvInventory.setAdapter(aaInventory);
                    aaInventory.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            Intent i = new Intent(getBaseContext(), loginActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

}
