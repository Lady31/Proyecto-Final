import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Editar_Med extends JFrame {
    private JPanel paneledit;
    private JTextField busqueda_COD;
    private JTextField usu_med;
    private JTextField Cod_usu;
    private JTextField usu_cedula;
    private JTextField usu_contra;
    private JComboBox comboBox1;
    private JButton cancelarButton;
    private JButton guardarButton;
    private String nombreUsuario;

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD = "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public Editar_Med() {
        setContentPane(paneledit);
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

        busqueda_COD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = busqueda_COD.getText().trim();
                cargarInformacionUsuario(codigo);
            }
        });
    }

    private void guardar() {
        String codigo = Cod_usu.getText().trim();
        String nombreUsuario = usu_med.getText().trim();
        String cedula = usu_cedula.getText().trim();
        String contraseña = usu_contra.getText().trim();
        String rol = comboBox1.getSelectedItem().toString();

        try (Connection connection = conexion_base()) {
            String sql = "UPDATE personal_medico SET nombre_de_usuario = ?, cedula = ?, contraseña = ?, rol = ? WHERE codigo = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, nombreUsuario);
            pstm.setString(2, cedula);
            pstm.setString(3, contraseña);
            pstm.setString(4, rol);
            pstm.setString(5, codigo);
            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Los cambios se guardaron correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la información del usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la información del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarInformacionUsuario(String codigo) {
        try (Connection connection = conexion_base()) {
            String sql = "SELECT * FROM personal_medico WHERE codigo = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, codigo);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                // Cargar la información del usuario en los campos de texto y el JComboBox
                usu_med.setText(rs.getString("nombre_de_usuario"));
                Cod_usu.setText(rs.getString("codigo"));
                usu_cedula.setText(rs.getString("cedula"));
                usu_contra.setText(rs.getString("contraseña"));
                // Establecer el valor seleccionado en el JComboBox
                comboBox1.setSelectedItem(rs.getString("rol"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con el código especificado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la información del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
