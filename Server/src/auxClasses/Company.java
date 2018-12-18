package auxClasses;

import java.util.HashMap;
import java.util.Map;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Company
{
    private String name;
    private Address location;
    private HashMap<String, Course> hostedCourses;
    private HashMap<String, Tutor> employedTutors;
    private Schedule container;
    public Company()
    {
        hostedCourses = new HashMap<>();
        employedTutors = new HashMap<>();
    }
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Address getLocation()
    {
        return location;
    }
    public void setLocation(Address location)
    {
        this.location = location;
    }
    public HashMap<String, Course> getHostedCourses()
    {
        return hostedCourses;
    }
    public void setHostedCourses(HashMap<String, Course> hostedCourses)
    {
        this.hostedCourses = hostedCourses;
    }
    public HashMap<String, Tutor> getEmployedTutors()
    {
        return employedTutors;
    }
    public void setEmployedTutors(HashMap<String, Tutor> employedTutors)
    {
        this.employedTutors = employedTutors;
    }
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse, "#").equals("") ? name : returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        while (!returnSubString(strToParse, "*").equals(""))
        {
            if (container.getCourses().get(returnSubString(strToParse, "*")) == null)
            {
                Course newCourse = new Course();
                newCourse.setName(returnSubString(strToParse, "*"));
                container.getCourses().put(newCourse.getName(), newCourse);
                hostedCourses.put(newCourse.getName(), newCourse);
            }
            else
            {
                hostedCourses.put(returnSubString(strToParse, "*"), container.getCourses().get(returnSubString(strToParse, "*")));
            }
            strToParse = purgeString(strToParse, "*");
        }
    }
    public String returnFullInfo()
    {
        String result;
        result = "Название: " + name;
        result += (location == null ? "" : "\nРасположение: " + location.returnFull());
        result += hostedCourses.size() <= 0 ? "" : "\nПроводимые курсы:\n|";
        int i = 0;
        for (Map.Entry<String, Course> courseEntry : hostedCourses.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCourses.get(courseEntryKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "\n|";
        }
        result += employedTutors.size() <= 0 ? "" : "\nШтат преподавателей:\n|";
        i = 0;
        for (Map.Entry<String, Tutor> tutorEntry : employedTutors.entrySet())
        {
            String tutorEntryKey = tutorEntry.getKey();
            result += employedTutors.get(tutorEntryKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "\n|";
        }
        result += "\n";
        return result;
    }
    public String returnWritableAddress()
    {
        return name + "#" + (location == null ? "" : location.returnWritableFull());
    }
    public String returnWritableFull()
    {
        String result;
        result = name + "#";
        for (Map.Entry<String, Course> courseEntry : hostedCourses.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCourses.get(courseEntryKey).getName() + "*";
        }
        result += "#";
        return result;
    }
}
