/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ra5pr1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Student registration management system providing CRUD operations and file
 * persistence. Supports the following features:
 * <ul>
 * <li>Register new students</li>
 * <li>Display student list</li>
 * <li>Delete student records</li>
 * <li>Search students by DNI</li>
 * <li>Data persistence to text file</li>
 * </ul>
 *
 * @author Yixin Huang
 * @version 1.0
 */
public class RegistroAlumnos {

    // ArrayList storing student objects
    ArrayList<Alumno> students;

    //Current working directory path
    String ruta = System.getProperty("user.dir");

    //System file separator character
    String separador = File.separator;

    //Data storage file object
    File newFile = new File(ruta + separador + "registro.txt");

    /**
     * Constructs a student registry and initializes data storage
     */
    public RegistroAlumnos() {
        students = new ArrayList<>();
    }

    /**
     * Main entry point for the student management system
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RegistroAlumnos register = new RegistroAlumnos();
        register.studentsFile();
        Scanner sc = new Scanner(System.in);
        int option;
        do {
            option = register.menu();
            switch (option) {
                case 1:
                    register.registerStudent();
                    break;
                case 2:
                    register.showList();
                    break;
                case 3:
                    register.deleteStudent();
                    break;
                case 4:
                    register.searchStudent();
                    break;
                case 5:
                    System.out.println("Leaving....");
                    break;
            }
        } while (option != 5);
    }

    /**
     * Registers a new student with validation checks Collects student
     * information including:
     * <ul>
     * <li>Name and surname</li>
     * <li>Age (numeric validation)</li>
     * <li>Course</li>
     * <li>Unique DNI</li>
     * </ul>
     * Automatically persists data to file after registration
     */
    public void registerStudent() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Name of student: ");
        String name = sc.nextLine();
        System.out.print("Last name: ");
        String lastName = sc.nextLine();
        System.out.print("Age: ");
        int age;
        try {
            age = sc.nextInt();
        } catch (Exception e) {
            System.err.println("Invalid age");
            return;
        }
        System.out.print("Course: ");
        String course = sc.next();
        System.out.print("DNI: ");
        String DNI = sc.next();
        System.out.println("~~~~~~~~");

        if (existStudent(DNI)) {
            System.out.println("Exist student with DNI: " + DNI);
            return;
        }

        Alumno student = new Alumno(name, lastName, age, course, DNI);
        students.add(student);

        add(students);
        System.out.println("Student registered");

    }

    /**
     * Displays all registered students from file <br>
     * Reads directly from file to ensure data consistency
     */
    public void showList() {

        System.out.println("List of students: ");
        try {
            FileReader fr = new FileReader(newFile);
            BufferedReader br = new BufferedReader(fr);
            String list = "";
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                list += "\nName: " + data[0] + "  Surname: " + data[1] + "\nAge: " + data[2] + "\nCourse: " + data[3] + "\nDNI: " + data[4] + "\n";
                line = br.readLine();
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    /**
     * Deletes a student record by DNI <br>
     * Updates file storage after deletion
     */
    public void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        showList();
        System.out.print("Enter the DNI of the student: ");
        String DNI = sc.nextLine();
        System.out.println("~~~~~~~~");
        if (!existStudent(DNI)) {
            System.out.println("Invalid DNI");
        } else {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getDNI().equalsIgnoreCase(DNI)) {
                    students.remove(i);

                    System.out.println("Student deleted");
                }
            }
        }
        add(students);

    }

    /**
     * Searches for a student by DNI <br>
     * Displays full student record if found
     */
    public void searchStudent() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the DNI of the student: ");
        String DNI = sc.nextLine();
        boolean found = false;

        try {

            FileReader fr = new FileReader(newFile);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                if (data.length == 5 && data[4].equalsIgnoreCase(DNI)) {
                    System.out.println("Student Found: " + line);
                    found = true;
                    break;
                }
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error");
        }

        if (!found) {
            System.out.println("No student was found with the DNI: " + DNI);
        }
    }

    /**
     * Initializes student data from file storage
     */
    public void studentsFile() {

        Alumno student;
        try {
            FileReader fr = new FileReader(newFile);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                line = br.readLine();
                if (data.length == 5) {
                    student = new Alumno(data[0], data[1], Integer.valueOf(data[2]), data[3], data[4]);
                    students.add(student);
                }
            }
            br.close();

            add(students);
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    /**
     * Displays interactive menu and handles user input
     *
     * @return Validated user selection (1-5)
     */
    public int menu() {

        int option = -1;
        boolean validOption = false;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("");
            System.out.println("===============");
            System.out.println("Main menu");
            System.out.println("===============");
            System.out.println("1) Register new student");
            System.out.println("2) Show list of students");
            System.out.println("3) Delete student");
            System.out.println("4) Search student");
            System.out.println("5) Exit");
            System.out.print("[Option]: ");
            try {
                option = sc.nextInt();
                if (option <= 0 || option >= 6) {
                    System.out.println("Invalid option");
                    validOption = false;
                } else {
                    validOption = true;
                }
            } catch (Exception e) {
                System.err.println("Please enter numeric value");
            }
            System.out.println("---------------");
        } while (!validOption);

        return option;
    }

    /**
     * Checks for existing student by DNI
     *
     * @param dni to verify
     * @return true if student exists, false otherwise
     */
    public boolean existStudent(String dni) {
        boolean exist = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getDNI().equalsIgnoreCase(dni)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    /**
     * Writes student data to file
     *
     * @param students ArrayList of student objects to persist
     */
    public void add(ArrayList<Alumno> students) {

        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            FileWriter fw = new FileWriter(newFile);
            BufferedWriter writer = new BufferedWriter(fw);

            for (int i = 0; i < students.size(); i++) {
                writer.write(students.get(i).toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

}
