package server;

import auxClasses.*;

import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Server
{
    public Schedule coursesSchedule;
    private ServerSocket srvSocket;
    public Server(int srvPort)
    {
        coursesSchedule = new Schedule();
        boolean filesParsedSuccessfully = false; boolean socketCreated = false;
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("Введите путь к папке с исходными файлами (нажмите Enter, если они находятся в одной папке с исполняемым файлом сервера): ");

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
        System.out.println("Загрузка информации завершена");
        try
        {
            srvSocket = new ServerSocket(srvPort, 10, Inet4Address.getByName(Inet4Address.getLocalHost().getHostAddress()));
            socketCreated = true;
        }
        catch (Exception sockCreationException)
        {
            System.out.println("Создание сокета провалено!");
        }
        if (socketCreated)
        {
            System.out.println("Сервер готов к работе. Его адрес: " + srvSocket.getInetAddress().toString().replace("/", "") + ":" + srvSocket.getLocalPort());
            listen();
        }
    }
    public void listen()
    {
        while (true)
        {
            Socket client = null;
            try
            {
                client = srvSocket.accept();
                System.out.println("\nКлиент с адресом " + client.getInetAddress().toString().replace("/", "") + " подключился.");
                ServerConnectionProcessor scp = new ServerConnectionProcessor(client, this);
                scp.start();
            }
            catch (IOException e)
            {
                System.out.println("\nКлиент с адресом " + client.getInetAddress().toString().replace("/", "")  + " отключился.");
            }
        }
    }
}

