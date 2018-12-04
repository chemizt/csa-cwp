package server;

import auxClasses.Course;
import auxClasses.Schedule;
import auxClasses.Student;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Server
{
    private Schedule coursesSchedule;
    private void init()
    {
        coursesSchedule = new Schedule();
        boolean filesParsedSuccessfully = false;
        do
        {
            System.out.print("Введите путь к папке с исходными файлами (нажмите Enter, если они находятся в одной папке с исполняемым файлом сервера): ");
            Scanner input = new Scanner(System.in);
            try
            {
                coursesSchedule.parseFiles(input.nextLine());
                filesParsedSuccessfully = true;
            }
            catch (IOException fileInputException)
            {
                System.out.println(fileInputException.getMessage());
            }
        }
        while (!filesParsedSuccessfully);
        System.out.println("Загрузка информации завершена, сервер готов к работе.\n");
    }
    public Server()
    {
        init();
    }
    public void listen()
    {
        String decision;
        do
        {
            System.out.println("Выберите желаемую операцию. Для выхода введите 'X-IT'.\n");
            printOperationsList();
            System.out.print("\nВаш выбор: ");
            Scanner input = new Scanner(System.in);
            decision = input.nextLine();
            switch (decision.toUpperCase(Locale.getDefault()))
            {
                case "1":
                {
                    System.out.println("Получение запрошенной информации...\n");
                    for(Map.Entry<String, Course> courseEntry : coursesSchedule.getCourses().entrySet())
                    {
                        String courseEntryKey = courseEntry.getKey();
                        System.out.println("Название курса: " + coursesSchedule.getCourses().get(courseEntryKey).getName());
                        System.out.print("Список студентов:\n|");
                        int i = 0;
                        for(Map.Entry<String, Student> studentEntry : coursesSchedule.getCourses().get(courseEntryKey).getEnrolledStudents().entrySet())
                        {
                            String studentKey = studentEntry.getKey();
                            System.out.print(coursesSchedule.getStudents().get(studentKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) System.out.println("|");
                        }
                        System.out.println("\n");
                    }
                    break;
                }
                case "2":
                {
                    boolean inputSuccessful = false;
                    String studName;
                    do
                    {
                        System.out.print("\nВведите имя студента в формате 'Фамилия И. О.': ");
                        Scanner nameInput = new Scanner(System.in);
                        studName = input.nextLine();
                        if (coursesSchedule.getStudents().get(studName) != null)
                        {
                            inputSuccessful = true;
                            System.out.print("Студент " + studName + " найден! Список курсов, на которые он записан:\n|");
                            int i = 0;
                            for(Map.Entry<String, Course> courseEntry : coursesSchedule.getStudents().get(studName).getAttendedCoursesList().entrySet())
                            {
                                String courseEntryKey = courseEntry.getKey();
                                System.out.print(coursesSchedule.getCourses().get(courseEntryKey).getName() + "|");
                                i++;
                                if (i % 4 == 0) System.out.print("\n|");
                            }
                            System.out.println();
                        }
                        else
                        {
                            System.out.println("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            studName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !studName.toUpperCase(Locale.getDefault()).equals("N"));
                    System.out.println();
                    break;
                }
                case "3":
                {
                    break;
                }
                case "4":
                {
                    break;
                }
                case "5":
                {
                    break;
                }
                case "6":
                {
                    break;
                }
                case "7":
                {
                    break;
                }
                case "8":
                {
                    break;
                }
                case "X-IT":
                {
                    System.out.println("Завершение работы сервера...");
                    break;
                }
                default:
                {
                    System.out.println("Выбрана неопределённая операция.");
                    break;
                }
            }
        }
        while (!decision.toUpperCase(Locale.getDefault()).equals("X-IT"));
    }
    private void printOperationsList()
    {
        System.out.println("1. Получить списки студентов по курсам.");
        System.out.println("2. Получить список курсов для конкретного студента.");
        System.out.println("3. Получить списки преподавателей по курсам.");
        System.out.println("4. Получить списки преподавателей по компаниям.");
        System.out.println("5. Получить данные о конкретном студенте.");
        System.out.println("6. Получить данные о конкретном преподавателе.");
        System.out.println("7. Получить данные о конкретном курсе.");
        System.out.println("8. Получить данные о конкретной компании.");
    }
}

