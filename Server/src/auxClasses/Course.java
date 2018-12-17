package auxClasses;

import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Course
{
    private String name;
    private Period duration;
    private int intensity;
    private Tutor hostingTutor;
    private HashMap<String, Student> enrolledStudents;
    public Course()
    {
        enrolledStudents = new HashMap<>();
    }
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
    public String returnFullInfo()
    {
        String result;
        result = "Название курса: " + name;
        result += "\nИнтенсивность: " + intensity + " ч. в день";
        int quantityOfWeeks = (duration.getYears() * 365 + duration.getMonths() * 30 + duration.getDays()) / 7;
        result += "\nПродолжительность: ";
        if (quantityOfWeeks > 0)
        {
            if (quantityOfWeeks % 10 == 1 && quantityOfWeeks / 10 != 1)
            {
                result += quantityOfWeeks + " неделя\n";
            }
            else if (quantityOfWeeks % 10 > 1 && quantityOfWeeks % 10 < 5 && quantityOfWeeks / 10 != 1)
            {
                result += quantityOfWeeks + " недели\n";
            }
            else
            {
                result += quantityOfWeeks + " недель\n";
            }
        }
        result += returnEnrolledStudents();
        return result;
    }
    public String returnEnrolledStudents()
    {
        String result;
        result = "Слушатели:\n|";
        int i = 0;
        for (Map.Entry<String, Student> studentEntry : enrolledStudents.entrySet())
        {
            String studentKey = studentEntry.getKey();
            result += enrolledStudents.get(studentKey).getName() + "|";
            i++;
            if (i % 4 == 0) result += "|\n";
        }
        result +=("\n");
        return result;
    }
    public String returnWritableFull()
    {
        return name + "#" + duration.toString() + "#" + intensity + "#";
    }
}
