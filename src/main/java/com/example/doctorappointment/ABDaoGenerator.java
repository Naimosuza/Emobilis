package com.example.doctorappointment;

import android.content.Entity;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

public class ABDaoGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.example.doctorappointment.greendao.db") {
            @Override
            public Validator newValidator() {
                return null;
            }

            @Override
            public ValidatorHandler newValidatorHandler() {
                return null;
            }
        }; // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new ABDaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateAll(Schema schema, String s) {
    }

    private static void addTables(final Schema schema) {
        addAddressBookEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addAddressBookEntities(final Schema schema) {
        Entity addressbook = schema.addEntity("AddressBook");
        addressbook.addIdProperty().primaryKey().autoincrement();
        addressbook.addStringProperty("name");
        addressbook.addStringProperty("email");
        addressbook.addStringProperty("contact_number");
        addressbook.addBooleanProperty("isactive");
        return addressbook;
    }

}
