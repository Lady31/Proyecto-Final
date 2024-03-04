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
                mostrarTabla();

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
    }
    private void mostrarTabla() {
        try (Connection connection = conexion_base()) {
            String sql = "SELECT * FROM personal_medico";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            // Crear el modelo de la tabla
            DefaultTableModel tableModel = new DefaultTableModel();
            // Añadir las columnas al modelo de la tabla
            tableModel.addColumn("ID");
            tableModel.addColumn("Usuario");
            tableModel.addColumn("Código");
            tableModel.addColumn("Cédula");
            tableModel.addColumn("Rol");

            // Agregar filas a la tabla con los datos de la base de datos
            while (rs.next()) {
                Object[] fila = new Object[5]; // Hay 5 columnas en la tabla
                fila[0] = rs.getInt("id_medico");
                fila[1] = rs.getString("usuario");
                fila[2] = rs.getString("codigo");
                fila[3] = rs.getString("cedula");
                fila[4] = rs.getString("rol");
                tableModel.addRow(fila);
            }

            // Crear la tabla con el modelo de tabla creado
            JTable table = new JTable(tableModel);
            // Crear el JScrollPane y agregar la tabla al JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            // Limpiar el panel_muestra antes de agregar la tabla
            panel_muestra.removeAll();
            // Agregar el JScrollPane al panel_muestra
            panel_muestra.add(scrollPane);
            // Refrescar el panel para que se muestre la tabla
            panel_muestra.revalidate();
            panel_muestra.repaint();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
