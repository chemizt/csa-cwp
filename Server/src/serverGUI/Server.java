package serverGUI;

import auxClasses.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
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
                ServerConnectionProcessor scp = new ServerConnectionProcessor(client, this);
                System.out.println("\n" + scp.getTimestamp() + " Клиент с адресом " + client.getInetAddress().toString().replace("/", "") + " подключился.");
                scp.start();
            }
            catch (IOException e)
            {
                System.out.println("Клиент с адресом " + client.getInetAddress().toString().replace("/", "")  + " отключился.");
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
    public void run()
    {
        try
        {
            clientOutput = new DataOutputStream(clientSocket.getOutputStream());
            clientInput = new DataInputStream(clientSocket.getInputStream());
            String decision;
            clientOutput.writeUTF("Добро пожаловать на сервер!\n");
            do
            {
                decision = clientInput.readUTF();
                System.out.println(getTimestamp() + " Клиент с адресом " + clientSocket.getInetAddress().toString().replace("/", "") + " запросил операцию " + decision);
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
                        break;
                    }
                    case "6":
                    {
                        String studName;
                            studName = clientInput.readUTF();
                            if (container.coursesSchedule.getStudents().get(studName) != null)
                            {
                                clientOutput.writeUTF("\nСтудент " + studName + " найден! Список курсов, на которые он записан:\n|");
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
                            }
                            clientOutput.writeUTF("\n");
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
                        break;
                    }
                    case "9":
                    {
                        String tutorName;
                            tutorName = clientInput.readUTF();
                            if (container.coursesSchedule.getTutors().get(tutorName) != null)
                            {
                                clientOutput.writeUTF("\nПреподаватель " + tutorName + " найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getTutors().get(tutorName).returnFullInfo());
                            }
                            else
                            {
                                clientOutput.writeUTF("Преподавателя с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            }
                        break;
                    }
                    case "10":
                    {
                        String studName;
                            studName = clientInput.readUTF();
                            if (container.coursesSchedule.getStudents().get(studName) != null)
                            {
                                clientOutput.writeUTF("\nСтудент " + studName + " найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getStudents().get(studName).returnFullInfo());
                            }
                            else
                            {
                                clientOutput.writeUTF("Студента с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                            }
                        break;
                    }
                    case "11":
                    {
                        String courseName;
                            courseName = clientInput.readUTF();
                            if (container.coursesSchedule.getCourses().get(courseName) != null)
                            {
                                clientOutput.writeUTF("\nКурс '" + courseName + "' найден! Вот полная информация о нём:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getCourses().get(courseName).returnFullInfo());
                            }
                            else
                            {
                                clientOutput.writeUTF("Курса с таким названием не существует. Чтобы уточнить название, Вы можете запросить полный список курсов.");
                            }
                            break;
                    }
                    case "12":
                    {
                        String companyName;
                            companyName = clientInput.readUTF();
                            if (container.coursesSchedule.getCompanies().get(companyName) != null)
                            {
                                clientOutput.writeUTF("\nКомпания " + companyName + " найдена! Вот полная информация о ней:\n");
                                clientOutput.writeUTF(container.coursesSchedule.getCompanies().get(companyName).returnFullInfo());
                            }
                            else
                            {
                                clientOutput.writeUTF("Компании с таким названием не существует. Чтобы уточнить название, Вы можете запросить полный список компаний.");
                            }
                        clientOutput.writeUTF("");
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
                        break;
                    }
                    case "14":
                    {
                        String tutorName;
                        tutorName = clientInput.readUTF();
                        if (container.coursesSchedule.getTutors().get(tutorName) != null)
                        {
                            clientOutput.writeUTF("\nПреподаватель " + tutorName + " найден! Вот полная информация о нём:\n");
                            clientOutput.writeUTF(container.coursesSchedule.getTutors().get(tutorName).returnFullInfo());
                        }
                        else
                        {
                            clientOutput.writeUTF("Преподавателя с таким именем не существует, имя введено с ошибкой, или не соответствует формату");
                        }
                        break;
                    }
                    case "15":
                    {
                        break;
                    }
                    case "16":
                    {
                        break;
                    }
                    case "17":
                    {
                        break;
                    }
                    case "18":
                    {
                        break;
                    }
                    case "19":
                    {
                        break;
                    }
                    case "20":
                    {
                        break;
                    }
                    case "21":
                    {
                        break;
                    }
                    case "22":
                    {
                        break;
                    }
                    case "X-IT":
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            while (!decision.toUpperCase(Locale.getDefault()).equals("X-IT"));
            clientOutput.writeUTF("terminate");
            clientOutput.close(); clientInput.close();
            System.out.println(getTimestamp() + " Клиент с адресом " + clientSocket.getInetAddress().toString().replace("/", "") + " отключился. ");
            clientSocket.close();
            container.coursesSchedule.dumpToFiles();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public String getTimestamp()
    {
        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
    }
}

