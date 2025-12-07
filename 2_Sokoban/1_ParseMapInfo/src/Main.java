import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        ArrayList<Stage> vecStages = new ArrayList<>();
        ArrayList<String> StrMap = new ArrayList<>();
        int MaxCol = 0;

        while (sc.hasNextLine())
        {
            String strInput = sc.nextLine();

            if (strInput.isEmpty()) break;

            if (strInput.startsWith("Stage "))
            {
                // 이전 스테이지가 있으면 먼저 저장
                if (!StrMap.isEmpty()) {
                    vecStages.add(new Stage(StrMap, StrMap.size(), MaxCol));
                    StrMap = new ArrayList<>();
                    MaxCol = 0;
                }

                // Stage 번호 파싱
                String numPart = strInput.substring("Stage ".length()).trim();
                // int StageNum = Integer.parseInt(numPart) - 1;
            }
            else if (strInput.startsWith("="))
            {
                // 스테이지 구분자에서도 저장
                if (!StrMap.isEmpty()) {
                    vecStages.add(new Stage(StrMap, StrMap.size(), MaxCol));
                    StrMap = new ArrayList<>();
                    MaxCol = 0;
                }
            }
            else
            {
                StrMap.add(strInput);
                if (MaxCol < strInput.length()) MaxCol = strInput.length();
            }
        }

        // 마지막 스테이지 처리
        if (!StrMap.isEmpty())
        {
            vecStages.add(new Stage(StrMap, StrMap.size(), MaxCol));
        }

        // 출력
        for (int i = 0; i < vecStages.size(); ++i)
        {
            Stage CurStage = vecStages.get(i);
            System.out.printf("Stage %d\n\n", i + 1);
            System.out.print(CurStage.toString());
            System.out.print("\n");
            System.out.print(CurStage.Desc());
            System.out.print("\n");
        }
    }
}