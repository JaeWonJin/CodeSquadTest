import java.util.Scanner;

public class Console
{
    public static void Tick()
    {
        Scanner sc = new Scanner(System.in);
        PaintBoard paintBoard = new PaintBoard();
        DrawInfo drawInfo = new DrawInfo();

        while(true)
        {
            System.out.print("명령을 입력하세요(help: 도움말) ");
            int Command = InputParser.GetInst().ProcessInput(sc.nextLine(), drawInfo);
            if(Command == InputParser.CMD_QUIT)
            {
                System.out.print("프로그램을 종료합니다.\n");
                break;
            }
            else if(Command == InputParser.CMD_WRONG_INPUT)
            {
                System.out.print("잘못 입력하셨습니다.\n");
            }
            else if(Command == InputParser.CMD_HELP)
            {
                System.out.print(InputParser.GetInst().sbDesc.toString());
            }
            else if(Command == InputParser.CMD_DRAW_LINE)
            {
                boolean bDrawSucess = paintBoard.DrawLine(new Vec2(drawInfo.vecCoords.get(0), drawInfo.vecCoords.get(1)),
                        new Vec2(drawInfo.vecCoords.get(2), drawInfo.vecCoords.get(3)),
                        drawInfo.c);
                if(bDrawSucess) System.out.print(paintBoard.toString());
                else System.out.print("잘못 입력하셨습니다.\n");
            }
            else if(Command == InputParser.CMD_DRAW_RECT)
            {
                boolean bDrawSucess = paintBoard.DrawRect(new Vec2(drawInfo.vecCoords.get(0), drawInfo.vecCoords.get(1)),
                        new Vec2(drawInfo.vecCoords.get(2), drawInfo.vecCoords.get(3)),
                        drawInfo.c);
                if(bDrawSucess) System.out.print(paintBoard.toString());
                else System.out.print("잘못 입력하셨습니다.\n");
            }
            else if(Command == InputParser.CMD_DRAW_CIRCLE)
            {
                boolean bDrawSucess = paintBoard.DrawCircle(new Vec2(drawInfo.vecCoords.get(0), drawInfo.vecCoords.get(1)),
                        drawInfo.vecCoords.get(2),
                        drawInfo.c);
                if(bDrawSucess) System.out.print(paintBoard.toString());
                else System.out.print("잘못 입력하셨습니다.\n");
            }
            else if(Command == InputParser.CMD_SAVE_BOARD)
            {
                boolean bSaveSucess = FileMgr.GetInst().Save(drawInfo.RelativePath, paintBoard);
                if(bSaveSucess) System.out.print("저장에 성공하셨습니다.\n");
                else System.out.print("잘못 입력하셨습니다.\n");
            }
            else if(Command == InputParser.CMD_LOAD_BOARD)
            {
                boolean bLoadSucess = FileMgr.GetInst().Load(drawInfo.RelativePath, paintBoard);
                if(bLoadSucess)
                {
                    System.out.print(paintBoard.toString());
                    System.out.print("로드에 성공하셨습니다.\n");
                }
                else System.out.print("잘못 입력하셨습니다.\n");
            }
        }
    }
}
