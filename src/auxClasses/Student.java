package auxClasses;

import java.util.HashMap;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Student
{
    private String name;
    private HashMap<String, Course> attendedCoursesList;
    private Schedule container;
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
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        while (!returnSubString(strToParse, "#").equals(""))
        {
           attendedCoursesList.put(returnSubString(strToParse, "*"), container.getCourses().get(returnSubString(strToParse, "*")));
           container.getCourses().get(returnSubString(strToParse, "*")).getEnrolledStudents().put(name, this);
           strToParse = purgeString(strToParse, "*");
        }
    }
    public Student()
    {
        attendedCoursesList = new HashMap<>();
    }
}
