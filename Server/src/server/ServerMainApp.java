package server;

import java.util.Scanner;

public class ServerMainApp
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Введите номер порта, на котором должен работать сервер: ");
        int srvPort = input.nextInt();
        Server serverBackend = new Server(srvPort);
    }
}
