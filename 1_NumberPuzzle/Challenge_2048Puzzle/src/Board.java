import java.util.ArrayList;
import java.util.Random;

public class Board
{
    int row;
    int col;
    int[][] Arr2D;
    ArrayList<RowCol> vecVoidTiles;

    public Board(int _Row, int _Col)
    {
        row = _Row;
        col = _Col;
        Arr2D = new int[row][col];
        vecVoidTiles = new ArrayList<RowCol>();
    }

    public void SetTile(RowCol _Pos, int _Value)
    {
        // 들어온 값이 0이고, 이전 값이 0이 아니라면
        if(_Value == 0)
        {
            if(Arr2D[_Pos.row][_Pos.col] != 0)
            {
                // 빈 타일 집합에 추가
                vecVoidTiles.add(_Pos);
            }
        }
        // 들어온 값이 0이 아니고, 이전 값이 0이었다면
        else
        {
            if(Arr2D[_Pos.row][_Pos.col] == 0)
            {
                // 빈 타일 집합에서 제거
                vecVoidTiles.remove(_Pos);
            }
        }

        // 실제 보드 변경
        Arr2D[_Pos.row][_Pos.col] = _Value;
    }

    public void Init()
    {
        for(int i = 0; i < row; ++i)
        {
            for(int j = 0; j < col; ++j)
            {
                // 처음 초기화는 SetTile 호출 대신 직접 값 변경 + 빈타일 집합에 모두 집어넣기
                Arr2D[i][j] = 0;
                vecVoidTiles.add(new RowCol(i, j));
            }
        }
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < row; ++i)
        {
            for (int j = 0; j < col; ++j)
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
            int RandValue = (random.nextInt(2) + 1) * 2;    // 2 or 4
            // 비복원 빈 타일 추출
            RowCol Pos = vecVoidTiles.remove(RandIdx);
            SetTile(Pos, RandValue);

            // 여기서 더 스폰할지(2개), 그만 스폰할지(1개) 랜덤으로 결정
            int RandExit = random.nextInt(2);       // 0 ~ 1
            if(RandExit == 1) break;
        }

    }

    public void Slide(DIR _Dir)
    {

    }

    public void Merge(DIR _Dir)
    {
        
    }
}
