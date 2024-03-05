import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class modificar extends JFrame {
    private JPanel modificar;
    private JRadioButton añadirNuevoMedicoRadioButton;
    private JRadioButton modificarMedicoRadioButton;
    private JRadioButton eliminarMedicoRadioButton;
    private JRadioButton volverRadioButton;
    private String nombreUsuario;

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD =  "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url,usuarioBD,contraseña);
    }

    public modificar(String nombreUsuario) {
        super("Modificar Informacion");
        setContentPane(modificar);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        this.nombreUsuario = nombreUsuario;

        modificarMedicoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editar_Med edit = new Editar_Med();
                edit.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(modificar);
                frame.dispose();
            }
        });
        eliminarMedicoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        añadirNuevoMedicoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agregar agregar =  new Agregar();
                agregar.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(modificar);
                frame.dispose();
            }
        });
        volverRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(modificar);
                frame.dispose();
            }
        });
    }
    private void eliminarUsuario() {
        String codigoMedicoEliminar = JOptionPane.showInputDialog("Ingrese el código del médico a eliminar:");

        if (codigoMedicoEliminar != null && !codigoMedicoEliminar.isEmpty()) {
            try (Connection connection = conexion_base()) {
                String sql = "DELETE FROM personal_medico WHERE codigo = ?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, codigoMedicoEliminar);

                int filasAfectadas = pstm.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con el código especificado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
