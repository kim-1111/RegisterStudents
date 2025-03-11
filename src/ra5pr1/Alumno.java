/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ra5pr1;

/**
 *
 * @author emhua
 */
public class Alumno {
    private String name;
    private String lastName;
    private int age;
    private String course;
    private String DNI;

    public Alumno(String name, String lastName, int age, String course, String DNI) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.course = course;
        this.DNI = DNI;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    @Override
    public String toString() {
        return name + ";" + lastName + ";" + age + ";" + course + ";" + DNI;
    }
    
    
}
