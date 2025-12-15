package P1;

import java.util.ArrayList;

public class P1_GameMgr
{
    private static ArrayList<Integer> PlayerCards = new ArrayList<>();
    private static ArrayList<Integer> DealerCards = new ArrayList<>();
    private static int Wins     = 0;
    private static int Loses    = 0;
    private static int Draws    = 0;
    private static int Turn     = 0;

    public static void PlayTurn()
    {
        P1_Deck deck = new P1_Deck();
        int PCard = deck.Sample();
        int DealerCard = deck.Sample();
        PlayerCards.add(PCard);
        DealerCards.add(DealerCard);
        if(PCard > DealerCard) ++Wins;
        else if(PCard < DealerCard) ++Loses;
        else ++Draws;
        ++Turn;
    }

    public static String Desc()
    {
        StringBuilder sb = new StringBuilder();
        if(Turn == 0) return "";

        sb.append(String.format("Game %d\n", Turn));
        sb.append("You\t:");
        for(int i = 0; i < PlayerCards.size(); ++i) sb.append(String.format(" [%2d]", PlayerCards.get(i)));
        sb.append("\n");
        sb.append("Dealer\t:");
        for(int i = 0; i < DealerCards.size(); ++i) sb.append(String.format(" [%2d]", DealerCards.get(i)));
        sb.append("\n");
        if(PlayerCards.getLast() > DealerCards.getLast())
        {
            sb.append("당신이 이겼습니다.\n");
        }
        else if(PlayerCards.getLast() < DealerCards.getLast())
        {
            sb.append("딜러가 이겼습니다.\n");
        }
        else
        {
            sb.append("비겼습니다.\n");
        }

        sb.append("현재 전적:");
        sb.append(String.format(" %d승", Wins));
        if(Draws > 0) sb.append(String.format(" %d무", Draws));
        sb.append(String.format(" %d패\n", Loses));

        return sb.toString();
    }
}
