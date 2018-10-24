package dataClasses;

import java.util.ArrayList;

public class Student
{
    private String firstName, middleName, lastName;
    private ArrayList<Course> attendedCoursesList;
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getMiddleName()
    {
        return middleName;
    }
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public ArrayList<Course> getAttendedCoursesList()
    {
        return attendedCoursesList;
    }
    public void setAttendedCoursesList(ArrayList<Course> attendedCoursesList)
    {
        this.attendedCoursesList = attendedCoursesList;
    }
}
