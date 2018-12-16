package clientGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public Socket clientSocket;
    private Scanner input;
    private String serverName;
    private DataInputStream srvOutput; //response from server
    private DataOutputStream srvInput; //query to server
    public Client(Inet4Address srvAddr, int srvPort, String srvName) throws IOException
    {
        try
        {
            clientSocket = new Socket(srvAddr, srvPort);
            serverName = srvName;
            srvOutput = new DataInputStream(clientSocket.getInputStream());
            srvInput = new DataOutputStream(clientSocket.getOutputStream());
            input = new Scanner(System.in);
        }
        catch (IOException connException)
        {
            throw connException;
        }
    }
    public void requestFullList(String operationID)
    {
        try
        {
            srvInput.writeUTF(operationID);
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
    }
    public void requestStudentsByCourses()
    {
        try
        {
            srvInput.writeUTF("5");
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
    }
    public void requestCoursesForStudent(String studName)
    {
        try
        {
            srvInput.writeUTF("6");
            Thread.sleep(125);
            srvInput.writeUTF(studName);
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public void requestTutorsByCourses()
    {
        try
        {
            srvInput.writeUTF("7");
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
    }
    public void requestTutorsByCompanies()
    {
        try
        {
            srvInput.writeUTF("8");
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
    }
    public void requestFullInfoFor(String name, String operationID)
    {
        try
        {
            srvInput.writeUTF(operationID);
            Thread.sleep(125);
            srvInput.writeUTF(name);
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public String processServerOutput()
    {
        String response = "";
        try
        {
            while (srvOutput.available() > 0)
            {
                response = response.concat(srvOutput.readUTF());
            }
        }
        catch (IOException exception)
        {
            System.out.println("Ошибка при выводе сообщения от сервера");
        }
        return response;
    }
    public void terminateServerConnection()
    {
        try
        {
            srvInput.writeUTF("x-it");
            Thread.sleep(125);
            srvOutput.close();
            srvInput.close();
            clientSocket.close();
        }
        catch (IOException e){}
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class ServerOutputProcessor extends Thread
{
    public DataInputStream srvOutput;
    public String response;
    public ServerOutputProcessor(DataInputStream o)
    {
        srvOutput = o;
        response = "";
    }
    public void run()
    {
        try
        {
            while (srvOutput.available() > 0)
            {
                response = response.concat(srvOutput.readUTF());
            }
        }
        catch (IOException exception)
        {
            System.out.println("Ошибка при выводе сообщения от сервера");
        }
    }
}