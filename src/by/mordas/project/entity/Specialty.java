package by.mordas.project.entity;

import java.util.ArrayList;
import java.util.List;

public class Specialty extends Entity {
    private int specialityId;
    private String specialityName;
    private int recruitmentPlan;
    private int facultyId;
    private ArrayList<Integer> subjectList=new ArrayList<>(3);

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public int getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public void setRecruitmentPlan(int recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
    }
}
