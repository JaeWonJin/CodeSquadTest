package P2;

public class P2_ProcessResult
{
    static final int PROCESS_ENDGAME    = 0;
    static final int PROCESS_CONTINUE   = 1;

    public int gameResult;
    public String desc;
    public boolean bPrintDesc;

    public P2_ProcessResult(int _Result, String _Desc, boolean _bPrintDesc)
    {
        gameResult = _Result;
        desc = _Desc;
        bPrintDesc = _bPrintDesc;
    }

    public String Desc()
    {
        return desc;
    }
}
