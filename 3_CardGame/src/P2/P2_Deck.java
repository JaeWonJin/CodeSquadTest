package P2;

import java.util.ArrayList;
import java.util.Random;

public class P2_Deck
{
    private ArrayList<Integer> DeckCards = new ArrayList<>();

    public void Init()
    {
        // 10장 이하일 경우에만 새로운 덱 가져옴
        if(DeckCards.size() > 10) return;
        DeckCards.clear();
        for(int i = 1; i <= 10; ++i)
        {
            for(int j = 0; j < 4; ++j) DeckCards.add(i);
        }
        for(int i = 0; i < 12; ++i)
        {
            DeckCards.add(11);
        }
        Shuffle();
    }

    private void Shuffle()
    {
        for(int i = DeckCards.size() - 1; i > 0; --i)
        {
            Random random = new Random();
            int randIdx = random.nextInt(i + 1);        // 0 ~ i
            if(randIdx != i)
            {
                int temp = DeckCards.get(randIdx);
                DeckCards.set(randIdx, DeckCards.get(i));
                DeckCards.set(i, temp);
            }
        }
    }

    public int ExtractCard()
    {
        int card = DeckCards.getLast();
        DeckCards.removeLast();
        return card;
    }

    public String CheatDesc()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("덱의 카드 ");
        for(int i = DeckCards.size() - 1; i >= DeckCards.size() - 6; --i)
        {
            sb.append(String.format("[%d]", DeckCards.get(i)));
        }
        sb.append("\n");

        return sb.toString();
    }
}
