import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LOGIN extends JFrame {

    private JPanel panelogin;
    private JTextField usuario;
    private JPasswordField clave;
    private JComboBox comborol;
    private JButton INICIARSESIÓNButton;
    private String nombreUsuario;

    public LOGIN() {
        super("CENTRO MÉDICO SANTA ROSA");
        setContentPane(panelogin);
        setSize(400,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        this.nombreUsuario = nombreUsuario;

        INICIARSESIÓNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login();
            }
        });
    }
    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD =  "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url,usuarioBD,contraseña);
    }
    public void Login() {
        try (Connection connection = conexion_base()) {
            String nombreusu = usuario.getText().trim();
            String contra = new String(clave.getPassword()).trim();
            String rolSeleccionado = comborol.getSelectedItem().toString();

            String sql = "SELECT * FROM personal_medico WHERE usuario = ? AND contraseña = ? AND rol = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, nombreusu);
            pstm.setString(2, contra);
            pstm.setString(3, rolSeleccionado);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String nombreUsuario = rs.getString("usuario");
                String rolUsuario = rs.getString("rol");
                JOptionPane.showMessageDialog(null, "CREDENCIALES CORRECTAS", "Info", JOptionPane.INFORMATION_MESSAGE);

                // Verificar el rol del usuario y crear la pantalla correspondiente
                if (rolUsuario.equals("admin")) {
                    Menu menu = new Menu(nombreUsuario);
                    menu.setVisible(true);
                    dispose();
                    JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(panelogin);
                    frame.dispose();
                } else if (rolUsuario.equals("medico")) {
                    Menu menu = new Menu(nombreUsuario);
                    menu.setVisible(true);
                    dispose();
                    JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(panelogin);
                    frame.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "CREDENCIALES INCORRECTAS", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL REALIZAR LA CONSULTA: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
