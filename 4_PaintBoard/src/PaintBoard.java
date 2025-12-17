import java.util.ArrayList;

public class PaintBoard
{
    public static final int BOARD_WIDTH     = 80;
    public static final int BOARD_HEIGHT    = 30;

    private char[][] Board = new char[BOARD_HEIGHT][BOARD_WIDTH];

    public PaintBoard()
    {
        for(int i = 0; i < BOARD_HEIGHT; ++i)
        {
            for(int j = 0; j < BOARD_WIDTH; ++j)
            {
                if(IsVertex(i, j)) Board[i][j] = '+';
                else if(IsHorizontalEdge(i, j)) Board[i][j] = '-';
                else if(IsVerticalEdge(i, j)) Board[i][j] = '|';
                else Board[i][j] = ' ';
            }
        }
    }



    private boolean IsValidPos(Vec2 _Pos)
    {
        return (_Pos.GetX() >= 1 && _Pos.GetX() <= BOARD_WIDTH - 2 && _Pos.GetY() >= 1 && _Pos.GetY() <= BOARD_HEIGHT);
    }

    private boolean IsPassed(Vec2 _Prev, Vec2 _Next, Vec2 _Dest)
    {
        // Next가 Dest를 지나쳤는지 검사
        int toNext = Math.powExact((_Next.GetX() - _Prev.GetX()), 2) + Math.powExact((_Next.GetY() - _Prev.GetY()), 2);
        int toDest = Math.powExact((_Dest.GetX() - _Prev.GetX()), 2) + Math.powExact((_Dest.GetY() - _Prev.GetY()), 2);

        return (toNext > toDest);
    }

    public void SetBoard(Vec2 _Pos, char _c)
    {
        Board[_Pos.GetY()][_Pos.GetX()] = _c;
    }

    public boolean DrawRect(Vec2 _Start, Vec2 _End, char _c)
    {
        if(!IsValidPos(_Start) || !IsValidPos(_End)) return false;
        int StepX = (_End.GetX() > _Start.GetX()) ? (1) : (-1);
        int StepY = (_End.GetY() > _Start.GetY()) ? (1) : (-1);
        for(int i = _Start.GetY(); i <= _End.GetY(); i += StepY)
        {
            for(int j = _Start.GetX(); j <= _End.GetX(); j += StepX)
            {
                Board[i][j] = _c;
            }
        }
        return true;
    }

    public boolean DrawCircle(Vec2 _Center, int _Radius, char _c)
    {
        if(!IsValidPos(_Center)) return false;
        if(!(_Radius >= 2 && _Radius <= 14)) return false;
        int RadiusSquare = _Radius * _Radius;
        for(int i = 1; i < BOARD_HEIGHT - 1; ++i)
        {
            for(int j = 1; j < BOARD_WIDTH - 1; ++j)
            {
                int DistSquare = (_Center.GetX() - j) * (_Center.GetX() - j) + (_Center.GetY() - i) * (_Center.GetY() - i);
                if(DistSquare <= RadiusSquare)
                {
                    Board[i][j] = _c;
                }
            }
        }
        return true;
    }

    public boolean DrawLine(Vec2 _Start, Vec2 _End, char _c)
    {
        if(!IsValidPos(_Start) || !IsValidPos(_End)) return false;

        Vec2 _Dir = new Vec2(_End.GetX() - _Start.GetX(), _End.GetY() - _Start.GetY());
        // 시작점과 끝점이 같을 경우 예외처리
        if(_Dir.GetX() == 0 && _Dir.GetY() == 0)
        {
            SetBoard(_Start, _c);
            return true;
        }
        // 가로축 방향이거나, 세로축 방향인지 검사
        boolean bAxisDir = (_Dir.GetX() == 0 || _Dir.GetY() == 0);
        _Dir.Normalize();
        _Dir.Divide(5.f);
        Vec2 Cur = new Vec2(_Start.GetX(), _Start.GetY());
        Vec2 LastPainted = new Vec2(_Start.GetX(), _Start.GetY());
        SetBoard(LastPainted, _c);
        while(true)
        {
            Vec2 Next = Cur.Add(_Dir);
            if(IsPassed(Cur, Next, _End)) break;

            // 축 방향이 아닌 경우 x, y 좌표가 둘 다 변경되어야 새로 점 찍기
            if(!bAxisDir && Next.GetX() != LastPainted.GetX() && Next.GetY() != LastPainted.GetY())
            {
                SetBoard(Next, _c);
                LastPainted = new Vec2(Next.GetX_fx(), Next.GetY_fx());
            }

            Cur = new Vec2(Next.GetX_fx(), Next.GetY_fx());
        }
        return true;
    }

    private boolean IsVertex(int _Row, int _Col)
    {
        if(_Row == 0 && _Col == 0) return true;
        if(_Row == BOARD_HEIGHT - 1 && _Col == 0) return true;
        if(_Row == 0 && _Col == BOARD_WIDTH - 1) return true;
        if(_Row == BOARD_HEIGHT - 1 && _Col == BOARD_WIDTH - 1) return true;
        return false;
    }

    private boolean IsHorizontalEdge(int _Row, int _Col)
    {
        return (_Row == 0 || _Row == BOARD_HEIGHT - 1);
    }
    private boolean IsVerticalEdge(int _Row, int _Col)
    {
        return (_Col == 0 || _Col == BOARD_WIDTH - 1);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < BOARD_HEIGHT; ++i)
        {
            for(int j = 0; j < BOARD_WIDTH; ++j)
            {
                sb.append(Board[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
