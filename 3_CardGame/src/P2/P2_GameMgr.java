package P2;

import java.util.ArrayList;

import static P2.P2_Console.ORDER_SHOWDECK;
import static P2.P2_Console.ORDER_YES;
import static P2.P2_Console.ORDER_NO;

public class P2_GameMgr
{
    private static ArrayList<Integer> PlayerCards = new ArrayList<>();
    private static ArrayList<Integer> DealerCards = new ArrayList<>();
    private static int Wins     = 0;
    private static int Loses    = 0;
    private static int Draws    = 0;
    private static int Turn     = 0;
    private static int Money    = 1000;
    private static int BettedMoney = 0;
    private static P2_Deck deck = new P2_Deck();

    public static final int GAMESTATE_BET_INPUT         = 0;
    public static final int GAMESTATE_PLAYER_DECISION   = 1;
    public static final int GAMESTATE_CONTINUE_GAME     = 2;
    public static final int GAMESTATE_END_GAME          = 3;
    private static int State    = GAMESTATE_BET_INPUT;

    public static int GetState() { return State; }
    public static void SetState(int _State)
    {
        if(State == GAMESTATE_BET_INPUT) ++Turn;
        State = _State;
    }

    public static int GetMoney(){ return Money; }

    public static void SetNewGame()
    {
        deck.Init();
        PlayerCards.clear();
        DealerCards.clear();
        BettedMoney = 0;
    }

    static final int MATCH_PLAYERLOSE   = 0;
    static final int MATCH_PLAYERWIN    = 1;
    static final int MATCH_PLAYERDRAW   = 2;
    static final int MATCH_PLAYER_SCORE_EXCEED  = 3;
    public static int ProcessMatch(boolean _bEndMatch)
    {
        int PlayerScore = GetScore(PlayerCards);
        int DealderScore = GetScore(DealerCards);
        int MatchResult = MATCH_PLAYERDRAW;

        if(PlayerScore >= 22) MatchResult = MATCH_PLAYER_SCORE_EXCEED;
        else if(DealderScore >= 22) MatchResult = MATCH_PLAYERWIN;
        else if(PlayerScore > DealderScore) MatchResult = MATCH_PLAYERWIN;
        else if(PlayerScore < DealderScore) MatchResult = MATCH_PLAYERLOSE;
        else if(DealderScore == 21) MatchResult = MATCH_PLAYERLOSE;

        if(_bEndMatch)
        {
            if(MatchResult == MATCH_PLAYERWIN)
            {
                if(PlayerScore == 21) Money += BettedMoney;
                Money += BettedMoney;
                ++Wins;
            }
            else if(MatchResult == MATCH_PLAYERLOSE)
            {
                Money -= BettedMoney;
                ++Loses;
            }
            else ++Draws;
            BettedMoney = 0;
        }

        // 플레이어 점수 21점 초과시 게임 종료가 아니어도 강제 종료시킴(배팅 결과 반영시킴)
        if(MatchResult == MATCH_PLAYER_SCORE_EXCEED)
        {
            Money -= BettedMoney;
            BettedMoney = 0;
            ++Loses;
        }

        return MatchResult;
    }

