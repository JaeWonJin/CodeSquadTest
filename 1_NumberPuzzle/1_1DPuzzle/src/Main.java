import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    static int N = 8;
    static int[] Board = new int[N];
    static
    {
        for(int i = 0; i < N; ++i) Board[i] = i + 1;
    }

    public static void Swap_Arr(int[] _Arr, int _i, int _j)
    {
        int temp = _Arr[_i];
        _Arr[_i] = _Arr[_j];
        _Arr[_j] = temp;
    }

    public static void Shuffle_Arr(int[] _Arr, int _N)
    {
        Random random = new Random();
        for(int i = _N - 1; i >= 1; --i)
        {
            int RandIdx = random.nextInt(i + 1);                // 0 ~ i
            if(i != RandIdx) Swap_Arr(_Arr, i, RandIdx);
        }
    }

    public static boolean CheckBoardFinished(int[] _Board, int _N)
    {
        for(int i = 0; i < _N; ++i)
        {
            if(_Board[i] != i + 1) return false;
        }
        return true;
    }

    public static boolean ParseInt(String _Str, ArrayList<Integer> _Out)
    {
        boolean bReadingNumber = false;
        int Value = 0;
        int Length = _Str.length();
        for(int i = 0; i < Length; ++i)
        {
            char c = _Str.charAt(i);
            if(c >= '0' && c <= '9')
            {
                bReadingNumber = true;
                Value = Value * 10 + (c - '0');

                // 범위 초과 체크
                if(Value > N || Value <= 0) return false;
            }
            else
            {
                if(c == ',')
                {
                    // 숫자 읽는 중이 아닐 경우
                    if(!bReadingNumber) return false;

                    _Out.add(Value);
                    Value = 0;
                    bReadingNumber = false;

                    // 숫자 2개 초과
                    if(_Out.size() > 1) return false;

                    // 콤마 뒤 공백은 허용
                    if(i + 1 < Length && _Str.charAt(i + 1) == ' ') ++i;
                }
                // 콤마, 콤마 + 공백이 아닌 경우 실패
                else
                {
                    return false;
                }
            }
        }
        // 마지막 숫자 저장
        if(!bReadingNumber) return false;
        _Out.add(Value);

        return true;
    }

    public static void main(String[] args)
    {
        int Turn = 1;
        boolean bTurnChanged = true;
        Scanner sc = new Scanner(System.in);
        Shuffle_Arr(Board, N);
        System.out.print("간단 숫자 퍼즐\n");
        while(true)
        {
            ArrayList<Integer> vecOutput = new ArrayList<>();
            if(bTurnChanged)
            {
                System.out.printf("Turn %d\n", Turn);
                System.out.print("[");
                for (int i = 0; i < N; ++i)
                {
                    System.out.printf("%d", Board[i]);
                    if(i < N - 1) System.out.print(", ");
                }
                System.out.print("]\n");
            }

            System.out.print("교환할 두 숫자를 입력>\n");
            String Input = sc.nextLine();
            if (ParseInt(Input, vecOutput))
            {
                Swap_Arr(Board, vecOutput.get(0) - 1, vecOutput.get(1) - 1);
                if(CheckBoardFinished(Board, N))
                {
                    System.out.printf("축하합니다! %d턴만에 퍼즐을 완성하셨습니다!", Turn);
                    break;
                }
                ++Turn;
                bTurnChanged = true;
            }
            else
            {
                System.out.print("잘못 입력하셨습니다. 다시 입력해 주세요.\n");
                bTurnChanged = false;
            }
        }
    }
}