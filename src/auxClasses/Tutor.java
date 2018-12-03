package auxClasses;

import java.util.HashMap;

import static auxClasses.ParserAuxUtils.*;

public class Tutor
{
    private String name;
    private Company employingCompany;
    private HashMap<String, Course> hostedCoursesList;
    private Schedule container;
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
    public Tutor()
    {
        hostedCoursesList = new HashMap<>();
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
}
