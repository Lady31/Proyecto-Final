import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class personal extends JFrame  {
    private JTextField codigo_medico;
    private JTextField rol;
    private JButton historialButton;
    private JButton agendamientoButton;
    private JPanel Panel_personal;

    public personal(String nombreUsuario) {
        super("Personal");
        setContentPane(Panel_personal);


        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
