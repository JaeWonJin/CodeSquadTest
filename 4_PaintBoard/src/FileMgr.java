import java.io.*;

public class FileMgr
{
    public boolean Load(String _RelativePath, PaintBoard _Board)
    {
        try
        {
            FileInputStream fis = new FileInputStream(_RelativePath);
            DataInputStream dis = new DataInputStream(fis);
            int Width = dis.readInt();
            int Height = dis.readInt();

            if(Width != PaintBoard.BOARD_WIDTH || Height != PaintBoard.BOARD_HEIGHT)
            {
                return false;
            }
            for(int i = 1; i < Height - 1; ++i)
            {
                for(int j = 1; j < Width - 1; ++j)
                {
                    _Board.SetBoard(i, j, dis.readChar());
                }
            }

            fis.close();
            dis.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean Save(String _RelativePath, PaintBoard _Board)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(_RelativePath);
            DataOutputStream dos = new DataOutputStream(fos);

            dos.writeInt(PaintBoard.BOARD_WIDTH);
            dos.writeInt(PaintBoard.BOARD_HEIGHT);
            for(int i = 1; i < PaintBoard.BOARD_HEIGHT - 1; ++i)
            {
                for(int j = 1; j < PaintBoard.BOARD_WIDTH - 1; ++j)
                {
                    dos.writeChar(_Board.GetBoard(i, j));
                }
            }

            fos.close();
            dos.close();

        }catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String CheckExt(String _Path, String _Ext)
    {
        StringBuilder sb = new StringBuilder();
        String[] strings = _Path.split("\\.");
        if(strings.length > 2)
        {
            return strings[0] + _Path;
        }
        else if(strings.length == 2)
        {

            if(strings[1].compareTo(_Ext) == 0)
            {
                return _Path;
            }
            else
            {
                return strings[0] + _Path;
            }
        }
        return _Path + _Ext;
    }



    private static FileMgr g_Inst = null;
    public static FileMgr GetInst()
    {
        if(g_Inst == null)
            g_Inst = new FileMgr();
        return g_Inst;
    }
    private FileMgr()
    {
    }
}
