import java.io.IOException;

public class StartClientWithGUI {

    public static void main(String[] args) {
        ClientWithGUI clientWithGUI = new ClientWithGUI();
        try {
            clientWithGUI.run();
        }
        catch (IOException e) {

        }
    }

}
