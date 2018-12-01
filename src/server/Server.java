package server;

import dataClasses.Schedule;

import java.io.IOException;
import java.util.Scanner;

public class Server
{
    private Schedule coursesSchedule;
    private void init()
    {
        coursesSchedule = new Schedule();
        boolean fileParsedSuccessfully = false;
        do
        {
            System.out.print("Введите называние файла-источника: ");
            Scanner input = new Scanner(System.in);
            try
            {
                coursesSchedule.parseFile(input.nextLine());
                fileParsedSuccessfully = true;
            }
            catch (IOException fileInputException)
            {
                System.out.println("Произошла ошибка при открытии файла, попробуйте ещё раз.");
            }
        }
        while (!fileParsedSuccessfully);
    }
    public Server()
    {
        init();
    }
}
