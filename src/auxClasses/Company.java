package auxClasses;

import java.util.HashMap;

public class Company
{
    private String name;
    private Address location;
    private HashMap<String, Course> hostedCourses;
    private HashMap<String, Tutor> employedTutors;
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

    }
    public void parseAddressString(String strToParse)
    {
        location = new Address();

    }
}
