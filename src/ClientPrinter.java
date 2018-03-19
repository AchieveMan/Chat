import java.io.BufferedReader;
import java.io.IOException;

public class ClientPrinter extends Thread {

    private BufferedReader bufferedReader;
    private boolean close;

    ClientPrinter(BufferedReader br) {
        bufferedReader = br;
        close = false;
    }

    @Override
    public void run() {
        super.run();
        String str;
        while (!close) {
            try {
                str = bufferedReader.readLine();
                System.out.println(str);
            }
            catch (IOException e) {

            }
        }
    }

    public void close() {
        close = true;
    }
}
