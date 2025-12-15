package P1;

import java.util.Scanner;

public class P1_Console
{
    static final int ORDER_INVALID  = 0;
    static final int ORDER_YES      = 1;
    static final int ORDER_NO       = 2;
    public static int ParseOrder(String _Input)
    {
        _Input = _Input.toLowerCase();
        if(_Input.length() != 1) return ORDER_INVALID;
        if(_Input.charAt(0) == 'y') return ORDER_YES;
        if(_Input.charAt(0) == 'n') return ORDER_NO;
        return ORDER_INVALID;
    }

    public static void Tick()
    {
        Scanner sc = new Scanner(System.in);

        boolean bPlayTurn = true;
        System.out.print("간단 카드 게임을 시작합니다.\n\n");
        while(true)
        {
            if(bPlayTurn)
            {
                P1_GameMgr.PlayTurn();
                System.out.print(P1_GameMgr.Desc());
            }
            System.out.print("한 게임 더 하시겠습니까? (Y/N) ");
            String strInput = sc.nextLine();
            int result = ParseOrder(strInput);
            if(result == ORDER_NO)
            {
                System.out.print("게임을 종료합니다.\n플레이해주셔서 감사합니다.\n");
                break;
            }
            else if(result == ORDER_INVALID)
            {
                System.out.print("잘못 입력하셨습니다.\n");
                bPlayTurn = false;
            }
            else
            {
                System.out.print("\n");
                bPlayTurn = true;
            }
        }
    }

}
