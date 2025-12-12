import java.util.*;

public class Console
{
    public static void Tick()
    {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> StrMap = new ArrayList<>(
                Arrays.asList(
                        "  #######",
                        "###  O  ###",
                        "#    o    #",
                        "# Oo P oO #",
                        "###  o  ###",
                        " #   O  # ",
                        " ########"
                )
        );
        Stage stage = new Stage(StrMap, 7, 11);
        System.out.print(stage.toString());
        System.out.print("\n");
        while(true)
        {
            System.out.print("SOKOBAN> ");

            Deque<Character> OrderQueue = new ArrayDeque<>();
            String cmd = sc.nextLine();
            for(int i = 0; i < cmd.length(); ++i)
            {
                char c = cmd.charAt(i);
                // 대소문자 모두 처리 (입력을 소문자로 통일)
                c = Character.toLowerCase(c);
                OrderQueue.addLast(c);

            }
            int result = 0;
            while(!OrderQueue.isEmpty())
            {
                char c = OrderQueue.getFirst();
                OrderQueue.removeFirst();

                result = stage.ExecuteOrder(c);
                if(result == Stage.ORDER_QUIT)
                {
                    System.out.print("Bye~\n");
                    break;
                }
                else if(result == Stage.ORDER_MOVE_FAIL)
                {
                    System.out.print(stage.toString());
                    System.out.print("\n");
                    System.out.printf("%c: (경고!) 해당 명령을 수행할 수 없습니다!\n", Character.toUpperCase(c));
                }
                else if(result == Stage.ORDER_MOVE_OK)
                {
                    System.out.print(stage.toString());
                    System.out.print("\n");
                }
            }
            if(result == Stage.ORDER_QUIT)
            {
                break;
            }
        }
    }
}
