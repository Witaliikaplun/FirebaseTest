package com.example.firebasetest;

public class Model {
   private String name;
   private String secondName;
   private String description;

    public Model(String name, String secondName, String description) {
        this.setName(name);
        this.setSecondName(secondName);
        this.setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
