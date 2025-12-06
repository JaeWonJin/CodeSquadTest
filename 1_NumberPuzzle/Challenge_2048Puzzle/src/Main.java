
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Board board = new Board(4, 4);
        board.Init();

        int Turn = 1;
        boolean bTurnChanged = true;
        System.out.print("재미있는 2048 퍼즐!\n\n");
        while(true)
        {
            if(bTurnChanged)
            {
                board.SpawnRandomTile();

                System.out.printf("Turn %d\n", Turn);
                System.out.print(board.toString());

                // Todo : 게임 종료 조건 검사
                ++Turn;
            }

            System.out.print("WASD 입력>");
            String strOutput = sc.nextLine();
            boolean bInValidOutput = false;
            switch(strOutput)
            {
                case "W":
                {
                    bTurnChanged = true;
                    break;
                }
                case "A":
                {
                    bTurnChanged = true;
                    break;
                }
                case "S":
                {
                    bTurnChanged = true;
                    break;
                }
                case "D":
                {
                    bTurnChanged = true;
                    break;
                }
                default:
                {
                    bInValidOutput = true;
                    bTurnChanged = false;
                    System.out.print("잘못된 입력입니다. 다시 입력하세요.\n");
                    break;
                }
            }

        }
    }
}