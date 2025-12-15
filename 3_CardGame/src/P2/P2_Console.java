package P2;

import java.util.Scanner;

public class P2_Console
{
    static final int ORDER_INVALID  = 0;
    static final int ORDER_YES      = 1;
    static final int ORDER_NO       = 2;
    static final int ORDER_SHOWDECK = 3;
    public static int ParseOrder(String _Input)
    {
        _Input = _Input.toLowerCase();
        if(_Input.equals("codesquad")) return ORDER_SHOWDECK;
        if(_Input.length() != 1) return ORDER_INVALID;
        if(_Input.charAt(0) == 'y') return ORDER_YES;
        if(_Input.charAt(0) == 'n') return ORDER_NO;
        return ORDER_INVALID;
    }
    public static int ParseMoney(String _Input)
    {
        int money = 0;
        for(int i = 0; i < _Input.length(); ++i)
        {
            char c = _Input.charAt(i);
            if(c >= '0' && c <= '9')
            {
                money = money * 10 + (c - '0');
            }
            else
            {
                return 0;
            }
        }
        if(money > P2_GameMgr.GetMoney()) money = ORDER_INVALID;
        // 베팅은 100원 단위로 입력
        if(money % 100 != 0) money = ORDER_INVALID;
        return money;
    }

    public static void Tick()
    {
        Scanner sc = new Scanner(System.in);
        boolean bPrintDesc = true;
        System.out.printf("간단 카드 게임을 시작합니다.\n현재 재산: %d\n", P2_GameMgr.GetMoney());
        while(true)
        {
            if(bPrintDesc)
            {
                if(P2_GameMgr.GetState() != P2_GameMgr.GAMESTATE_BET_INPUT)
                {
                    System.out.print(P2_GameMgr.PrevDesc_Once());
                }
            }
            System.out.print(P2_GameMgr.PrevDesc_Repeat());
            String strInput = sc.nextLine();
            int result = ORDER_INVALID;
            if(P2_GameMgr.GetState() == P2_GameMgr.GAMESTATE_BET_INPUT)
            {
                result = ParseMoney(strInput);
            }
            else
            {
                result = ParseOrder(strInput);
            }
            if(result == ORDER_INVALID)
            {
                System.out.print("잘못 입력하셨습니다.\n");
                bPrintDesc = false;
                continue;
            }
            else
            {
                P2_ProcessResult processResult = P2_GameMgr.ProcessInput(result);
                bPrintDesc = processResult.bPrintDesc;
                System.out.print(processResult.Desc());
                if(processResult.gameResult == P2_ProcessResult.PROCESS_ENDGAME)
                {
                    System.out.print(P2_GameMgr.EndGameDesc());
                    break;
                }
            }
        }

    }
}
