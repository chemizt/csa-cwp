package auxClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

import static java.nio.file.Files.newBufferedReader;

public class Schedule
{
    private HashMap<String, Lesson> schedule;
    private HashMap<String, Course> courses;
    private HashMap<String, Student> students;
    private HashMap<String, Tutor> tutors;
    private HashMap<String, Company> companies;
    public HashMap<String, Lesson> getSchedule()
    {
        return schedule;
    }
    public void setSchedule(HashMap<String, Lesson> schedule)
    {
        this.schedule = schedule;
    }
    public void parseFiles(String sourceFilesPath) throws IOException
    {
        Path inputFilePath;
        try
        {
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath,"courses.txt");
            BufferedReader inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            String inputString = "";
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Course newCourse = new Course();
                newCourse.parseString(inputString);
                courses.put(newCourse.getName(), newCourse);
            }
        }
        catch (IOException buffReaderException)
        {
            throw buffReaderException;
        }
    }
    public Schedule()
    {
        schedule = new HashMap<>();
        courses = new HashMap<>();
        students = new HashMap<>();
        tutors = new HashMap<>();
        companies = new HashMap<>();
    }
}
