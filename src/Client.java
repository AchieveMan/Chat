import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private Scanner scanner;
    private Socket socket;
    private boolean close;
    private String name;
    private String sendTo;
    private BufferedReader in;
    private PrintWriter out;
    private ClientPrinter clientPrinter;

    Client(String name) throws IOException {
        this.name = name;
        scanner = new Scanner(System.in);
        socket = new Socket("localhost", 1997);
        sendTo = "All";
    }

    @Override
    public void run() {
        super.run();
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clientPrinter = new ClientPrinter(in);
            clientPrinter.start();
            out.println(name);
            out.println(sendTo);
        }
        catch (IOException e) {

        }
        while (!close) {
            String str = scanner.next();
            if (str.equals("change")) {
                out.println(str);
                System.out.println("Кому отправлять?");
                sendTo = scanner.next();
                out.println(sendTo);
            }
            else {
                out.println(name + ":" + str);
            }
            if (str.equals("close")) {
                out.println(str);
                close = true;
            }
        }
        close();
    }

    public void close() {
        try {
            clientPrinter.close();
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {

        }
    }
}