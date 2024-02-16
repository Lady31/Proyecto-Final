import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LOGIN login = new LOGIN();
                login.setSize(700,500);
                login.setVisible(true);
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        });

    }

}