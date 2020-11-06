package com.example.phonebook;

public class contact_info {

    private int _id;
    private String _name;
    private String _phone_number;
    private String _email;
    private String _home_page_address;

    // Empty constructor
    public contact_info() {

    }

    // alternative constructor
    public contact_info(int id, String name, String phone_number, String email, String home_page_address) {
        _id = id;
        _name = name;
        _phone_number = phone_number;
        _email = email;
        _home_page_address = home_page_address;
    }

    //  alternative constructor
    public contact_info(String name, String phone_number, String email, String home_page_address) {
        _name = name;
        _phone_number = phone_number;
        _email = email;
        _home_page_address = home_page_address;
    }

    // get the ID
    public int getID() {
        return _id;
    }

    // set the id
    public void setID(int id) {
        _id = id;
    }

    // get the name
    public String getName() {
        return _name;
    }

    // set the name
    public void setName(String name) {
        _name = name;
    }

    // get the phone number
    public String getPhoneNumber() {
        return _phone_number;
    }

    // set the phone number
    public void setPhoneNumber(String phone_number) {
        _phone_number = phone_number;
    }

    // get the email
    public String getEmail() {
        return _email;
    }

    // set the email
    public void setEmail(String email) {
        _email = email;
    }

    // get the home page address
    public String getHome_page() {
        return _home_page_address;
    }

    // set the phone number
    public void setHome_page(String home_page_address) {
        _home_page_address = home_page_address;
    }
}
