public enum DIR
{
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3),
    END(4);

    private final int value;

    DIR(int _value)
    {
        this.value = _value;
    }

    public int getValue()
    {
        return value;
    }
}
