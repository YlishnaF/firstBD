package model;

import java.util.ArrayList;

public class Root {

    ArrayList<People> people;


    public ArrayList<People> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<People> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Root{" +

                ", people=" + people +
                '}';
    }
}
