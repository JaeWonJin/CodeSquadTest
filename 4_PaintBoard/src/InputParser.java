import java.util.ArrayList;

public class InputParser
{
    public static final int CMD_HELP            = 0;
    public static final int CMD_DRAW_LINE       = 1;
    public static final int CMD_DRAW_CIRCLE     = 2;
    public static final int CMD_DRAW_RECT       = 3;
    public static final int CMD_QUIT            = 4;
    public static final int CMD_SAVE_BOARD      = 5;
    public static final int CMD_LOAD_BOARD      = 6;
    public static final int CMD_WRONG_INPUT     = 7;

    public static final String[] strCommands = new String[]{"help", "line", "circle", "rect", "quit", "save", "load"};
    public static final String[] strCommandTultips = new String[]
            {
                    ": 도움말, 지금 이 화면을 출력한다.",
                    " x1, y1, x2, y2, m: 두 좌표를 지정한 모양으로 잇는 선을 그린다.",
                    " x, y, r, m: x,y 를 중점으로 하는 반지름 r인 원을 그린다.",
                    " x1, y1, x2, y2, m: 두 좌표를 양 끝점으로 하는 직사각형을 그린다.",
                    ": 프로그램을 종료한다.",
                    " filename: 파일을 저장한다.",
                    " filename: 파일을 불러온다.",
            };

    public StringBuilder sbDesc = new StringBuilder();

    public int ParseInt(String _StrInput)
    {
        int Num = 0;
        for(int i = 0; i < _StrInput.length(); ++i)
        {
            char c = _StrInput.charAt(i);
            if(c == ' ') continue;
            if(c >= '0' && c <= '9')
            {
                Num = Num * 10 + (c - '0');
            }
            else return 0;
        }
        return Num;
    }

    public char ParseShape(String _StrInput)
    {
        if(_StrInput.length() > 2) return '\0';
        for(int i = 0; i < _StrInput.length(); ++i) {
            char c = _StrInput.charAt(i);
            if(c == ' ') continue;
            return c;
        }
        return '\0';
    }

    public void ParseDrawInfo(String _StrInput, DrawInfo _Info, int _Length)
    {
        _Info.vecCoords.clear();
        _Info.c = '\0';
        String[] strings = _StrInput.split(",");

        if(strings.length != _Length) return;

        for(int i = 0; i < strings.length; ++i)
        {
            if(i == strings.length - 1)
            {
                _Info.c = ParseShape(strings[i]);
            }
            else
            {
                _Info.vecCoords.add(ParseInt(strings[i]));
            }
        }
    }

    public int ProcessInput(String _StrInput, DrawInfo _Info)
    {
        _StrInput = _StrInput.toLowerCase();
        if(_StrInput.compareTo(strCommands[CMD_QUIT]) == 0)
        {
            return CMD_QUIT;
        }
        if(_StrInput.compareTo(strCommands[CMD_HELP]) == 0)
        {
            return CMD_HELP;
        }

        if(_StrInput.startsWith(strCommands[CMD_DRAW_LINE]))
        {
            int Offset = strCommands[CMD_DRAW_LINE].length();
            ParseDrawInfo(_StrInput.substring(Offset), _Info, 5);
            if(_Info.c != '\0') return CMD_DRAW_LINE;
        }
        if(_StrInput.startsWith(strCommands[CMD_DRAW_CIRCLE]))
        {
            int Offset = strCommands[CMD_DRAW_CIRCLE].length();
            ParseDrawInfo(_StrInput.substring(Offset), _Info, 4);
            if(_Info.c != '\0') return CMD_DRAW_CIRCLE;
        }
        if(_StrInput.startsWith(strCommands[CMD_DRAW_RECT]))
        {
            int Offset = strCommands[CMD_DRAW_RECT].length();
            ParseDrawInfo(_StrInput.substring(Offset), _Info, 5);
            if(_Info.c != '\0') return CMD_DRAW_RECT;
        }

        if(_StrInput.startsWith(strCommands[CMD_SAVE_BOARD]))
        {
            int Offset = strCommands[CMD_SAVE_BOARD].length();
            String RelativePath = FileMgr.GetInst().CheckExt(_StrInput.substring(Offset + 1), ".txt");
            _Info.RelativePath = RelativePath;
            return CMD_SAVE_BOARD;
        }
        if(_StrInput.startsWith(strCommands[CMD_LOAD_BOARD]))
        {
            int Offset = strCommands[CMD_LOAD_BOARD].length();
            String RelativePath = FileMgr.GetInst().CheckExt(_StrInput.substring(Offset + 1), ".txt");
            _Info.RelativePath = RelativePath;
            return CMD_LOAD_BOARD;
        }

        return CMD_WRONG_INPUT;
    }

    private static InputParser g_Inst = null;
    public static InputParser GetInst()
    {
        if(g_Inst == null)
            g_Inst = new InputParser();
        return g_Inst;
    }
    private InputParser()
    {
        for(int i = 0; i < strCommandTultips.length; ++i)
        {
            sbDesc.append(strCommands[i]);
            sbDesc.append(strCommandTultips[i]);
            sbDesc.append("\n");
        }
    }
}
