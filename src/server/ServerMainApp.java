package server;

public class ServerMainApp
{
    public static void main(String[] args)
    {
        Server serverBackend = new Server();
        serverBackend.listen();
    }
}
