package server;

import auxClasses.Company;
import auxClasses.Course;
import auxClasses.Schedule;
import auxClasses.Tutor;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Server
{
    private Schedule coursesSchedule;
    public Server()
    {
        init();
    }
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
        System.out.println("Загрузка информации завершена, сервер готов к работе.");
    }
    public void listen()
    {
        String decision;
        do
        {
            System.out.println("\nВведите номер желаемой операции. Для выхода введите 'X-IT'.\n");
            printOperationsList();
            System.out.print("\nВаш выбор: ");
            Scanner input = new Scanner(System.in);
            decision = input.nextLine();
            switch (decision.toUpperCase(Locale.getDefault()))
            {
                case "1":
                {
                    waitForEnter();
                    break;
                }
                case "2":
                {
                    waitForEnter();
                    break;
                }
                case "3":
                {
                    waitForEnter();
                    break;
                }
                case "4":
                {
                    waitForEnter();
                    break;
                }
                case "5":
                {
                    System.out.println("\nПолучение запрошенной информации...\n");
                    for(Map.Entry<String, Course> courseEntry : coursesSchedule.getCourses().entrySet())
                    {
                        String courseEntryKey = courseEntry.getKey();
                        System.out.println("Название курса: " + coursesSchedule.getCourses().get(courseEntryKey).getName());
                        coursesSchedule.getCourses().get(courseEntryKey).printEnrolledStudents();
                    }
                    waitForEnter();
                    break;
                }
                case "6":
                {
                    boolean inputSuccessful = false;
                    String studName;
                    do
                    {
                        System.out.print("\nВведите имя студента в формате 'Фамилия И. О.': ");
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
                        } else
                        {
                            System.out.println("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            studName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !studName.toUpperCase(Locale.getDefault()).equals("N"));
                    System.out.println();
                    waitForEnter();
                    break;
                }
                case "7":
                {
                    System.out.println("\nПолучение запрошенной информации...\n");
                    for (Map.Entry<String, Course> courseEntry : coursesSchedule.getCourses().entrySet())
                    {
                        String courseEntryKey = courseEntry.getKey();
                        System.out.println("Название курса: " + coursesSchedule.getCourses().get(courseEntryKey).getName());
                        System.out.println("Преподаватель: " + coursesSchedule.getCourses().get(courseEntryKey).getHostingTutor().getName() + "\n");
                    }
                    waitForEnter();
                    break;
                }
                case "8":
                {
                    System.out.println("\nПолучение запрошенной информации...\n");
                    for (Map.Entry<String, Company> companyEntry : coursesSchedule.getCompanies().entrySet())
                    {
                        String companyEntryKey = companyEntry.getKey();
                        System.out.println("Название компании: " + coursesSchedule.getCompanies().get(companyEntryKey).getName());
                        System.out.print("Список преподавателей:\n|");
                        int i = 0;
                        for (Map.Entry<String, Tutor> tutorEntry : coursesSchedule.getCompanies().get(companyEntryKey).getEmployedTutors().entrySet())
                        {
                            String tutorEntryKey = tutorEntry.getKey();
                            System.out.print(coursesSchedule.getTutors().get(tutorEntryKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) System.out.println("|");
                        }
                        System.out.println("\n");
                    }
                    waitForEnter();
                    break;
                }
                case "9":
                {
                    boolean inputSuccessful = false;
                    String tutorName;
                    do
                    {
                        System.out.print("\nВведите имя преподавателя в формате 'Фамилия И. О.': ");
                        tutorName = input.nextLine();
                        if (coursesSchedule.getTutors().get(tutorName) != null)
                        {
                            inputSuccessful = true;
                            System.out.println("Преподаватель " + tutorName + " найден! Вот полная информация о нём:");
                            coursesSchedule.getTutors().get(tutorName).printFullInfo();
                        } else
                        {
                            System.out.println("Преподавателя с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            tutorName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !tutorName.toUpperCase(Locale.getDefault()).equals("N"));
                    waitForEnter();
                    break;
                }
                case "10":
                {
                    boolean inputSuccessful = false;
                    String studName;
                    do
                    {
                        System.out.print("\nВведите имя студента в формате 'Фамилия И. О.': ");
                        studName = input.nextLine();
                        if (coursesSchedule.getStudents().get(studName) != null)
                        {
                            inputSuccessful = true;
                            System.out.println("Студент " + studName + " найден! Вот полная информация о нём:");
                            coursesSchedule.getStudents().get(studName).printFullInfo();
                        } else
                        {
                            System.out.println("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            studName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !studName.toUpperCase(Locale.getDefault()).equals("N"));
                    waitForEnter();
                    break;
                }
                case "11":
                {
                    boolean inputSuccessful = false;
                    String courseName;
                    do
                    {
                        System.out.print("\nВведите название курса: ");
                        courseName = input.nextLine();
                        if (coursesSchedule.getCourses().get(courseName) != null)
                        {
                            inputSuccessful = true;
                            System.out.print("Курс '" + courseName + "' найден! Вот полная информация о нём:\n");
                            coursesSchedule.getCourses().get(courseName).printFullInfo();
                        } else
                        {
                            System.out.println("Курса с таким названием не существует. Чтобы уточнить название, Вы можете вывести полный список курсов.");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            courseName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !courseName.toUpperCase(Locale.getDefault()).equals("N"));
                    waitForEnter();
                    break;
                }
                case "12":
                {
                    boolean inputSuccessful = false;
                    String companyName;
                    do
                    {
                        System.out.print("\nВведите название компании: ");
                        companyName = input.nextLine();
                        if (coursesSchedule.getCompanies().get(companyName) != null)
                        {
                            inputSuccessful = true;
                            System.out.print("Компания " + companyName + " найдена! Вот полная информация о ней:\n");
                            coursesSchedule.getCompanies().get(companyName).printFullInfo();
                        } else
                        {
                            System.out.println("Компании с таким названием не существует. Чтобы уточнить название, Вы можете вывести полный список компаний.");
                            System.out.print("Попробовать ещё раз? (Y/N): ");
                            companyName = input.nextLine();
                        }
                    }
                    while (!inputSuccessful && !companyName.toUpperCase(Locale.getDefault()).equals("N"));
                    System.out.println();
                    waitForEnter();
                    break;
                }
                case "13":
                {
                    waitForEnter();
                    break;
                }
                case "14":
                {
                    waitForEnter();
                    break;
                }
                case "15":
                {
                    waitForEnter();
                    break;
                }
                case "16":
                {
                    waitForEnter();
                    break;
                }
                case "17":
                {
                    waitForEnter();
                    break;
                }
                case "18":
                {
                    waitForEnter();
                    break;
                }
                case "19":
                {
                    waitForEnter();
                    break;
                }
                case "20":
                {
                    waitForEnter();
                    break;
                }
                case "X-IT":
                {
                    System.out.println("Завершение работы сервера...");
                    break;
                }
                default:
                {
                    System.out.println("Введённое значение не определено.");
                    break;
                }
            }
        }
        while (!decision.toUpperCase(Locale.getDefault()).equals("X-IT"));
    }
    private void printOperationsList()
    {
        System.out.println("1. Получить полный список преподавателей.");
        System.out.println("2. Получить полный список студентов.");
        System.out.println("3. Получить полный список курсов.");
        System.out.println("4. Получить полный список компаний.");
        System.out.println("5. Получить списки студентов по курсам.");
        System.out.println("6. Получить список курсов для конкретного студента.");
        System.out.println("7. Получить списки преподавателей по курсам.");
        System.out.println("8. Получить списки преподавателей по компаниям.");
        System.out.println("9. Получить данные о конкретном преподавателе.");
        System.out.println("10. Получить данные о конкретном студенте.");
        System.out.println("11. Получить данные о конкретном курсе.");
        System.out.println("12. Получить данные о конкретной компании.");
        System.out.println("13. Получить расписание для участника учебного процесса на заданный интервал времени.");
        System.out.println("14. Изменить данные о преподавателе.");
        System.out.println("15. Изменить данные о студенте.");
        System.out.println("16. Изменить данные о курсе.");
        System.out.println("17. Добавить или удалить преподавателя.");
        System.out.println("18. Добавить или удалить студента.");
        System.out.println("19. Добавить или удалить курс.");
        System.out.println("20. Составить расписание занятий для курса.");
    }
    private void waitForEnter()
    {
        System.out.print("Для продолжения нажмите Enter");
        Scanner enterInput = new Scanner(System.in);
        enterInput.nextLine();
    }
}

