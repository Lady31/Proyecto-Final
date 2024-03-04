import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Menu extends JFrame{
    private JPanel menupn;
    private JTextField codigo_medico;
    private JTextField rol;
    private JButton gestionDePersonalButton;
    private JButton reportesEstadisticosButton;
    private JPanel panel_muestra;
    private JButton agregarNuevoMedicoButton;
    private String nombreUsuario;

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD =  "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url,usuarioBD,contraseña);
    }

    public Menu(String nombreUsuario){
        super("CENTRO MÉDICO SANTA ROSA");
        setContentPane(menupn);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        this.nombreUsuario = nombreUsuario;
        panel_muestra = new JPanel();

        try (Connection connection = conexion_base()) {
            String sql = "SELECT codigo, rol FROM administrador WHERE usuario = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, this.nombreUsuario);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String codigoMedico = rs.getString("codigo");
                String rolUsuario = rs.getString("rol");

                codigo_medico.setText(codigoMedico);
                rol.setText(rolUsuario);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        gestionDePersonalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tablas tablas = new Tablas();
                tablas.setVisible(true);
            }
        });
        agregarNuevoMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificar modi = new modificar(nombreUsuario);
                modi.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(menupn);
                frame.dispose();
            }
        });
        reportesEstadisticosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

}
