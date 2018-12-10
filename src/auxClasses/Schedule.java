package auxClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Scanner;

import static java.nio.file.Files.newBufferedReader;

public class Schedule
{
    private HashMap<LocalDateTime, Lesson> schedule;
    private HashMap<String, Course> courses;
    private HashMap<String, Student> students;
    private HashMap<String, Tutor> tutors;
    private HashMap<String, Company> companies;
    private String exceptionCause;
    public Schedule()
    {
        schedule = new HashMap<>();
        courses = new HashMap<>();
        students = new HashMap<>();
        tutors = new HashMap<>();
        companies = new HashMap<>();
    }
    public HashMap<String, Course> getCourses()
    {
        return courses;
    }
    public HashMap<String, Student> getStudents()
    {
        return students;
    }
    public HashMap<String, Tutor> getTutors()
    {
        return tutors;
    }
    public HashMap<String, Company> getCompanies()
    {
        return companies;
    }
    public HashMap<LocalDateTime, Lesson> getSchedule()
    {
        return schedule;
    }
    public void setSchedule(HashMap<LocalDateTime, Lesson> schedule)
    {
        this.schedule = schedule;
    }
    public void parseFiles(String sourceFilesPath) throws IOException
    {
        Path inputFilePath;
        try
        {
            exceptionCause = "courses.txt";
            System.out.print("Загрузка информации о курсах... ");
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath,"courses.txt");
            BufferedReader inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            String inputString;
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Course newCourse = new Course();
                newCourse.parseString(inputString);
                courses.put(newCourse.getName(), newCourse);
            }
            inputFileBuffer.close();
            System.out.print("готово!\nЗагрузка информации о компаниях... ");
            exceptionCause = "companies.txt";
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath, "companies.txt");
            inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Company newCompany = new Company();
                newCompany.setContainer(this);
                newCompany.parseCompanyInfoString(inputString);
                companies.put(newCompany.getName(), newCompany);
            }
            inputFileBuffer.close();
            exceptionCause = "addresses.txt";
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath, "addresses.txt");
            inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Address newAddress = new Address();
                newAddress.setContainer(this);
                newAddress.parseString(inputString);
            }
            inputFileBuffer.close();
            System.out.print("готово!\nЗагрузка информации о преподавателях... ");
            exceptionCause = "tutors.txt";
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath, "tutors.txt");
            inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Tutor newTutor = new Tutor();
                newTutor.setContainer(this);
                newTutor.parseString(inputString);
                tutors.put(newTutor.getName(), newTutor);
                companies.get(newTutor.getEmployingCompany().getName()).getEmployedTutors().put(newTutor.getName(), newTutor);
            }
            inputFileBuffer.close();
            System.out.print("готово!\nЗагрузка информации о студентах... ");
            exceptionCause = "students.txt";
            inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath, "students.txt");
            inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
            while ((inputString = inputFileBuffer.readLine()) != null)
            {
                Student newStudent = new Student();
                newStudent.setContainer(this);
                newStudent.parseString(inputString);
                students.put(newStudent.getName(), newStudent);
            }
            inputFileBuffer.close();
            System.out.print("готово!\nВведите 1, если файл schedule.txt содержит составленное расписание, иначе - любую другую последовательность символов: ");
            Scanner input = new Scanner(System.in);
            String decision = input.nextLine();
            switch (decision)
            {
                case "1":
                {
                    System.out.print("Загрузка информации о расписании... ");
                    exceptionCause = "schedule.txt";
                    inputFilePath = FileSystems.getDefault().getPath(sourceFilesPath, "schedule.txt");
                    inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);
                    while ((inputString = inputFileBuffer.readLine()) != null)
                    {
                        Lesson newLesson = new Lesson();
                        newLesson.setContainer(this);
                        newLesson.parseString(inputString);
                        schedule.put(newLesson.getHostingDateTime(), newLesson);
                    }
                    inputFileBuffer.close();
                    System.out.print("готово!\n");
                    break;
                }
                default:
                {
                    System.out.print("Загрузка расписания из файла пропущена.\n");
                    break;
                }
            }
            System.out.print("Актуализация информации... ");
            System.out.print("готово!\n\n");
        }
        catch (IOException e)
        {
            throw new IOException("Произошла ошибка при открытии файла " + exceptionCause + ". Он не существует, имеет кодировку, отличную от UTF-8, или находится в другой директории. Попробуйте ещё раз.");
        }
    }
    public HashMap<LocalDateTime, Lesson> getScheduleFor(Object person, String personName, Period timeFrame, LocalDateTime startDate)
    {
        HashMap<LocalDateTime, Lesson> result = new HashMap<>();
        if (person.getClass().getName().contains("Student"))
        {
            startDate.plus(timeFrame);
        }
        else
        if (person.getClass().getName().contains("Tutor"))
        {

        }
        return result;
    }
}
