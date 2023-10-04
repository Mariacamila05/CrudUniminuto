package com.example.cruduniminuto;

public class Entity {

    private int idUser;
    private String document;
    private String name;
    private String lastName;
    private String user;
    private String password;

    public Entity(int idUser, String document, String user, String name, String lastName, String password) {
        this.idUser = idUser;
        this.document = document;
        this.name = name;
        this.lastName = lastName;
        this.user = user;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
