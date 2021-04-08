import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the path to the python interpreter");
        Scanner in = new Scanner(System.in);
        String pathToPythonExecutable = in.nextLine();
        String result = new PythonCommand(pathToPythonExecutable).call();
        System.out.print(result);
    }
}
