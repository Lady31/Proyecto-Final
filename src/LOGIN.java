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
                Login();
            }
        });
    }
    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medico";
        String usuarioBD =  "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url,usuarioBD,contraseña);
    }
     public void Login(){
        try (Connection connection = conexion_base()){
            String nombreusu = usuario.getText().trim();
            String contra = new String(clave.getPassword()).trim();

            String sql = "SELECT *FROM personal_medico WHERE usuario =? AND contraseña =?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,nombreusu);
            pstm.setString(2,contra);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()){
                String nombreUsuario = rs.getString("usuario");
                JOptionPane.showMessageDialog(null,"CREDENCIALES CORRECTAS", "info",JOptionPane.INFORMATION_MESSAGE );

            }else{
                JOptionPane.showMessageDialog(null,"CREDENCIALES INCORRECTAS","ERROR",JOptionPane.ERROR_MESSAGE);

            }

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"ERROR AL REALIZAR LA CONSULTA" + ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);

        }
     }
}
