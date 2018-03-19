import java.io.IOException;
import java.util.Scanner;

public class StartClient {

    public static void main(String[] args) throws IOException {
        System.out.print("Введите никнейм: ");
        new Client(new Scanner(System.in).next()).start();
    }

}