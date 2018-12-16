package clientGUI;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Inet4Address;
import java.text.ParseException;
import java.util.StringTokenizer;

public class clientGUI
{
    private static JFrame mainFrame;
    private JPanel mainPanel;
    private JFormattedTextField serverAddressTextField;
    private JButton connectButton;
    private JTextField portTextField;
    private JTextArea serverOutput;
    private JButton disconnectButton;
    private JRadioButton fullListRadioButton;
    private JRadioButton infoAboutRadioButton;
    private JRadioButton modifyRadioButton;
    private JComboBox firstBtnCBox;
    private JComboBox secondBtnCBox;
    private JRadioButton addRadioButton;
    private JButton queryButton;
    private JButton studentsByCourses;
    private JButton coursesForStudent;
    private JButton tutorsByCourses;
    private JButton tutorsByCompanies;
    private JComboBox thirdBtnCBox;
    private JComboBox fourthBtnCBox;
    private Client clientBackend;
    private Inet4Address serverAddress;
    private int serverPort;
    private String serverName;
    private final static String errorSubMessage = "не существует";
    public static void main(String[] args)
    {
        mainFrame = new JFrame("CWP Client");
        mainFrame.setContentPane(new clientGUI().mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public clientGUI()
    {
        queryButton.setText("<html><center>Выполнить<br />запрос</center></html>");
        studentsByCourses.setText("<html><center>Получить список<br />студентов по курсам</center></html>");
        coursesForStudent.setText("<html><center>Получить список курсов<br />для конкретного студента</center></html>");
        tutorsByCourses.setText("<html><center>Получить список<br />преподавателей по курсам</center></html>");
        tutorsByCompanies.setText("<html><center>Получить список<br />преподавателей по компаниям</center></html>");
        mainFrame.addWindowListener(new WindowAdapter()
                                    {
                                        @Override
                                        public void windowClosing(WindowEvent windowEvent)
                                        {
                                            if (clientBackend != null) clientBackend.terminateServerConnection();
                                            try
                                            {
                                                Thread.sleep(125);
                                            }
                                            catch (InterruptedException e)
                                            {
                                                e.printStackTrace();
                                            }
                                            System.exit(0);
                                        }
                                    });
        connectButton.addActionListener(e ->
        {
            Object offender = null;
            try
            {
                offender = serverAddress;
                serverAddress = (Inet4Address) Inet4Address.getByName(serverAddressTextField.getText());
                serverName = serverAddressTextField.getText();
                offender = serverPort;
                serverPort = Integer.parseInt(portTextField.getText());
                clientBackend = new Client(serverAddress, serverPort, serverName);
                JOptionPane.showMessageDialog(mainPanel, "Соединение с сервером " + serverName + ":" + serverPort + " установлено");
                serverOutput.append(clientBackend.processServerOutput());
            }
            catch (Exception uHX)
            {
                JOptionPane.showMessageDialog(mainPanel, "Адрес сервера или порт введены неверно (возможно, сервер не запущен). Попробуйте ещё раз");
                if (offender.equals(serverAddress)) serverAddressTextField.setText("");
                else if (offender.equals(serverPort)) portTextField.setText("");
            }
        });
        disconnectButton.addActionListener(e ->
        {
            if (connOnline())
            {
                clientBackend.terminateServerConnection();
                JOptionPane.showMessageDialog(mainPanel, "Соединение с сервером " + serverName + ":" + serverPort + " разорвано");
            }
        });
        queryButton.addActionListener(e ->
        {
            if (connOnline())
            {
                if (fullListRadioButton.isSelected())
                {
                    clientBackend.requestFullList(String.valueOf(firstBtnCBox.getSelectedIndex() + 1));
                    try
                    {
                        Thread.sleep(125);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                    serverOutput.append(clientBackend.processServerOutput());
                }
                else if (infoAboutRadioButton.isSelected())
                {
                    switch (secondBtnCBox.getSelectedIndex())
                    {
                        case 0:
                        {
                            String tutorName = JOptionPane.showInputDialog(mainFrame, "Введите имя преподавателя в формате 'Фамилия И. О.'");
                            if (tutorName != null)
                            {
                                clientBackend.requestFullInfoFor(tutorName, "9");
                                try
                                {
                                    Thread.sleep(125);
                                }
                                catch (InterruptedException e1)
                                {
                                    e1.printStackTrace();
                                }
                                String response = clientBackend.processServerOutput();
                                if (response.contains(errorSubMessage))
                                {
                                    JOptionPane.showMessageDialog(mainPanel, response);
                                } else serverOutput.append(response);
                            }
                            break;
                        }
                        case 1:
                        {
                            String studName = JOptionPane.showInputDialog(mainFrame, "Введите имя студента в формате 'Фамилия И. О.'");
                            if (studName != null)
                            {
                                clientBackend.requestFullInfoFor(studName, "10");
                                try
                                {
                                    Thread.sleep(125);
                                }
                                catch (InterruptedException e1)
                                {
                                    e1.printStackTrace();
                                }
                                String response = clientBackend.processServerOutput();
                                if (response.contains(errorSubMessage))
                                {
                                    JOptionPane.showMessageDialog(mainPanel, response);
                                } else serverOutput.append(response);
                            }
                            break;
                        }
                        case 2:
                        {
                            String courseName = JOptionPane.showInputDialog(mainFrame, "Введите название курса");
                            if (courseName != null)
                            {
                                clientBackend.requestFullInfoFor(courseName, "11");
                                try
                                {
                                    Thread.sleep(125);
                                }
                                catch (InterruptedException e1)
                                {
                                    e1.printStackTrace();
                                }
                                String response = clientBackend.processServerOutput();
                                if (response.contains(errorSubMessage))
                                {
                                    JOptionPane.showMessageDialog(mainPanel, response);
                                } else serverOutput.append(response);
                            }
                            break;
                        }
                        case 3:
                        {
                            String companyName = JOptionPane.showInputDialog(mainFrame, "Введите название компании");
                            if (companyName != null)
                            {
                                clientBackend.requestFullInfoFor(companyName, "12");
                                try
                                {
                                    Thread.sleep(125);
                                }
                                catch (InterruptedException e1)
                                {
                                    e1.printStackTrace();
                                }
                                String response = clientBackend.processServerOutput();
                                if (response.contains(errorSubMessage))
                                {
                                    JOptionPane.showMessageDialog(mainPanel, response);
                                } else serverOutput.append(response);
                            }
                            break;
                        }
                    }

                }
            }
        });
        studentsByCourses.addActionListener(e ->
        {
            if (connOnline())
            {
                clientBackend.requestStudentsByCourses();
                try
                {
                    Thread.sleep(125);
                }
                catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }
                serverOutput.append(clientBackend.processServerOutput());
            }
        });
        coursesForStudent.addActionListener(e ->
        {
            if (connOnline())
            {
                String studName = JOptionPane.showInputDialog(mainFrame, "Введите имя студента в формате 'Фамилия И. О.'");
                if (studName != null)
                {
                    clientBackend.requestCoursesForStudent(studName);
                    try
                    {
                        Thread.sleep(125);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                    String response = clientBackend.processServerOutput();
                    if (response.contains(errorSubMessage))
                    {
                        JOptionPane.showMessageDialog(mainPanel, response);
                    } else serverOutput.append(response);
                }
            }
        });
        tutorsByCourses.addActionListener(e ->
        {
            if (connOnline())
            {
                clientBackend.requestTutorsByCourses();
                try
                {
                    Thread.sleep(125);
                }
                catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }
                serverOutput.append(clientBackend.processServerOutput());
            }
        });
        tutorsByCompanies.addActionListener(e ->
        {
            if (connOnline())
            {
                clientBackend.requestTutorsByCompanies();
                try
                {
                    Thread.sleep(125);
                }
                catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }
                serverOutput.append(clientBackend.processServerOutput());
            }
        });
    }

    private boolean connOnline()
    {
        boolean connStatus = clientBackend != null && !clientBackend.clientSocket.isClosed();
        if (!connStatus) JOptionPane.showMessageDialog(mainPanel, "В данный момент Вы не подключены ни к какому серверу");
        return connStatus;
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
