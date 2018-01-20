package by.mordas.project.entity;

/**
 * Created by Cooler Master on 28.11.2017.
 */
public class Faculty extends Entity {
    private int facultyId;
    private String facultyName;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
