package dataClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.Files.newBufferedReader;

public class Schedule
{
    public ArrayList<Lesson> getSchedule()
    {
        return schedule;
    }
    public void setSchedule(ArrayList<Lesson> schedule)
    {
        this.schedule = schedule;
    }
    private ArrayList<Lesson> schedule;
    public void parseFile(String fileToParse) throws IOException
    {
        Path inputFilePath = FileSystems.getDefault().getPath(fileToParse);
        try
        {
            BufferedReader inputFileBuffer = newBufferedReader(inputFilePath, StandardCharsets.UTF_8);

        }
        catch (IOException buffReaderException)
        {
            throw buffReaderException;
        }
    }
}
