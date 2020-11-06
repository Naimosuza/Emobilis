package com.example.phonebook;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_contact extends AppCompatActivity {

    EditText contact_name, contact_num, contact_email, contact_homepage;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        db = new DatabaseHandler(getApplicationContext());
        contact_name = findViewById(R.id.input_name);
        contact_num = findViewById(R.id.input_number);
        contact_email = findViewById(R.id.input_email);
        contact_homepage = findViewById(R.id.input_homepage);
        contact_homepage.setText("http://");
        Button btn_cancel = findViewById(R.id.button_cancel);
        final Button btn_confirm = findViewById(R.id.button_confirm);
        btn_confirm.setEnabled(false); //Disable the confirm button

        /*Action for confirm button in add contact activity*/
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast_text = "";
                int id = db.getContactsCount() + 1; //The first id is 1
                String name = contact_name.getText().toString();
                String number = contact_num.getText().toString();
                String email = contact_email.getText().toString();
                String home_page = contact_homepage.getText().toString();
                contact_info contact = new contact_info(id, name, number, email, home_page);
                db.addContact(contact);
                Context context = getApplicationContext();
                /*
                If the id generated is still equal to the count of contact + 1 which is same as the statement
                at the above, that mean the contact is not added because the inputted name is not unique,
                it will output error message.
                If the id generated is not equal to the count of contact + 1 because the count of contact + 1
                has increased by 1 by the new contact,that mean the inputted name is unique,
                it will output success message.
                */
                if (id == db.getContactsCount() + 1) {
                    toast_text = "Failed, contact name must be unique!";
                } else {
                    toast_text = "Contact " + name + " has been created!";
                }
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, toast_text, duration);
                toast.show();
                finish();
            }
        });

        /*Action for cancel button in add contact activity*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*Input Validation to prevent empty or null name*/
        contact_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*The confirm button is only enable when there is text in the text box of contact name*/
                btn_confirm.setEnabled(!contact_name.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
