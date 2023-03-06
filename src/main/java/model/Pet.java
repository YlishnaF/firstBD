package model;

import java.util.Date;

public class Pet {
    String pet_name;
    Date pet_birthday;
    String type;

    public Pet(String pet_name, Date pet_birthday, String type) {
        this.pet_name = pet_name;
        this.pet_birthday = pet_birthday;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "pet_name='" + pet_name + '\'' +
                ", pet_birthday=" + pet_birthday +
                ", type='" + type + '\'' +
                '}';
    }
}
