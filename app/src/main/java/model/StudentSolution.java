package model;

/**
 * Created by Muhammad on 23-May-17.
 */

public class StudentSolution {
    private int stu_id;
    private int assignment_id;
    private byte[] file;

    public StudentSolution() {
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
