
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
        boolean bCompleted = false;
        System.out.print("재미있는 2048 퍼즐!\n\n");
        while(true)
        {
            if(bTurnChanged)
            {
                // Todo : 게임 종료 조건 검사
                if(bCompleted)
                {
                    System.out.printf("축하합니다! %d턴만에 퍼즐을 완성하셨습니다!", Turn);
                    break;
                }
                board.UpdateVoidTiles();
                board.SpawnRandomTile();

                // 게임 패배 조건 검사
                if(!board.CanMove())
                {
                    System.out.printf("더이상 움직일 수 없습니다. 패배하셨습니다. (%d턴)", Turn);
                    break;
                }

                System.out.printf("Turn %d\n", Turn);
                System.out.print(board.toString());

                ++Turn;
            }

            System.out.print("WASD 입력>");
            String strOutput = sc.nextLine();
            DIR Dir = DIR.END;
            switch(strOutput)
            {
                case "W":
                {
                    Dir = DIR.UP;
                    break;
                }
                case "A":
                {
                    Dir = DIR.LEFT;
                    break;
                }
                case "S":
                {
                    Dir = DIR.DOWN;
                    break;
                }
                case "D":
                {
                    Dir = DIR.RIGHT;
                    break;
                }
                default:
                {
                    bTurnChanged = false;
                    System.out.print("잘못된 입력입니다. 다시 입력하세요.\n");
                    break;
                }
            }
            if(Dir != DIR.END)
            {
                bTurnChanged = true;
                board.Slide(Dir);
                bCompleted = board.Merge(Dir);
                board.Slide(Dir);
            }

        }
    }
}