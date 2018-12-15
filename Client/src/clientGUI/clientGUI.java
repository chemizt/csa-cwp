package clientGUI;

import clientGUI.Client;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.ParseException;
import java.util.StringTokenizer;

public class clientGUI
{
    private JPanel mainPanel;
    private JFormattedTextField serverAddressTextField;
    private JButton connectButton;
    private JTextField portTextField;
    private JTextArea textArea1;
    private JButton disconnectButton;
    private Client clientBackend;
    private Inet4Address serverAddress;
    private int serverPort;
    private String serverName;
    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("CWP Client");
        mainFrame.setContentPane(new clientGUI().mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public clientGUI()
    {
        IPAddressFormatter addressFormat = new IPAddressFormatter();
        DefaultFormatterFactory addFormFactory = new DefaultFormatterFactory(addressFormat);
        //serverAddressTextField.setFormatterFactory(addFormFactory);
        connectButton.addActionListener(e ->
        {
            Object offender = null;
            try
            {
                offender = serverAddress;
                serverAddress = (Inet4Address) Inet4Address.getByName(serverAddressTextField.getText());
                serverName = serverAddressTextField.getText();
                serverPort = Integer.parseInt(portTextField.getText());
                offender = serverPort;
                clientBackend = new Client(serverAddress, serverPort, serverName);
                JOptionPane.showMessageDialog(mainPanel, "Соединение с сервером " + serverName + ":" + serverPort + " установлено");
            }
            catch (IOException uHX)
            {
                JOptionPane.showMessageDialog(mainPanel, "Адрес сервера или порт введены неверно, попробуйте ещё раз");
                if (offender.equals(serverAddress)) serverAddressTextField.setText("");
                else if (offender.equals(serverPort)) portTextField.setText("");
            }
        });
        disconnectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (clientBackend != null && !clientBackend.clientSocket.isClosed())
                {
                    clientBackend.terminateServerConnection();
                    JOptionPane.showMessageDialog(mainPanel, "Соединение с сервером " + serverName + ":" + serverPort + " разорвано");
                }
                else
                {
                    JOptionPane.showMessageDialog(mainPanel, "В данный момент Вы не подключены ни к какому серверу");
                }
            }
        });
    }

    class IPAddressFormatter extends DefaultFormatter //copy-pasted from http://www.java2s.com/Tutorials/Java/Swing/JFormattedText/Use_JFormattedTextField_to_accept_IP_address_only_in_Java.htm
    {
        public String valueToString(Object value) throws ParseException
        {
            if (!(value instanceof byte[])) throw new ParseException("Not a byte[]", 0);
            byte[] a = (byte[]) value;
            if (a.length != 4) throw new ParseException("Length != 4", 0);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 4; i++)
            {
                int b = a[i];
                if (b < 0) b += 256;
                builder.append(String.valueOf(b));
                if (i < 3) builder.append('.');
            }
            return builder.toString();
        }

        public Object stringToValue(String text) throws ParseException
        {
            StringTokenizer tokenizer = new StringTokenizer(text, ".");
            byte[] a = new byte[4];
            for (int i = 0; i < 4; i++)
            {
                int b = 0;
                if (!tokenizer.hasMoreTokens()) throw new ParseException("Too few bytes", 0);
                try
                {
                    b = Integer.parseInt(tokenizer.nextToken());
                }
                catch (NumberFormatException e)
                {
                    throw new ParseException("Not an integer", 0);
                }
                if (b < 0 || b >= 256) throw new ParseException("Byte out of range", 0);
                a[i] = (byte) b;
            }
            if (tokenizer.hasMoreTokens()) throw new ParseException("Too many bytes", 0);
            return a;
        }
    }
}
