package auxClasses;

import java.util.HashMap;
import java.util.Map;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Tutor
{
    private String name;
    private Company employingCompany;
    private HashMap<String, Course> hostedCourses;
    private Schedule container;
    public Tutor()
    {
        hostedCourses = new HashMap<>();
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
    public HashMap<String, Course> getHostedCourses()
    {
        return hostedCourses;
    }
    public void setHostedCourses(HashMap<String, Course> hostedCourses)
    {
        this.hostedCourses = hostedCourses;
    }
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse, "#").equals("") ? name : returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        if (!returnSubString(strToParse, "#").equals(""))
        {
            if (container.getCompanies().get(returnSubString(strToParse, "#")) == null)
            {
                Company newCompany = new Company();
                newCompany.setName(returnSubString(strToParse, "#"));
                newCompany.getEmployedTutors().put(name, this);
                newCompany.setContainer(container);
                if (employingCompany != null) employingCompany.getEmployedTutors().remove(name);
                employingCompany = newCompany;
                container.getCompanies().put(newCompany.getName(), newCompany);
            }
            else
            {
                if (employingCompany != null) employingCompany.getEmployedTutors().remove(name);
                employingCompany = container.getCompanies().get(returnSubString(strToParse, "#"));
                employingCompany.getEmployedTutors().put(name, this);
            }
        }
        strToParse = purgeString(strToParse, "#");
        while (!returnSubString(strToParse, "*").equals(""))
        {
            if (container.getCourses().get(returnSubString(strToParse, "*")) == null)
            {
                Course newCourse = new Course();
                newCourse.setName(returnSubString(strToParse, "*"));
                newCourse.setHostingTutor(this);
                hostedCourses.put(newCourse.getName(), newCourse);
                container.getCourses().put(newCourse.getName(), newCourse);
            }
            else
            {
                hostedCourses.put(returnSubString(strToParse, "*"), container.getCourses().get(returnSubString(strToParse, "*")));
                container.getCourses().get(returnSubString(strToParse, "*")).setHostingTutor(this);
            }
            strToParse = purgeString(strToParse, "*");
        }
    }
    public String returnFullInfo()
    {
        String result;
        result = "Имя: " + name;
        result += (employingCompany == null ? "" : "\nРаботодатель: " + employingCompany.getName());
        result += (hostedCourses.size() <= 0 ? "" : "\nЧитаемые курсы:\n|");
        int i = 0;
        for (Map.Entry<String, Course> courseEntry : hostedCourses.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCourses.get(courseEntryKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "\n|";
        }
        result += "\n";
        return result;
    }
    public String returnWritableFull()
    {
        String result;
        result = name + "#" + (employingCompany == null ? "" : employingCompany.getName()) + "#";
        for (Map.Entry<String, Course> courseEntry : hostedCourses.entrySet())
        {
            String courseEntryKey = courseEntry.getKey();
            result += hostedCourses.get(courseEntryKey).getName() + "*";
        }
        result += "#";
        return result;
    }
}
