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
    public void printFullInfo()
    {
        System.out.println("Название курса: " + name);
        System.out.println("Интенсивность: " + intensity + " ч. в день");
        int quantityOfWeeks = (duration.getYears() * 365 + duration.getMonths() * 30 + duration.getDays()) / 7;
        System.out.print("Продолжительность: ");
        if (quantityOfWeeks > 0)
        {
            if (quantityOfWeeks % 10 == 1 && quantityOfWeeks / 10 != 1)
            {
                System.out.println(quantityOfWeeks + " неделя");
            } else if (quantityOfWeeks % 10 > 1 && quantityOfWeeks % 10 < 5 && quantityOfWeeks / 10 != 1)
            {
                System.out.println(quantityOfWeeks + " недели");
            } else
            {
                System.out.println(quantityOfWeeks + " недель");
            }
        }

        printEnrolledStudents();
    }
    public void printEnrolledStudents()
    {
        System.out.print("Слушатели:\n|");
        int i = 0;
        for (Map.Entry<String, Student> studentEntry : enrolledStudents.entrySet())
        {
            String studentKey = studentEntry.getKey();
            System.out.print(enrolledStudents.get(studentKey).getName() + "|");
            i++;
            if (i % 4 == 0) System.out.println("|");
        }
        System.out.println("\n");
    }
}
