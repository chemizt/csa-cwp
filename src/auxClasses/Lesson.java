package auxClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Lesson
{
    private Course hostedCourse;
    private Tutor hostingTutor;
    private LocalDateTime hostingDateTime;
    private HashMap<String, Student> enrolledStudents;
    private Schedule container;
    public Course getHostedCourse()
    {
        return hostedCourse;
    }
    public void setHostedCourse(Course hostedCourse)
    {
        this.hostedCourse = hostedCourse;
    }
    public Tutor getHostingTutor()
    {
        return hostingTutor;
    }
    public void setHostingTutor(Tutor hostingTutor)
    {
        this.hostingTutor = hostingTutor;
    }
    public LocalDateTime getHostingDateTime()
    {
        return hostingDateTime;
    }
    public void setHostingDateTime(LocalDateTime hostingDateTime)
    {
        this.hostingDateTime = hostingDateTime;
    }
    public HashMap<String, Student> getEnrolledStudents()
    {
        return enrolledStudents;
    }
    public void setEnrolledStudents(HashMap<String, Student> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
    }
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public void parseString(String strToParse)
    {
        hostingDateTime = LocalDateTime.parse(returnSubString(strToParse, "#"), DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
        strToParse = purgeString(strToParse, "#");
        hostedCourse = container.getCourses().get(returnSubString(strToParse, "#"));
        hostingTutor = hostedCourse.getHostingTutor();
        strToParse = purgeString(strToParse, "#");
    }
    public Lesson()
    {
        enrolledStudents = new HashMap<>();
    }
}
