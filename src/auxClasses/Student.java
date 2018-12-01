package auxClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Student
{
    private String firstName, middleName, lastName;
    private HashMap<String, Course> attendedCoursesList;
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
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
    public HashMap<String, Course> getAttendedCoursesList()
    {
        return attendedCoursesList;
    }
    public void setAttendedCoursesList(HashMap<String, Course> attendedCoursesList)
    {
        this.attendedCoursesList = attendedCoursesList;
    }
}
