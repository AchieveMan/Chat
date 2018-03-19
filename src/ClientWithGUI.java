import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWithGUI {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("ChatApp");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 40);
    private Socket socket;
    private String name;
    private String sendTo;

    public ClientWithGUI() {

        textField.setEditable(true);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = textField.getText();
                if (str.equals("close")) {
                    out.println(str);
                    System.exit(0);
                }
                else {
                    out.println(name + textField.getText());
                }
                textField.setText("");
            }
        });
    }

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                frame,
                "Введите IP сервера",
                "IP",
                JOptionPane.QUESTION_MESSAGE);
    }

    private String getName() {
        return JOptionPane.showInputDialog(
                frame,
                "Введите ник",
                "Ник",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void run() throws IOException {
        socket = new Socket(getServerAddress(), 1997);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        name = getName();
        sendTo = "All";
        out.println(name);
        out.println(sendTo);
        while (true) {
            messageArea.append(in.readLine() + '\n');
        }
    }
}
