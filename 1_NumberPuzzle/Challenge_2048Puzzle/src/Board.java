import java.util.ArrayList;
import java.util.Random;

public class Board
{
    static RowCol[] DirSteps =
    {
        new RowCol(1, 0),   // UP
        new RowCol(-1, 0),    // DOWN
        new RowCol(0, 1),   // LEFT
        new RowCol(0, -1)     // RIGHT
    };
    int MaxRow;
    int MaxCol;
    int[][] Arr2D;
    ArrayList<RowCol> vecVoidTiles;
    ArrayList<RowCol>[] vecStartTiles;

    public Board(int _Row, int _Col)
    {
        MaxRow = _Row;
        MaxCol = _Col;
        Arr2D = new int[MaxRow][MaxCol];
        vecVoidTiles = new ArrayList<RowCol>();
    }

    public boolean CanMove()
    {
        // 빈 타일이 하나라도 있으면 움직일 수 있음
        if(!vecVoidTiles.isEmpty()) return true;

        for(int i = 0; i < DIR.END.getValue(); ++i)
        {
            ArrayList<RowCol> vecTiles = vecStartTiles[i];
            RowCol Step = DirSteps[i];
            for(RowCol Pos : vecTiles)
            {
                RowCol CurPos = new RowCol(Pos.row, Pos.col);
                while(true)
                {
                    RowCol NextPos = CurPos.add(Step);
                    if(!IsValidPos(NextPos)) break;

                    if(Get(CurPos) != 0 && Get(CurPos) == Get(NextPos)) return true;

                    CurPos.copy(NextPos);
                }
            }
        }

        return false;
    }

    public void UpdateVoidTiles()
    {
        vecVoidTiles.clear();
        for(int i = 0; i < MaxRow; ++i)
        {
            for(int j = 0; j < MaxCol; ++j)
            {
                if(Get(i, j) == 0)
                {
                    vecVoidTiles.add(new RowCol(i, j));
                }
            }
        }
    }

    public void SetTile(RowCol _Pos, int _Value)
    {
        // 실제 보드 변경
        Arr2D[_Pos.row][_Pos.col] = _Value;
    }

    public int Get(RowCol _Pos)
    {
        return Get(_Pos.row, _Pos.col);
    }

    public int Get(int _Row, int _Col)
    {
        return Arr2D[_Row][_Col];
    }

    public void Init()
    {
        for(int i = 0; i < MaxRow; ++i)
        {
            for(int j = 0; j < MaxCol; ++j)
            {
                // 처음 초기화는 SetTile 호출 대신 직접 값 변경 + 빈타일 집합에 모두 집어넣기
                Arr2D[i][j] = 0;
            }
        }
        UpdateVoidTiles();

        vecStartTiles = (ArrayList<RowCol>[]) new ArrayList[DIR.END.getValue()];
        for (int i = 0; i < DIR.END.getValue(); ++i)
        {
            vecStartTiles[i] = new ArrayList<>();
            if(i == DIR.UP.getValue())
            {
                for(int Col = 0; Col < MaxCol; ++Col) vecStartTiles[i].add(new RowCol(0, Col));
            }
            else if(i == DIR.DOWN.getValue())
            {
                for(int Col = 0; Col < MaxCol; ++Col) vecStartTiles[i].add(new RowCol(MaxRow - 1, Col));
            }
            else if(i == DIR.RIGHT.getValue())
            {
                for(int Row = 0; Row < MaxRow; ++Row) vecStartTiles[i].add(new RowCol(Row, MaxCol - 1));
            }
            else if(i == DIR.LEFT.getValue())
            {
                for(int Row = 0; Row < MaxRow; ++Row) vecStartTiles[i].add(new RowCol(Row, 0));
            }
        }


    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < MaxRow; ++i)
        {
            for (int j = 0; j < MaxCol; ++j)
            {
                if(Arr2D[i][j] == 0)
                {
                    s.append("[    ]");
                }
                else
                {
                    s.append(String.format("[%4d]", Arr2D[i][j]));
                }
            }
            s.append("\n");
        }
        s.append("\n");

        return s.toString();
    }

    // 랜덤 빈 타일 1 ~ 2개 추출하여 2(90%) 혹은 4(10%) 스폰
    // 빈 타일이 없을 경우 중단
    public void SpawnRandomTile()
    {
        Random random = new Random();
        for(int i = 0; i < 2; ++i)
        {
            if(vecVoidTiles.isEmpty()) break;

            int RandIdx = random.nextInt(vecVoidTiles.size());  // 0 ~ size - 1
            int Rand = random.nextInt(10) + 1;      // 1 ~ 10
            int Value;
            if(Rand <= 9) Value = 2;
            else Value = 4;
            // 비복원 빈 타일 추출
            RowCol Pos = vecVoidTiles.remove(RandIdx);
            SetTile(Pos, Value);

            // 여기서 더 스폰할지(2개), 그만 스폰할지(1개) 랜덤으로 결정
            int RandExit = random.nextInt(2);       // 0 ~ 1
            if(RandExit == 1) break;
        }

    }

    // 유효하지 않은 타일인지 검사
    public boolean IsValidPos(RowCol _Pos)
    {
        return (_Pos.row >= 0 && _Pos.row < MaxRow && _Pos.col >= 0 && _Pos.col < MaxCol);
    }

    public void MoveValue(RowCol _Start, RowCol _Dest)
    {
        SetTile(_Dest, Get(_Start));
        SetTile(_Start, 0);
    }

    public boolean MergeValue(RowCol _Start, RowCol _Dest)
    {
        SetTile(_Dest, Get(_Start) + Get(_Dest));
        SetTile(_Start, 0);
        return Get(_Dest) >= 2048;
    }

    public void Slide(DIR _Dir)
    {
        ArrayList<RowCol> vecTiles = vecStartTiles[_Dir.getValue()];
        RowCol Step = DirSteps[_Dir.getValue()];
        for(int i = 0; i < vecTiles.size(); ++i)
        {
            RowCol TargetPos = vecTiles.get(i);
            RowCol CurPos = vecTiles.get(i);
            while(IsValidPos(CurPos))
            {
                if(Get(CurPos) != 0)
                {
                    if(TargetPos.row != CurPos.row || TargetPos.col != CurPos.col)
                    {
                        MoveValue(CurPos, TargetPos);
                    }
                    TargetPos = TargetPos.add(Step);
                }

                CurPos = CurPos.add(Step);
            }

        }
    }

    public boolean Merge(DIR _Dir)
    {
        // 2048 완성시 true 반환
        boolean bCompleted = false;
        ArrayList<RowCol> vecTiles = vecStartTiles[_Dir.getValue()];
        RowCol Step = DirSteps[_Dir.getValue()];
        for(int i = 0; i < vecTiles.size(); ++i)
        {
            RowCol CurPos = vecTiles.get(i);
            while(IsValidPos(CurPos))
            {
                if(Get(CurPos) != 0)
                {
                    RowCol NextPos = CurPos.add(Step);
                    if(!IsValidPos(NextPos)) break;
                    if(Get(CurPos) == Get(NextPos))
                    {
                       bCompleted |= MergeValue(NextPos, CurPos);
                    }
                }

                CurPos = CurPos.add(Step);
            }
        }
        return bCompleted;
    }
}
