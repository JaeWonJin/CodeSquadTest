public class CoordUtil
{
    public static RowCol toRowCol(int _MaxCol, int _Idx)
    {
        return new RowCol(_Idx / _MaxCol, _Idx % _MaxCol);
    }
    public static int toIndex(int _MaxCol, int _Row, int _Col) { return _Row * _MaxCol + _Col; }
}
