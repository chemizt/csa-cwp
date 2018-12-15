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
    public boolean operationTerminated;
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
    public void run()
    {
        try
        {
            String query; operationTerminated = false;
            ServerOutputProcessor sop = new ServerOutputProcessor(srvOutput, this);
            sop.start();
            while (!operationTerminated)
            {
                query = input.nextLine();
                srvInput.writeUTF(query);
            }
        }
        catch (IOException connException)
        {
            System.out.println("Проблема при попытке установления соединения с сервером. Перезапустите клиент и попробуйте ещё раз.");
        }
    }
    public void terminateServerConnection()
    {
        try
        {
            srvInput.writeUTF("x-it");
            srvOutput.close();
            srvInput.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

class ServerOutputProcessor extends Thread
{
    public Client container;
    public DataInputStream srvOutput;
    public ServerOutputProcessor(DataInputStream o, Client c)
    {
        container = c;
        srvOutput = o;
    }
    public void run()
    {
        String response;
        try
        {
            while (!container.operationTerminated)
            {
                while (srvOutput.available() > 0)
                {
                    response = srvOutput.readUTF();
                    if (response.contains("terminate"))
                    {
                        container.operationTerminated = true;
                    }
                    else
                    {
                        System.out.print(response);
                    }
                }
            }
        }
        catch (IOException exception)
        {
            System.out.println("Ошибка при выводе сообщения от сервера");
        }
    }
}