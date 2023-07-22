package dto;

import java.util.Objects;

public class Person {
    private String name;
    private int age;

    // Default constructor
    public Person() {
        // You can choose to initialize the fields with default values here
        // The error message java.lang.NoSuchMethodException: dto.Person.<init>()
        // typically occurs when you are trying to create a new instance of the
        // Person class using reflection, and the default constructor (no-argument constructor)
        // is not available or accessible.
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // this is required to compare the Object from DB to some expected Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

}