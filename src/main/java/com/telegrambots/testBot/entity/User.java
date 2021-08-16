package com.telegrambots.testBot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "surname")
    @Size(min = 2, max = 30, message = "{user.surname.invalid}")
    @NotBlank(message = "{user.surname.NotBlank}")
    private String surname;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{user.name.invalid}")
    @NotBlank(message = "{user.name.NotBlank}")
    private String name;

    @Column(name = "phone")
    @Pattern(regexp = "^\\s*((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{5,9}$", message = "{user.phone.invalid}")
    private String phone;

    @Column(name = "address")
    @Size(min = 4, max = 150, message = "{user.address.invalid}")
    @NotBlank(message = "{user.address.NotBlank}")
    private String address;

    @Column(name = "work")
    @Size(min = 5, max = 300, message = "{user.work.invalid}")
    @NotBlank(message = "{user.work.NotBlank}")
    private String work;


    public User() {
    }

    public User(int id, String surname, String name, String phone, String address, String work) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.work = work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
