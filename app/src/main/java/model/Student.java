package model;

/**
 * Created by Muhammad on 11-May-17.
 */

public class Student {

    private int id;
    private String name;
    private String password;
    private String email;

    public Student() {
    }

    public Student(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
