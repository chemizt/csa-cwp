package auxClasses;

public class ParserAuxUtils
{
    public static String purgeString(String strToPurge)
    {
        return strToPurge.replace(strToPurge.substring(0, strToPurge.indexOf("#") + 1), "");
    }
    public static String returnSubString(String strToSearch)
    {
        return strToSearch.substring(0, strToSearch.indexOf("#"));
    }
}
