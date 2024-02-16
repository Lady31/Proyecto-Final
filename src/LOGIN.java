import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LOGIN extends JFrame {

    private JPanel panelogin;
    private JTextField usuario;
    private JTextField codigo;
    private JTextField cedula;
    private JTextField contraseña;
    private JComboBox comborol;
    private JButton INICIARSESIÓNButton;

    public LOGIN() {
        super("CENTRO MÉDICO SANTA ROSA");
        setContentPane(panelogin);
        setSize(400,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        INICIARSESIÓNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
}
