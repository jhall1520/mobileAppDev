package com.company;

import java.util.Objects;

public class User {
    String firstname;
    String lastname;
    String age;
    String email;
    String gender;
    String city;
    String state;

    public User(String data) {
        String[] info = data.split(",");
        if (info != null && info.length == 7) {
            firstname = info[0];
            lastname = info[1];
            age = info[2];
            email = info[3];
            gender = info[4];
            city = info[5];
            state = info[6];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(age, user.age) && Objects.equals(email, user.email) && Objects.equals(gender, user.gender) && Objects.equals(city, user.city) && Objects.equals(state, user.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, age, email, gender, city, state);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
