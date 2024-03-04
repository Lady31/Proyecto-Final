import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Tablas extends JFrame {
    private JButton volverButton;
    private JTable tablaUsuarios;
    private JScrollPane scrollPane;
    private JPanel panelp;

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD = "root";
        String contraseña = "Hidokun2003.y";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public Tablas() {
        setContentPane(panelp);
        setSize(700, 500);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        try (Connection connection = conexion_base()) {
            String sql = "SELECT * FROM personal_medico UNION SELECT * FROM administrador";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Usuario");
            tableModel.addColumn("Código");
            tableModel.addColumn("Cédula");
            tableModel.addColumn("Rol");

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_medico");
                fila[1] = rs.getString("usuario");
                fila[2] = rs.getString("codigo");
                fila[3] = rs.getString("cedula");
                fila[4] = rs.getString("rol");
                tableModel.addRow(fila);
            }

            tablaUsuarios.setModel(tableModel);

            // Agregar la tabla a un JScrollPane para que sea desplazable si hay muchos datos
            scrollPane.setViewportView(tablaUsuarios);
            tablaUsuarios.add(scrollPane);


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
