import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Agregar extends JFrame {
    private JPanel panel1;
    private JTextField usuario;
    private JTextField codigo;
    private JTextField cedula;
    private JTextField contraseña;
    private JComboBox comboBox1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private String nombreUsuario;
    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD = "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }
    public Agregar() {
        setContentPane(panel1);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        this.nombreUsuario = nombreUsuario;

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
    }

    private void guardar() {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Desea agregar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            String nombreUsuario = usuario.getText().trim();
            String cod = codigo.getText().trim();
            String ced = cedula.getText().trim();
            String pass = contraseña.getText().trim();
            String rol = comboBox1.getSelectedItem().toString();

            try (Connection connection = conexion_base()) {
                String sql;
                if (rol.equals("medico")) {
                    sql = "INSERT INTO personal_medico (nombre_de_usuario, codigo, cedula, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
                } else if (rol.equals("admin")) {
                    sql = "INSERT INTO administradores (nombre_de_usuario, codigo, cedula, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
                } else {
                    JOptionPane.showMessageDialog(null, "Rol no válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, nombreUsuario);
                pstm.setString(2, cod);
                pstm.setString(3, ced);
                pstm.setString(4, pass);
                pstm.setString(5, rol);
                int rowsInserted = pstm.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar la información del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
