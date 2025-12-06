public class RowCol
{
    public int row;
    public int col;

    public RowCol(int _Row, int _Col)
    {
        row = _Row;
        col = _Col;
    }

    public RowCol add(RowCol _Other)
    {
        return new RowCol(row + _Other.row, col + _Other.col);
    }
    public void copy(RowCol _Src)
    {
        row = _Src.row;
        col = _Src.col;
    }

    @Override
    public boolean equals(Object _Obj)
    {
        if (this == _Obj) return true;
        if (_Obj == null || getClass() != _Obj.getClass()) return false;

        RowCol rowCol = (RowCol) _Obj;
        return row == rowCol.row && col == rowCol.col;
    }
}