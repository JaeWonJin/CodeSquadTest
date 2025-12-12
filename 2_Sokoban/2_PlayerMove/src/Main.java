import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
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

        while(true)
        {
            System.out.print("SOKOBAN> ");
            String cmd = sc.nextLine();
        }

    }
}