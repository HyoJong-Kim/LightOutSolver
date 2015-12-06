import java.util.Scanner;

/**
 * Created by Answer on 2015-12-06.
 */
public class LightOutSolver {

    static int n;
    static int maxCount;
    static boolean[][] board;
    static boolean[] check;
    static boolean[] solution;
    static boolean hasSolution = false;

    public static boolean isSolved() {
        for(int i = 0; i < n; i++) {
            for(int k = 0; k < n; k++) {
                if(board[i][k] != false)
                    return false;
            }
        }
        return true;
    }

    public static boolean promising(int index) {

        if(index == -1)
            return true;

        /* press */
        if(check[index]) {
            pressButton(index);
            if(checkUpperRow(index))
                return false;
            else
                return true;
        }
        /* do not press */
        else {
            if(checkUpperRow(index))
                return false;
            else
                return true;
        }
    }

    public static void lightsOut(int index) {
        int count;
        if(index < n*n) {
        /* If it is promising */

            if (promising(index)) {
            /* and it is unsolved go deeper*/
                if (!isSolved()) {
                    try {
                        check[index + 1] = true;
                        lightsOut(index + 1);
                        /* unPress */
                        pressButton(index + 1);
                        check[index + 1] = false;
                        lightsOut(index + 1);
                    } catch (ArrayIndexOutOfBoundsException e)
                    {}
                }
                /* if it is solved, compare the previous solution with new solution */
                else {
                    hasSolution = true;
                    count = countSolution(check);
                    if (count <= maxCount) {
                        solution = check.clone();
                        maxCount = count;
                    }
                }
            }
        }
    }

    public static int countSolution(boolean[] check) {
        int count = 0;
        for(int i = 0; i < n*n; i++) {
            if(check[i])
                count++;
        }
        return count;
    }

    public static void printSolution() {
        if(hasSolution) {
            for(int i = 0; i < n*n; i++) {
                if(i % n == n-1) {
                    if(solution[i])
                        System.out.println("O");
                    else
                        System.out.println("#");
                }
                else {
                    if(solution[i])
                        System.out.print("O ");
                    else
                        System.out.print("# ");
                }
            }
        }
        else {
            System.out.println("No Solution!!");
        }
    }

    public static void pressButton(int index) {
        int x = index / n;
        int y = index % n;
        board[x][y] = !board[x][y];

        if(x - 1 >= 0)
            board[x-1][y] = !board[x-1][y];
        if(y - 1 >= 0)
            board[x][y-1] = !board[x][y-1];
        if(x + 1 < n)
            board[x+1][y] = !board[x+1][y];
        if(y + 1 < n)
            board[x][y+1] = !board[x][y+1];

    }

    public static boolean checkUpperRow(int index) {
        int x = index / n;
        int y = index % n;

        if(x == 0)
            return false;
        else
            return board[x-1][y];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String state;

        n = scanner.nextInt();
        check = new boolean[n*n];
        board = new boolean[n][n];
        maxCount = n*n;
        for(int i = 0; i < n; i++) {
            for(int k = 0; k < n; k++) {
                state = scanner.next();
                if(state.equals("#"))
                    board[i][k] = false;
                else if(state.toLowerCase().equals("o"))
                    board[i][k] = true;
            }
        }

        lightsOut(-1);
        printSolution();
    }
}