    public static P2_ProcessResult ProcessInput(int _Result)
    {
        StringBuilder sb = new StringBuilder();
        int ProcessContinue = P2_ProcessResult.PROCESS_CONTINUE;
        boolean bPrintDesc = true;
        int MatchResult = 0;
        if(State == GAMESTATE_BET_INPUT)
        {
            SetNewGame();
            DrawCard();
            BettedMoney = _Result;
            SetState(GAMESTATE_PLAYER_DECISION);
        }
        else if(State == GAMESTATE_PLAYER_DECISION)
        {
            if(_Result == ORDER_SHOWDECK)
            {
                sb.append(deck.CheatDesc());
                bPrintDesc = false;
            }
            else if(_Result == ORDER_YES)
            {
                DrawCard();
                // 카드를 더 받아서 플레이어 카드가 22점 이상 되어버리면 게임 패배
                MatchResult = ProcessMatch(false);
                if(MatchResult == MATCH_PLAYER_SCORE_EXCEED)
                {
                    sb.append(String.format("당신의 패배입니다.\n"));
                    SetState(GAMESTATE_CONTINUE_GAME);
                }
            }
            else if(_Result == ORDER_NO)
            {
                // 딜러와 점수 비교를 통해 승리, 패배 결정
                // 21점으로 승리할 경우 배팅한 금액의 두배를 돌려받음
                MatchResult = ProcessMatch(true);
                sb.append(DealerCardDesc());
                if(MatchResult == MATCH_PLAYERWIN)
                {
                    sb.append(String.format("당신의 승리입니다\n"));
                }
                else if(MatchResult == MATCH_PLAYERLOSE || MatchResult == MATCH_PLAYERLOSE)
                {
                    sb.append(String.format("당신의 패배입니다.\n"));
                }
                else
                {
                    sb.append(String.format("무승부 입니다.\n"));
                }
                SetState(GAMESTATE_CONTINUE_GAME);
            }
            // 게임 결과 자산이 0원 이하일시 게임 종료
            if(Money <= 0)
            {
                ProcessContinue = P2_ProcessResult.PROCESS_ENDGAME;
                SetState(GAMESTATE_END_GAME);
            }
        }
        else if(State == GAMESTATE_CONTINUE_GAME)
        {
            if(_Result == ORDER_YES)
            {
                ProcessContinue = P2_ProcessResult.PROCESS_CONTINUE;
                SetNewGame();
                SetState(GAMESTATE_BET_INPUT);
            }
            else if(_Result == ORDER_NO)
            {
                ProcessContinue = P2_ProcessResult.PROCESS_ENDGAME;
                SetState(GAMESTATE_END_GAME);
            }
        }
        return new P2_ProcessResult(ProcessContinue, sb.toString(), bPrintDesc);
    }

    // GameState에 따라 다른 텍스트 출력
    // Input 받기 전에 출력할 텍스트
    // 잘못된 출력일시, 반복해서 또 출력하지는 않는 텍스트
    public static String PrevDesc_Once()
    {
        StringBuilder sb = new StringBuilder();
        if(State == GAMESTATE_BET_INPUT)
        {

        }
        else if(State == GAMESTATE_PLAYER_DECISION)
        {
            sb.append(String.format("Game %d\n", Turn));
            sb.append(PlayerCardDesc());
        }
        else if(State == GAMESTATE_CONTINUE_GAME)
        {
            sb.append(String.format("현재 남은 자산: %d\n", Money));
        }
        return sb.toString();
    }

    public static String PrevDesc_Repeat()
    {
        StringBuilder sb = new StringBuilder();
        if(State == GAMESTATE_BET_INPUT)
        {
            sb.append("얼마를 거시겠습니까? ");
        }
        else if(State == GAMESTATE_PLAYER_DECISION)
        {
            sb.append("카드를 더 받겠습니까? (Y / N) ");
        }
        else if(State == GAMESTATE_CONTINUE_GAME)
        {
            sb.append("한 게임 더 하시겠습니까? (Y / N) ");
        }
        return sb.toString();
    }

    private static String CardsDesc(ArrayList<Integer> _Cards)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < _Cards.size(); ++i) sb.append(String.format("[%d]", _Cards.get(i)));
        sb.append("\n");
        return sb.toString();
    }

    private static String PlayerCardDesc()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("플레이어: ");
        sb.append(CardsDesc(PlayerCards));
        int Sum = 0;
        for(int i = 0; i < PlayerCards.size(); ++i) Sum += PlayerCards.get(i);
        sb.append(String.format("총합 : %d\n", Sum));
        return sb.toString();
    }

    private static String DealerCardDesc()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("딜러: ");
        sb.append(CardsDesc(DealerCards));
        sb.append(String.format("딜러의 카드 합계는 %d입니다.", GetScore(DealerCards)));
        return sb.toString();
    }

    public static String EndGameDesc()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d승 %d무 %d패로 %d원의 자산이 남았습니다.\n플레이 해 주셔서 감사합니다.\n", Wins, Draws, Loses, Money));
        return sb.toString();
    }

    private static int GetScore(ArrayList<Integer> _Cards)
    {
        int Score = 0;
        for(int i = 0; i < _Cards.size(); ++i) Score += _Cards.get(i);
        return Score;
    }

    // 플레이어와 딜러에게 카드 분배.
    // 딜러는 17 이상일 경우 카드를 받지 않는다.
    public static void DrawCard()
    {
        PlayerCards.add(deck.ExtractCard());
        if(GetScore(DealerCards) < 17)
        {
            DealerCards.add(deck.ExtractCard());
        }
    }


}
