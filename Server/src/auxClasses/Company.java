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
    public void parseCompanyInfoString(String strToParse)
    {
        name = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        while (!returnSubString(strToParse, "#").equals(""))
        {
            hostedCourses.put(returnSubString(strToParse, "*"), container.getCourses().get(returnSubString(strToParse, "*")));
            strToParse = purgeString(strToParse, "*");
        }
    }
    public String returnFullInfo()
    {
        String result;
        result = "Название: " + name;
        result += "\nРасположение: " + location.returnFull();
        result += "\nПроводимые курсы:\n|";
        int i = 0;
        for (Map.Entry<String, Course> courseEntry : hostedCourses.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCourses.get(courseEntryKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "\n|";
        }
        result += "\nШтат преподавателей:\n|";
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
}
