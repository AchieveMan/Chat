import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

    private ServerSocket serverSocket;
    ArrayList<Connection> connections;

    Server() throws IOException {
        serverSocket = new ServerSocket(1997);
    }

    @Override
    public void run() {
        super.run();
        connections = new ArrayList<>();
        try {
            while (true) {
                Connection connection = new Connection(serverSocket.accept());
                connection.start();
                connections.add(connection);
                while (connection.returnName() == null) {
                    if (connection.isInterrupted()) {
                        break;
                    }
                }
                System.out.println(connection.returnName() + " is connected!");
                System.out.println(connections.size() + " users is active!");
            }
        }
        catch (IOException e) {

        }
    }

    public class Connection extends Thread {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String name;
        private String nameTo;
        private boolean close;

        Connection(Socket socket) {
            client = socket;
            close = false;
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            }
            catch (IOException e) {

            }
        }

        @Override
        public void run() {
            super.run();
            String str;

            try {
                str = in.readLine();
                name = str;
                str = "";
                nameTo = in.readLine();
                while (!close) {
                    try {
                        str = in.readLine();
                        if (str.equals("close")) {
                            close = true;
                        }
                        if (str.equals("change")) {
                            nameTo = in.readLine();
                        }
                        else {
                            for (int i = 0; i < connections.size(); i++) {
                                if (nameTo.equals("All")) {
                                    connections.get(i).send(str);
                                }
                                else {
                                    if (connections.get(i).returnName().equals(nameTo)) {
                                        connections.get(i).send(str);
                                    }
                                    if (connections.get(i).returnName().equals(name)) {
                                        connections.get(i).send(str);
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e) {

                    }
                }
                close();
            }
            catch (IOException e) {

            }

        }

        private void send(String str) {
            out.println(str);
        }

        public String returnName() {
            return name;
        }

        public void close() {
            try {
                in.close();
                out.close();
                client.close();
                int i = 0;
                while (!connections.get(i).returnName().equals(name)) {
                    i++;
                }
                connections.remove(i);
                System.out.println(name + " is disconnected!");
                System.out.println(connections.size() + " users is active!");
            }
            catch (IOException e) {

            }
        }

    }
}
