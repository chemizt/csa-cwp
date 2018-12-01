package auxClasses;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class Lesson
{
    private Course hostedCourse;
    private Tutor hostingTutor;
    private ZonedDateTime hostingDateTime;
    private HashMap<String, Student> enrolledStudents;
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
    public ZonedDateTime getHostingDateTime()
    {
        return hostingDateTime;
    }
    public void setHostingDateTime(ZonedDateTime hostingDateTime)
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
}
