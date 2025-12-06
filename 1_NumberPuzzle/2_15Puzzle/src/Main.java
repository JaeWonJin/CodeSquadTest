import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    static int N, M = 4;
    static int[][] Board = new int[N][M];

    public static void Swap_Arr2D(int[][] _Arr2D, RowCol _RowCol1, RowCol _RowCol2)
    {
        int temp = _Arr2D[_RowCol1.row][_RowCol1.col];
        _Arr2D[_RowCol1.row][_RowCol1.col] = _Arr2D[_RowCol2.row][_RowCol2.col];
        _Arr2D[_RowCol2.row][_RowCol2.col] = temp;
    }

    public static int Find(int[][] _Arr2D, int _Row, int _Col, int _Value)
    {
        for(int Row = 0; Row < _Row; ++Row)
        {
            for(int Col = 0; Col < _Col; ++Col)
            {
                if(_Arr2D[Row][Col] == _Value) return toIndex(_Col, Row, Col);
            }
        }

        return -1;
    }

    public static RowCol toRowCol(int _MaxCol, int _Idx)
    {
        return new RowCol(_Idx / _MaxCol, _Idx % _MaxCol);
    }

    public static int toIndex(int _MaxCol, int _Row, int _Col) { return _Row * _MaxCol + _Col; }

    public static void Shuffle_Arr2D(int[][] _Arr2D, int _Row, int _Col)
    {
        Random random = new Random();
        int Length_1D = _Row * _Col;
        for(int i = Length_1D - 1; i > 0; --i)
        {
            int RandIdx = random.nextInt(i + 1);    // 0 ~ i
            if(i != RandIdx)
            {
                Swap_Arr2D(_Arr2D, toRowCol(_Col, i), toRowCol(_Col, RandIdx));
            }
        }
    }

    public static boolean IsAdjacent(RowCol _Start, RowCol _Dest)
    {
        int RowDiff = Math.abs(_Start.row - _Dest.row);
        int ColDiff = Math.abs(_Start.col - _Dest.col);

        if(RowDiff == 1 && ColDiff == 0) return true;
        if(RowDiff == 0 && ColDiff == 1) return true;
        return false;
    }

    public static boolean IsBoardCompleted(int[][] _Board, int _Row, int _Col)
    {
        int Index_1D = 1;
        for(int Row = 0; Row < N; ++Row)
        {
            for(int Col = 0; Col < M; ++Col)
            {
                if(Board[Row][Col] != Index_1D)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int N = 4, M = 4;
        int[][] Board = new int[N][M];
        int Max1DIdx = N * M;
        int CurPos_1D = 1;
        {
            int Index_1D = 1;
            for(int Row = 0; Row < N; ++Row)
            {
                for(int Col = 0; Col < M; ++Col)
                {
                    Board[Row][Col] = Index_1D;
                    ++Index_1D;
                }
            }
            Shuffle_Arr2D(Board, N, M);
            // 테스트 코드 Swap_Arr2D(Board, toRowCol(M, 15), toRowCol(M, 14));

            for(int Row = 0; Row < N; ++Row)
            {
                for(int Col = 0; Col < M; ++Col)
                {
                    if(Board[Row][Col] == Max1DIdx)
                    {
                        CurPos_1D = toIndex(M, Row, Col);
                    }
                }
            }
        }
        int Turn = 1;
        boolean bTurnChanged = true;
        System.out.print("재미있는 15 퍼즐!\n\n");
        while(true)
        {
            if(bTurnChanged)
            {
                System.out.printf("Turn %d\n", Turn);
                for(int Row = 0; Row < N; ++Row)
                {
                    for(int Col = 0; Col < M; ++Col)
                    {
                        if(Board[Row][Col] != Max1DIdx) System.out.printf("[%2d]", Board[Row][Col]);
                        else System.out.print("[  ]");
                    }
                    System.out.print("\n");
                }

                // 현재 빈칸의 위치가 마지막 칸일 경우에만 퍼즐이 완성되었는지 검사한다.
                if(CurPos_1D == Max1DIdx - 1 && IsBoardCompleted(Board, N, M))
                {
                    System.out.printf("축하합니다! %d턴만에 퍼즐을 완성하셨습니다!", Turn);
                    break;
                }
                ++Turn;
            }

            System.out.print("숫자 입력>");
            int Output = sc.nextInt();
            int DestIdx_1D = Find(Board, N, M, Output);

            if(DestIdx_1D >= 0 && DestIdx_1D < Max1DIdx)
            {
                RowCol Start = toRowCol(M, CurPos_1D);
                RowCol Dest = toRowCol(M, DestIdx_1D);
                if(IsAdjacent(Start, Dest))
                {
                    Swap_Arr2D(Board, Start, Dest);
                    CurPos_1D = DestIdx_1D;

                    bTurnChanged = true;
                }
                else
                {
                    System.out.print("인접한 타일을 선택해야 합니다. 다시 입력하세요.\n");
                    bTurnChanged = false;
                }
            }
            else
            {
                System.out.print("잘못된 입력입니다. 다시 입력하세요.\n");
                bTurnChanged = false;
            }
        }

    }
}