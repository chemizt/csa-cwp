package server;

import auxClasses.Schedule;

import java.io.IOException;
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
                System.out.println("Произошла ошибка при открытии файла, попробуйте ещё раз.");
            }
        }
        while (!filesParsedSuccessfully);
    }
    public Server()
    {
        init();
    }
}
