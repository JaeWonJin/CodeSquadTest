import java.util.ArrayList;

public class Stage
{
    static final int TILE_NONE      = 0;
    static final int TILE_HALL      = 1;
    static final int TILE_BALL      = 2;
    static final int TILE_PLAYER    = 3;
    static final int TILE_WALL      = 4;
    static final int TILE_BALL_IN   = 5;
    static final int TILE_END       = 6;

    static final char[] Tilechar = new char[]{' ', 'O', 'o', 'P', '#', 'a'};

    ArrayList<ArrayList<Integer>> TileMap;
    int[] TileCntArr = new int[TILE_END];
    int MaxRow;
    int MaxCol;
    RowCol PlayerPos = new RowCol(0, 0);

    private char toChar(int _TileInfo)
    {
        if(_TileInfo >= TILE_END || _TileInfo < 0) return ' ';
        return Tilechar[_TileInfo];
    }

    private int toInt(char _c)
    {
        for(int i = 0; i < TILE_END; ++i)
        {
            if(Tilechar[i] == _c) return i;
        }
        return TILE_NONE;
    }

    public Stage(ArrayList<String> _StrMap, int _Row, int _Col)
    {
        MaxRow = _Row;
        MaxCol = _Col;
        TileMap = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < _StrMap.size(); ++i)
        {
            String strline = _StrMap.get(i);
            ArrayList<Integer> TileLine = new ArrayList<Integer>(strline.length());
            TileMap.add(TileLine);
            for(int j = 0; j < strline.length(); ++j)
            {
                char c = strline.charAt(j);
                int TileInfo = toInt(c);
                TileLine.add(TileInfo);
                TileCntArr[TileInfo] += 1;
                if(TileInfo == TILE_PLAYER)
                {
                    PlayerPos.row = i;
                    PlayerPos.col = j;
                }
            }
            while(TileLine.size() < MaxCol)
            {
                TileLine.add(TILE_NONE);
            }
        }
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < TileMap.size(); ++i)
        {
            for(int j = 0; j < TileMap.get(i).size(); ++j)
            {
                sb.append(toChar(TileMap.get(i).get(j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String Desc()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("가로 크기 :%d\n", MaxCol));
        sb.append(String.format("세로 크기 :%d\n", MaxRow));
        sb.append(String.format("구멍의 수 :%d\n", TileCntArr[TILE_HALL]));
        sb.append(String.format("공의 수 :%d\n", TileCntArr[TILE_BALL]));
        sb.append(String.format("플레이어 위치 %d행 %d열\n", PlayerPos.row + 1, PlayerPos.col + 1));
        return sb.toString();
    }
}
