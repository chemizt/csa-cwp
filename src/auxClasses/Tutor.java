package auxClasses;

import java.util.HashMap;
import java.util.Map;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Tutor
{
    private String name;
    private Company employingCompany;
    private HashMap<String, Course> hostedCoursesList;
    private Schedule container;
    public Tutor()
    {
        hostedCoursesList = new HashMap<>();
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Company getEmployingCompany()
    {
        return employingCompany;
    }
    public void setEmployingCompany(Company employingCompany)
    {
        this.employingCompany = employingCompany;
    }
    public HashMap<String, Course> getHostedCoursesList()
    {
        return hostedCoursesList;
    }
    public void setHostedCoursesList(HashMap<String, Course> hostedCoursesList)
    {
        this.hostedCoursesList = hostedCoursesList;
    }
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        employingCompany = container.getCompanies().get(returnSubString(strToParse, "#"));
        strToParse = purgeString(strToParse, "#");
        while (!returnSubString(strToParse, "#").equals(""))
        {
            hostedCoursesList.put(returnSubString(strToParse, "*"), container.getCourses().get(returnSubString(strToParse, "*")));
            container.getCourses().get(returnSubString(strToParse, "*")).setHostingTutor(this);
            strToParse = purgeString(strToParse, "*");
        }
    }
    public String returnFullInfo()
    {
        String result;
        result = "Имя: " + name;
        result += "\nРаботодатель: " + employingCompany.getName();
        result += "\nЧитаемые курсы:\n|";
        int i = 0;
        for (Map.Entry<String, Course> courseEntry : hostedCoursesList.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCoursesList.get(courseEntryKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "\n|";
        }
        result += "\n";
        return result;
    }
}
