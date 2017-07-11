package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public  String id;
    public  String name;
    public  String address;
    public  String number;
    public  String primaryBusiness;
    public  String province;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Contact(String id, String name, String address, String number, String primaryBusiness, String province){
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
        this.primaryBusiness = primaryBusiness;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("number", number);
        result.put("primaryBusiness", primaryBusiness);
        result.put("province", province);

        return result;
    }
}
