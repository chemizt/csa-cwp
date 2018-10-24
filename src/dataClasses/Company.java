package dataClasses;

import java.util.ArrayList;

public class Company
{
    private String name;
    private Address location;
    private ArrayList<Course> hostedCourses;
    private ArrayList<Tutor> employedTutors;
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
    public ArrayList<Course> getHostedCourses()
    {
        return hostedCourses;
    }
    public void setHostedCourses(ArrayList<Course> hostedCourses)
    {
        this.hostedCourses = hostedCourses;
    }
    public ArrayList<Tutor> getEmployedTutors()
    {
        return employedTutors;
    }
    public void setEmployedTutors(ArrayList<Tutor> employedTutors)
    {
        this.employedTutors = employedTutors;
    }
}
