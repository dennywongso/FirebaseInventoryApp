package com.example.a15017096.firebaseinventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInventoryActivity extends AppCompatActivity {
    private static final String TAG = "AddInventoryActivity";
    private EditText etName, etCost;
    private Button btnAdd;
    Spinner spinner;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studentListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        etName = (EditText)findViewById(R.id.etName);
        etCost = (EditText)findViewById(R.id.etCost);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        studentListRef = firebaseDatabase.getReference("/inventory");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO…
                String brand = spinner.getSelectedItem().toString();
                if(!brand.equals("") && !etCost.getText().toString().equals("")) {
                String name = etName.getText().toString();
                int cost = Integer.parseInt(etCost.getText().toString());
                Inventory i1 = new Inventory(name, cost,brand);

                studentListRef.push().setValue(i1);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddInventoryActivity.this,"Please fill in all form",Toast.LENGTH_SHORT).show();
            }


            }
        });
    }
}
