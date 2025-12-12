import java.util.ArrayList;

public class Stage
{
    static final int MASK_TILE = 0xFF;      // 하위 8비트 (0~255)
    static final int MASK_OBJ  = 0xFF00;    // 상위 8비트 (256~65535)

    static final int TILE_NONE      = 0;
    static final int TILE_HALL      = 1;
    static final int TILE_WALL      = 2;
    static final int TILE_END       = 3;

    static final int OBJ_NONE       = 0;
    static final int OBJ_PLAYER     = 0x100;
    static final int OBJ_BALL       = 0x200;

    static final int ORDER_QUIT         = 0;
    static final int ORDER_MOVE_OK      = 1;
    static final int ORDER_MOVE_FAIL    = 2;

    ArrayList<ArrayList<Integer>> Map;
    int HallCnt = 0;
    int BallCnt = 0;
    int BallInHallCnt = 0;
    int MaxRow;
    int MaxCol;
    RowCol PlayerPos = new RowCol(0, 0);

    private char toChar(int _TileInfo)
    {
        int tile = _TileInfo & MASK_TILE; // 하위 8비트 추출 (배경)
        int obj  = _TileInfo & MASK_OBJ;  // 상위 8비트 추출 (물체)
        if (tile == TILE_HALL && obj == OBJ_BALL) {
            return '◎'; // 혹은 체크 표시 등
        }
        // 2. 물체가 있으면 물체 우선 표시
        if (obj == OBJ_PLAYER) return 'P';
        if (obj == OBJ_BALL)   return 'o';

        // 3. 물체가 없으면 타일 표시
        if (tile == TILE_WALL) return '#';
        if (tile == TILE_HALL) return 'O'; // 구멍

        return ' '; // 빈 공간 (TILE_NONE)
    }

    private int toInt(char _c)
    {
        switch (_c)
        {
            case '#': return TILE_WALL;
            case 'O': return TILE_HALL;
            case 'P': return OBJ_PLAYER | TILE_NONE; // 플레이어 아래는 빈땅이라 가정
            case 'o': return OBJ_BALL | TILE_NONE;
            case '◎': return OBJ_BALL | TILE_HALL;
            case ' ': return TILE_NONE;
            default:  return TILE_NONE;
        }
    }

    public int GetTileInfo(RowCol _Pos)
    {
        return Map.get(_Pos.row).get(_Pos.col);
    }

    public void SetTileInfo(RowCol _Pos, int _TileInfo)
    {
        Map.get(_Pos.row).set(_Pos.col, _TileInfo);
    }

    public Stage(ArrayList<String> _StrMap, int _Row, int _Col)
    {
        MaxRow = _Row;
        MaxCol = _Col;
        Map = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < _StrMap.size(); ++i)
        {
            String strline = _StrMap.get(i);
            ArrayList<Integer> TileLine = new ArrayList<Integer>(strline.length());
            Map.add(TileLine);
            for(int j = 0; j < strline.length(); ++j)
            {
                char c = strline.charAt(j);
                int TileInfo = toInt(c);
                TileLine.add(TileInfo);
                int tile = TileInfo & MASK_TILE; // 하위 8비트 추출 (배경)
                int obj  = TileInfo & MASK_OBJ;
                if(obj == OBJ_PLAYER)
                {
                    PlayerPos.row = i;
                    PlayerPos.col = j;
                }
                else if(obj == OBJ_BALL)
                {
                    if(tile == TILE_HALL) ++BallInHallCnt;
                    else ++BallCnt;
                }
                else if(tile == TILE_HALL) ++HallCnt;
            }
            while(TileLine.size() < MaxCol)
            {
                TileLine.add(TILE_NONE);
            }
        }
    }

    public int ExecuteOrder(char _Order)
    {
        boolean bMoveSucess = false;
        switch(_Order)
        {
            case 'q':
                return ORDER_QUIT;
            case 'w':
                bMoveSucess = PlayerMove(RowCol.DIR_ROWCOLS[RowCol.DIR_UP]);
                break;
            case 'a':
                bMoveSucess = PlayerMove(RowCol.DIR_ROWCOLS[RowCol.DIR_LEFT]);
                break;
            case 's':
                bMoveSucess = PlayerMove(RowCol.DIR_ROWCOLS[RowCol.DIR_DOWN]);
                break;
            case 'd':
                bMoveSucess = PlayerMove(RowCol.DIR_ROWCOLS[RowCol.DIR_RIGHT]);
                break;
        }

        if(bMoveSucess) return ORDER_MOVE_OK;

        return ORDER_MOVE_FAIL;
    }

    private boolean IsValid(RowCol _Pos)
    {
        return (_Pos.row >= 0 && _Pos.row < MaxRow && _Pos.col >= 0 && _Pos.col < MaxCol);
    }

    static int MOVEABLE_DISABLE     = 0;
    static int MOVEABLE_ABLE        = 1;
    static int MOVEABLE_BALLEXIST   = 2;

    private int IsMoveable(RowCol _Pos)
    {
        if(!IsValid(_Pos)) return MOVEABLE_DISABLE;

        int TileInfo = GetTileInfo(_Pos);
        int Obj = TileInfo & MASK_OBJ;
        int Tile = TileInfo & MASK_TILE;
        if(Obj == OBJ_BALL) return MOVEABLE_BALLEXIST;
        if(Obj != 0) return MOVEABLE_DISABLE;
        if(Tile == TILE_WALL) return MOVEABLE_DISABLE;

        return MOVEABLE_ABLE;
    }

    private void MoveObj(RowCol _Start, RowCol _Dest)
    {
        int TileInfo = GetTileInfo(_Start);
        int Obj = TileInfo & MASK_OBJ;
        int DestTileInfo = GetTileInfo(_Dest);
        SetTileInfo(_Start, TileInfo & ~Obj);
        SetTileInfo(_Dest, DestTileInfo | Obj);

        if(Obj == OBJ_PLAYER)
        {
            PlayerPos.row = _Dest.row;
            PlayerPos.col = _Dest.col;
        }
    }
    private boolean PlayerMove(RowCol _Dir)
    {
        return AttemptMove(OBJ_PLAYER, PlayerPos, _Dir);
    }

    private boolean AttemptMove(int _Obj, RowCol _Start, RowCol _Dir)
    {
        int Obj = GetTileInfo(_Start) & MASK_OBJ;
        if(Obj != _Obj) return false;

        RowCol Dest = new RowCol(_Start.row + _Dir.row, _Start.col + _Dir.col);
        int Moveable = IsMoveable(Dest);
        if(Moveable == MOVEABLE_ABLE)
        {
            MoveObj(_Start, Dest);
            return true;
        }
        else if(Moveable == MOVEABLE_BALLEXIST)
        {
            if(_Obj == OBJ_PLAYER)
            {
                if(AttemptMove(OBJ_BALL, Dest, _Dir))
                {
                    return AttemptMove(OBJ_PLAYER, _Start, _Dir);
                }
            }
        }

        return false;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Map.size(); ++i)
        {
            for(int j = 0; j < Map.get(i).size(); ++j)
            {
                sb.append(toChar(Map.get(i).get(j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /*
    public String Desc()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("가로 크기 :%d\n", MaxCol));
        sb.append(String.format("세로 크기 :%d\n", MaxRow));
        sb.append(String.format("구멍의 수 :%d\n", TileCntArr[TILE_HALL]));
        sb.append(String.format("공의 수 :%d\n", TileCntArr[TILE_BALL]));
        sb.append(String.format("플레이어 위치 %d행 %d열\n", PlayerPos.row + 1, PlayerPos.col + 1));
        return sb.toString();
    }*/
}
