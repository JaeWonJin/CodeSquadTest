public class Vec2
{
    private int x;
    private int y;
    private float x_fx;
    private float y_fx;

    public int GetX() { return x; }
    public int GetY() { return y; }
    public float GetX_fx(){ return x_fx;}
    public float GetY_fx() {return y_fx; }
    public Vec2(int _x, int _y)
    {
        x = _x;
        y = _y;
        x_fx = _x;
        y_fx = _y;
    }

    public Vec2(float _x, float _y)
    {
        x = (int) _x;
        y = (int) _y;
        x_fx = _x;
        y_fx = _y;
    }

    public float Length(){ return (float) Math.sqrt(x_fx * x_fx + y_fx * y_fx); }

    public void Set(float _x, float _y)
    {
        x = (int) _x;
        y = (int) _y;
        x_fx = _x;
        y_fx = _y;
    }

    public void Divide(float _f)
    {
        if(_f != 0.f)
        {
            Set(x_fx / _f, y_fx / _f);
        }
    }

    public void Normalize()
    {
        float leng = Length();

        Set(x_fx / leng, y_fx / leng);
    }

    public Vec2 Add(Vec2 _Other)
    {
        return new Vec2(x_fx + _Other.x_fx, y_fx + _Other.y_fx);
    }
}
