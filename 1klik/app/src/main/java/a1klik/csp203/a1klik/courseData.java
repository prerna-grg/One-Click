package a1klik.csp203.a1klik;

public class courseData {
    private String source;
    private char delimghter;
    private int current_index;
    private int size;
    public boolean hasNext()
    {
        return (current_index<size);
    }
    public int num_Total_lines()
    {
        int total=0;
        for (int i=0;i<size;i++)
        {
            if (source.charAt(i)==delimghter)
            {
                total++;
            }
        }
        return total;
    }
    public String getNextLine()
    {
        String result="";
        int i=current_index;
        while (i<size && source.charAt(i)!=delimghter)
        {
            result=result+source.charAt(i);
            i++;
        }
        current_index=i+1;
        return result;
    }
    public courseData(String input,char delim)
    {
        source=input;
        delimghter=delim;
        current_index=0;
        size=input.length();
    }
}
