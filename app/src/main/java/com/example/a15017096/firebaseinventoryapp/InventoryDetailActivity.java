package com.example.a15017096.firebaseinventoryapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
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

public class InventoryDetailActivity extends AppCompatActivity {
    private static final String TAG = "InventoryDetailActivity";

    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;
    private Spinner spinner;

    private Inventory inventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        etName = (EditText)findViewById(R.id.etName);
        etCost = (EditText)findViewById(R.id.etCost);
        btnDelete = (Button)findViewById(R.id.buttonDelete);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.phones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("/inventory");
        Intent intent = getIntent();
        inventory = (Inventory) intent.getSerializableExtra("Inventory");
            int spinnerPosition = adapter.getPosition(inventory.getBrand().toString());
            spinner.setSelection(spinnerPosition);


        //Display Student details as retrieved from the intent
        etName.setText(inventory.getName());
        etCost.setText(String.valueOf(inventory.getCost()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = spinner.getSelectedItem().toString();
                if(!brand.equals("") && !etCost.getText().toString().equals("")) {
                    Inventory c1 = new Inventory(etName.getText().toString(), Integer.parseInt(etCost.getText().toString()), brand);
                    inventoryListRef.child(inventory.getId()).setValue(c1);
                    Toast.makeText(getApplicationContext(), "Inventory record updated successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(InventoryDetailActivity.this,"Please fill in all form",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryListRef.child(inventory.getId()).removeValue();
                Toast.makeText(getApplicationContext(), "Inventory record deleted successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);

                finish();

            }
        });


    }
}
