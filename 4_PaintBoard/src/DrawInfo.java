import java.util.ArrayList;

public class DrawInfo
{
    public ArrayList<Integer> vecCoords;
    char c;
    String RelativePath;

    public DrawInfo()
    {
        vecCoords = new ArrayList<Integer>(10);
        c = '\0';
        RelativePath = "";
    }
}