class ServerConnectionProcessor extends Thread
{
    private Socket clientSocket;
    private Server container;
    private DataOutputStream clientOutput;
    private DataInputStream clientInput;
    public ServerConnectionProcessor(Socket s, Server c)
    {
        clientSocket = s;
        container = c;
    }
    private void waitForEnter()
    {
        try
        {
            clientOutput.writeUTF("Для продолжения нажмите Enter");
            clientInput.readUTF();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void printOperationsList()
    {
        try
        {
            clientOutput.writeUTF("1. Получить полный список преподавателей.\n");
            clientOutput.writeUTF("2. Получить полный список студентов.\n");
            clientOutput.writeUTF("3. Получить полный список курсов.\n");
            clientOutput.writeUTF("4. Получить полный список компаний.\n");
            clientOutput.writeUTF("5. Получить списки студентов по курсам.\n");
            clientOutput.writeUTF("6. Получить список курсов для конкретного студента.\n");
            clientOutput.writeUTF("7. Получить списки преподавателей по курсам.\n");
            clientOutput.writeUTF("8. Получить списки преподавателей по компаниям.\n");
            clientOutput.writeUTF("9. Получить данные о конкретном преподавателе.\n");
            clientOutput.writeUTF("10. Получить данные о конкретном студенте.\n");
            clientOutput.writeUTF("11. Получить данные о конкретном курсе.\n");
            clientOutput.writeUTF("12. Получить данные о конкретной компании.\n");
            clientOutput.writeUTF("13. Получить расписание для участника учебного процесса на заданный интервал времени.\n");
            clientOutput.writeUTF("14. Изменить данные о преподавателе.\n");
            clientOutput.writeUTF("15. Изменить данные о студенте.\n");
            clientOutput.writeUTF("16. Изменить данные о курсе.\n");
            clientOutput.writeUTF("17. Добавить или удалить преподавателя.\n");
            clientOutput.writeUTF("18. Добавить или удалить студента.\n");
            clientOutput.writeUTF("19. Добавить или удалить курс.\n");
            clientOutput.writeUTF("20. Составить расписание занятий для курса.\n");
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при попытке отправить сообщение клиенту");
        }
    }
    public void run()
    {
        try
        {
            clientOutput = new DataOutputStream(clientSocket.getOutputStream());
            clientInput = new DataInputStream(clientSocket.getInputStream());
            String decision;
            do
            {
                clientOutput.writeUTF("\nВведите номер желаемой операции. Для выхода введите 'X-IT'.\n\n");
                printOperationsList();
                clientOutput.writeUTF("\nВаш выбор: ");
                decision = clientInput.readUTF();
                System.out.print("Клиент с адресом " + clientSocket.getInetAddress().toString().replace("/", "") + " запросил операцию " + decision);
                switch (decision.toUpperCase(Locale.getDefault()))
                {
                    case "1":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        clientOutput.writeUTF("Список преподавателей:\n|");
                        int i = 0;
                        for (Map.Entry<String, Tutor> tutorEntry : container.coursesSchedule.getTutors().entrySet())
                        {
                            String tutorEntryKey = tutorEntry.getKey();
                            clientOutput.writeUTF(container.coursesSchedule.getTutors().get(tutorEntryKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) clientOutput.writeUTF("\n|");
                        }
                        clientOutput.writeUTF("\n");
                        waitForEnter();
                        break;
                    }
                    case "2":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        clientOutput.writeUTF("Список студентов:\n|");
                        int i = 0;
                        for (Map.Entry<String, Student> studentEntry : container.coursesSchedule.getStudents().entrySet())
                        {
                            String studentEntryKey = studentEntry.getKey();
                            clientOutput.writeUTF(container.coursesSchedule.getStudents().get(studentEntryKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) clientOutput.writeUTF("\n|");
                        }
                        clientOutput.writeUTF("\n");
                        waitForEnter();
                        break;
                    }
                    case "3":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        clientOutput.writeUTF("Список курсов:\n|");
                        int i = 0;
                        for (Map.Entry<String, Course> courseEntry : container.coursesSchedule.getCourses().entrySet())
                        {
                            String courseEntryKey = courseEntry.getKey();
                            clientOutput.writeUTF(container.coursesSchedule.getCourses().get(courseEntryKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) clientOutput.writeUTF("\n|");
                        }
                        clientOutput.writeUTF("\n");
                        waitForEnter();
                        break;
                    }
                    case "4":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        clientOutput.writeUTF("Список компаний:\n|");
                        int i = 0;
                        for (Map.Entry<String, Company> companyEntry : container.coursesSchedule.getCompanies().entrySet())
                        {
                            String companyEntryKey = companyEntry.getKey();
                            clientOutput.writeUTF(container.coursesSchedule.getCompanies().get(companyEntryKey).getName() + "|");
                            i++;
                            if (i % 4 == 0) clientOutput.writeUTF("\n|");
                        }
                        clientOutput.writeUTF("\n");
                        waitForEnter();
                        break;
                    }
                    case "5":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        for(Map.Entry<String, Course> courseEntry : container.coursesSchedule.getCourses().entrySet())
                        {
                            String courseEntryKey = courseEntry.getKey();
                            clientOutput.writeUTF("Название курса: " + container.coursesSchedule.getCourses().get(courseEntryKey).getName() + "\n");
                            clientOutput.writeUTF(container.coursesSchedule.getCourses().get(courseEntryKey).returnEnrolledStudents() + "\n");
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
                            clientOutput.writeUTF("\nВведите имя студента в формате 'Фамилия И. О.': ");
                            studName = clientInput.readUTF();
                            if (container.coursesSchedule.getStudents().get(studName) != null)
                            {
                                inputSuccessful = true;
                                clientOutput.writeUTF("Студент " + studName + " найден! Список курсов, на которые он записан:\n|");
                                int i = 0;
                                for(Map.Entry<String, Course> courseEntry : container.coursesSchedule.getStudents().get(studName).getAttendedCoursesList().entrySet())
                                {
                                    String courseEntryKey = courseEntry.getKey();
                                    clientOutput.writeUTF(container.coursesSchedule.getCourses().get(courseEntryKey).getName() + "|");
                                    i++;
                                    if (i % 4 == 0) clientOutput.writeUTF("\n|");
                                }
                            }
                            else
                            {
                                clientOutput.writeUTF("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                studName = clientInput.readUTF();
                            }
                            clientOutput.writeUTF("\n");
                        }
                        while (!inputSuccessful && !studName.toUpperCase(Locale.getDefault()).equals("N"));
                        clientOutput.writeUTF("");
                        waitForEnter();
                        break;
                    }
                    case "7":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        for (Map.Entry<String, Course> courseEntry : container.coursesSchedule.getCourses().entrySet())
                        {
                            String courseEntryKey = courseEntry.getKey();
                            clientOutput.writeUTF("Название курса: " + container.coursesSchedule.getCourses().get(courseEntryKey).getName() + "\n");
                            clientOutput.writeUTF("Преподаватель: " + container.coursesSchedule.getCourses().get(courseEntryKey).getHostingTutor().getName() + "\n\n");
                        }
                        waitForEnter();
                        break;
                    }
                    case "8":
                    {
                        clientOutput.writeUTF("\nПолучение запрошенной информации...\n\n");
                        for (Map.Entry<String, Company> companyEntry : container.coursesSchedule.getCompanies().entrySet())
                        {
                            String companyEntryKey = companyEntry.getKey();
                            clientOutput.writeUTF("Название компании: " + container.coursesSchedule.getCompanies().get(companyEntryKey).getName() + "\n");
                            clientOutput.writeUTF("Список преподавателей:\n|");
                            int i = 0;
                            for (Map.Entry<String, Tutor> tutorEntry : container.coursesSchedule.getCompanies().get(companyEntryKey).getEmployedTutors().entrySet())
                            {
                                String tutorEntryKey = tutorEntry.getKey();
                                clientOutput.writeUTF(container.coursesSchedule.getTutors().get(tutorEntryKey).getName() + "|");
                                i++;
                                if (i % 4 == 0) clientOutput.writeUTF("|");
                            }
                            clientOutput.writeUTF("\n\n");
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
                            clientOutput.writeUTF("\nВведите имя преподавателя в формате 'Фамилия И. О.': ");
                            tutorName = clientInput.readUTF();
                            if (container.coursesSchedule.getTutors().get(tutorName) != null)
                            {
                                inputSuccessful = true;
                                clientOutput.writeUTF("Преподаватель " + tutorName + " найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getTutors().get(tutorName).returnFullInfo());
                            }
                            else
                            {
                                clientOutput.writeUTF("Преподавателя с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                tutorName = clientInput.readUTF();
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
                            clientOutput.writeUTF("\nВведите имя студента в формате 'Фамилия И. О.': ");
                            studName = clientInput.readUTF();
                            if (container.coursesSchedule.getStudents().get(studName) != null)
                            {
                                inputSuccessful = true;
                                clientOutput.writeUTF("Студент " + studName + " найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getStudents().get(studName).returnFullInfo());
                            } else
                            {
                                clientOutput.writeUTF("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                studName = clientInput.readUTF();
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
                            clientOutput.writeUTF("\nВведите название курса: ");
                            courseName = clientInput.readUTF();
                            if (container.coursesSchedule.getCourses().get(courseName) != null)
                            {
                                inputSuccessful = true;
                                clientOutput.writeUTF("Курс '" + courseName + "' найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getCourses().get(courseName).returnFullInfo());
                            } else
                            {
                                clientOutput.writeUTF("Курса с таким названием не существует. Чтобы уточнить название, Вы можете вывести полный список курсов.");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                courseName = clientInput.readUTF();
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
                            clientOutput.writeUTF("\nВведите название компании: ");
                            companyName = clientInput.readUTF();
                            if (container.coursesSchedule.getCompanies().get(companyName) != null)
                            {
                                inputSuccessful = true;
                                clientOutput.writeUTF("Компания " + companyName + " найдена! Вот полная информация о ней:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getCompanies().get(companyName).returnFullInfo());
                            } else
                            {
                                clientOutput.writeUTF("Компании с таким названием не существует. Чтобы уточнить название, Вы можете вывести полный список компаний.");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                companyName = clientInput.readUTF();
                            }
                        }
                        while (!inputSuccessful && !companyName.toUpperCase(Locale.getDefault()).equals("N"));
                        clientOutput.writeUTF("");
                        waitForEnter();
                        break;
                    }
                    case "13":
                    {
                        boolean nameInputSuccessful = false;
                        boolean typeInputSuccessful = false;
                        String name, type;
                        do
                        {
                            clientOutput.writeUTF("\nВведите роль участника учебного процесса (преподаватель/студент): ");
                            type = clientInput.readUTF();
                            if(type.toUpperCase(Locale.getDefault()).equals("ПРЕПОДАВАТЕЛЬ"))
                            {
                                typeInputSuccessful = true;
                                clientOutput.writeUTF("Введиите имя преподавателя в формате 'Фамилия И. О.': ");
                                name = clientInput.readUTF();
                                do
                                {
                                    if (container.coursesSchedule.getTutors().get(name) != null)
                                    {
                                        nameInputSuccessful = true;
                                        container.coursesSchedule.getScheduleFor(container.coursesSchedule.getTutors().get(name), name,null, null);
                                    }
                                    else
                                    {
                                        clientOutput.writeUTF("Преподавателя с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                                        clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                        name = clientInput.readUTF();
                                    }
                                }
                                while (!nameInputSuccessful && !name.toUpperCase(Locale.getDefault()).equals("N"));
                            }
                            else
                            if(type.toUpperCase(Locale.getDefault()).equals("СТУДЕНТ"))
                            {
                                typeInputSuccessful = true;
                                clientOutput.writeUTF("Введиите имя студента в формате 'Фамилия И. О.': ");
                                name = clientInput.readUTF();
                                do
                                {
                                    if (container.coursesSchedule.getStudents().get(name) != null)
                                    {
                                        nameInputSuccessful = true;
                                    }
                                    else
                                    {
                                        clientOutput.writeUTF("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                                        clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                        name = clientInput.readUTF();
                                    }
                                }
                                while (!nameInputSuccessful && !name.toUpperCase(Locale.getDefault()).equals("N"));
                            }
                            else
                            {
                                clientOutput.writeUTF("Роль участника учебного процесса введена с ошибкой");
                                clientOutput.writeUTF("\nПопробовать ещё раз? (Y/N): ");
                                type = clientInput.readUTF();
                            }
                        }
                        while (!typeInputSuccessful && !type.toUpperCase(Locale.getDefault()).equals("N"));
                        clientOutput.writeUTF("");
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
                        clientOutput.writeUTF("Завершение сеанса... нажмите Enter, чтобы завершить работу клиента.");
                        break;
                    }
                    default:
                    {
                        clientOutput.writeUTF("Введённое значение не определено.\n");
                        break;
                    }
                }
            }
            while (!decision.toUpperCase(Locale.getDefault()).equals("X-IT"));
            clientOutput.writeUTF("terminate");
            clientOutput.close(); clientInput.close();
            System.out.println("\nКлиент с адресом " + clientSocket.getInetAddress().toString().replace("/", "") + " отключился. ");
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

