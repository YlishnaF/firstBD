package model;

import java.util.ArrayList;
import java.util.Date;

public class People {
    String name;
    Date birthday;
    String location;
    ArrayList<Pet> pet = new ArrayList<>();

    public ArrayList<Pet> getPet() {
        return pet;
    }

    public void setPet(ArrayList<Pet> pet) {
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", location='" + location + '\'' +
                ", pet=" + pet +
                '}';
    }
}
