package com.example.doctorappointment;

import java.io.Serializable;

public class AddressBook implements Serializable {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String email;
    private String contact_number;
    private Boolean isactive;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated(hash = 44583721)
    public AddressBook() {
    }

    public AddressBook(Long id) {
        this.id = id;
    }

    @Generated(hash = 1483669639)
    public AddressBook(Long id, String name, String email, String contact_number, Boolean isactive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact_number = contact_number;
        this.isactive = isactive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
