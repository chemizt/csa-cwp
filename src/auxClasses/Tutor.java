package auxClasses;

import java.util.HashMap;

public class Tutor
{
    private String firstName, middleName, lastName;
    private Company employingCompany;
    private HashMap<String, Course> hostedCoursesList;
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
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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
}
