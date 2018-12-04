package auxClasses;

import java.time.Period;
import java.util.HashMap;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Course
{
    private String name;
    private Period duration;
    private int intensity;
    private Tutor hostingTutor;
    private HashMap<String, Student> enrolledStudents;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Period getDuration()
    {
        return duration;
    }
    public void setDuration(Period duration)
    {
        this.duration = duration;
    }
    public int getIntensity()
    {
        return intensity;
    }
    public void setIntensity(int intensity)
    {
        this.intensity = intensity;
    }
    public Tutor getHostingTutor()
    {
        return hostingTutor;
    }
    public void setHostingTutor(Tutor hostingTutor)
    {
        this.hostingTutor = hostingTutor;
    }
    public HashMap<String, Student> getEnrolledStudents()
    {
        return enrolledStudents;
    }
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        duration = Period.parse(returnSubString(strToParse, "#"));
        strToParse = purgeString(strToParse, "#");
        intensity = Integer.parseInt(returnSubString(strToParse, "#"));
    }
    public Course()
    {
        enrolledStudents = new HashMap<>();
    }
}
