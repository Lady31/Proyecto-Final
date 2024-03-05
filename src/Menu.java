import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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
                estadistico();
            }
        });
    }
    public void estadistico(){
        try (Connection connection = conexion_base()) {
            String sql = "SELECT COUNT(*) AS total_pacientes FROM pacientes";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            if (rs.next()) {
                int totalPacientes = rs.getInt("total_pacientes");
                dataset.addValue(totalPacientes, "Pacientes", "Total");
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Reporte Estadístico de Pacientes",
                    "Categoría",
                    "Cantidad",
                    dataset
            );

            JFrame frame = new JFrame("Reporte Estadístico de Pacientes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.setContentPane(chartPanel);
            frame.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}




