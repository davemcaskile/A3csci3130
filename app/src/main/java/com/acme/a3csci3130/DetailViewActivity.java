package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DetailViewActivity extends Activity {

    private EditText nameField, addressField, numberField, primaryField, provinceField;
    private MyApplicationData appState;
    Contact receivedPersonInfo;
    String businessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.readname);
        addressField = (EditText) findViewById(R.id.readaddress);
        numberField = (EditText) findViewById(R.id.readnumber);
        primaryField = (EditText) findViewById(R.id.readtype);
        provinceField = (EditText) findViewById(R.id.readprovince);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            addressField.setText(receivedPersonInfo.address);
            numberField.setText(receivedPersonInfo.number);
            primaryField.setText(receivedPersonInfo.primaryBusiness);
            provinceField.setText(receivedPersonInfo.province);
        }
    }

    public void updateContact(View v){
        businessID = receivedPersonInfo.id;
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        DatabaseReference orig = appState.firebaseReference.child(businessID);

        // Make a hashmap to store the changes
        Map<String, Object> update = new HashMap<String, Object>();

        // Get all the changes made by the user
        String newName = nameField.getText().toString();
        String newAddress = addressField.getText().toString();
        String newNumber = numberField.getText().toString();
        String newType = primaryField.getText().toString();
        String newProvince = provinceField.getText().toString();

        // Add the changes to the hashmap
        update.put("name", newName);
        update.put("address", newAddress);
        update.put("number", newNumber);
        update.put("primary", newType);
        update.put("province", newProvince);

        // Update the databse
        orig.updateChildren(update);
    }

    public void eraseContact(View v)
    {
        // Get the contact
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        // Get the contact id
        businessID = receivedPersonInfo.id;

        // Remove the contact
        appState.firebaseReference.child(businessID).removeValue();
    }
}
