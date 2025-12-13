import java.util.Random;

public class P1_Deck
{
    public int Sample()
    {
        Random random = new Random();
        return (random.nextInt(11) + 1);      // 1 ~ 11
    }
}
