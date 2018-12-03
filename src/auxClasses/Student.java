package auxClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Student
{
    private String name;
    private HashMap<String, Course> attendedCoursesList;
    public HashMap<String, Course> getAttendedCoursesList()
    {
        return attendedCoursesList;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setAttendedCoursesList(HashMap<String, Course> attendedCoursesList)
    {
        this.attendedCoursesList = attendedCoursesList;
    }
}
