package es.studium.Museo;

import java.sql.*;
import java.awt.*;

public class Modelo {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/GestionMuseo";
    String login = "gestorMuseo";
    String password = "claveSegura123";
    String sentencia = "";

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public Connection conectar() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
        return connection;
    }

    public void desconectar(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }

    public boolean altaMuseo(Connection conexion, String nombre, String ubicacion) {
        boolean altaCorrecta = false;
        if (!nombre.isBlank() && !ubicacion.isBlank()) {
            sentencia = "INSERT INTO Museo VALUES (null, '" + nombre + "', '" + ubicacion + "');";
            try {
                statement = conexion.createStatement();
                statement.executeUpdate(sentencia);
                altaCorrecta = true;
            } catch (SQLException e) {
                altaCorrecta = false;
            }
        }
        return altaCorrecta;
    }

    public boolean bajaMuseo(Connection connection, String idMuseo) {
        boolean resultado = false;
        sentencia = "DELETE FROM Museo WHERE idMuseo = " + idMuseo + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sentencia);
            resultado = true;
        } catch (SQLException sqlex) {
            resultado = false;
        }
        return resultado;
    }

    public void rellenarChoiceMuseos(Connection connection, Choice ch) {
        ch.removeAll();
        ch.add("Seleccionar un museo...");
        try {
            statement = connection.createStatement();
            sentencia = "SELECT * FROM Museo";
            rs = statement.executeQuery(sentencia);
            while (rs.next()) {
                ch.add(rs.getInt("idMuseo") + " - " + rs.getString("nombreMuseo"));
            }
        } catch (SQLException sqlex) {}
    }

    public String consultarMuseos(Connection conexion) {
        String contenidoTextarea = "ID - Nombre - Ubicación\n";
        sentencia = "SELECT * FROM Museo;";
        try {
            statement = conexion.createStatement();
            rs = statement.executeQuery(sentencia);
            while (rs.next()) {
                contenidoTextarea += rs.getInt("idMuseo") + " - " + rs.getString("nombreMuseo") + " - " + rs.getString("ubicacionMuseo") + "\n";
            }
        } catch (SQLException sqlex) {}
        return contenidoTextarea;
    }

    public boolean modificarMuseo(Connection connection, String idMuseo, String nombre, String ubicacion) {
        boolean resultado = false;
        sentencia = "UPDATE Museo SET nombreMuseo = '" + nombre + "', ubicacionMuseo = '" + ubicacion + "' WHERE idMuseo = " + idMuseo + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sentencia);
            resultado = true;
        } catch (SQLException sqlex) {
            resultado = false;
        }
        return resultado;
    }

    public void editarCamposMuseo(Connection connection, String idMuseo, Label lblIdMuseo, TextField nombreMuseo, TextField ubicacionMuseo) {
        try {
            statement = connection.createStatement();
            sentencia = "SELECT * FROM Museo WHERE idMuseo = " + idMuseo;
            rs = statement.executeQuery(sentencia);
            rs.next();
            lblIdMuseo.setText(rs.getInt("idMuseo") + "");
            nombreMuseo.setText(rs.getString("nombreMuseo"));
            ubicacionMuseo.setText(rs.getString("ubicacionMuseo"));
        } catch (SQLException sqlex) {}
    }

    public int comprobarCredenciales(Connection connection, String usuario, String clave) {
        int tipoUsuario = -1;
        sentencia = "SELECT tipoUsuario FROM Usuario WHERE nombreUsuario = '" + usuario + "' AND claveUsuario = SHA2('" + clave + "', 256);";
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sentencia);
            if (rs.next()) {
                tipoUsuario = rs.getInt("tipoUsuario");
            }
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.getMessage());
        }
        return tipoUsuario;
    }

    // Métodos añadidos para ModificarMuseo y ConsultaMuseo
    public void modificarMuseo(String sentencia) {
        try {
            connection = conectar();
            if (connection != null) {
                statement = connection.createStatement();
                statement.executeUpdate(sentencia);
            }
        } catch (SQLException sqlex) {
            System.out.println("Error en modificación: " + sqlex.toString());
        } finally {
            desconectar(connection);
        }
    }

    public String consultarMuseos() {
        String resultado = "ID - Nombre - Ubicación\n";
        try {
            connection = conectar();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Museo");

            while (rs.next()) {
                resultado += rs.getInt("idMuseo") + " - " + rs.getString("nombreMuseo") + " - " + rs.getString("ubicacionMuseo") + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error en consulta: " + e.toString());
        } finally {
            desconectar(connection);
        }
        return resultado;
    }
    
    
    public boolean altaVisitante(Connection conexion, String nombre, String email, String dni) {
        boolean altaCorrecta = false;
        if (!nombre.isBlank() && !email.isBlank() && !dni.isBlank()) {
            sentencia = "INSERT INTO Visitante (nombreVisitante, emailVisitante, dniVisitante) VALUES ('" + nombre + "', '" + email + "', '" + dni + "');";
            try {
                statement = conexion.createStatement();
                statement.executeUpdate(sentencia);
                altaCorrecta = true;
            } catch (SQLException e) {
                System.out.println("Error en altaVisitante: " + e.getMessage());
                altaCorrecta = false;
            }
        }
        return altaCorrecta;
    }
 // Método corregido para rellenar el Choice con los visitantes
    public void rellenarChoiceVisitantes(Connection connection, Choice choice) {
        choice.removeAll(); // Limpiamos antes de cargar
        choice.add("Seleccionar un visitante...");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idVisitante, nombreVisitante FROM Visitante");
            while (rs.next()) {
                choice.add(rs.getInt("idVisitante") + " - " + rs.getString("nombreVisitante"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar visitantes: " + e.getMessage());
        }
    }


    // Método para eliminar un visitante
    public boolean bajaVisitante(Connection connection, String idVisitante) {
        boolean resultado = false;
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM visitante WHERE idVisitante = ?");
            pstmt.setInt(1, Integer.parseInt(idVisitante));
            resultado = pstmt.executeUpdate() > 0;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar visitante: " + e.getMessage());
        }
        return resultado;
    }
    
    public void editarCamposVisitante(Connection connection, String idVisitante, Label lblIdVisitante, TextField nombreVisitante, TextField emailVisitante, TextField dniVisitante) {
        try {
            statement = connection.createStatement();
            sentencia = "SELECT * FROM Visitante WHERE idVisitante = " + idVisitante;
            rs = statement.executeQuery(sentencia);
            rs.next();
            lblIdVisitante.setText(rs.getInt("idVisitante") + "");
            nombreVisitante.setText(rs.getString("nombreVisitante"));
            emailVisitante.setText(rs.getString("emailVisitante"));
            dniVisitante.setText(rs.getString("dniVisitante"));
        } catch (SQLException sqlex) {
            System.out.println("Error al editar visitante: " + sqlex.getMessage());
        }
    }

    public boolean modificarVisitante(Connection connection, String idVisitante, String nombre, String email, String dni) {
        boolean resultado = false;
        String sentencia = "UPDATE Visitante SET nombreVisitante = '" + nombre + "', emailVisitante = '" + email + "', dniVisitante = '" + dni + "' WHERE idVisitante = " + idVisitante + ";";
        
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sentencia);
                resultado = true;
            }
        } catch (SQLException sqlex) {
            System.out.println("Error en modificación: " + sqlex.toString());
            resultado = false;
        } finally {
            desconectar(connection);
        }
        
        return resultado;
    }

    public String consultarVisitantes(Connection connection) {
        String resultado = "ID - Nombre - Email - DNI\n";
        String sentencia = "SELECT * FROM Visitante;";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sentencia);

            while (rs.next()) {
                resultado += rs.getInt("idVisitante") + " - " 
                          + rs.getString("nombreVisitante") + " - " 
                          + rs.getString("emailVisitante") + " - " 
                          + rs.getString("dniVisitante") + "\n";
            }
            
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error en consulta de visitantes: " + e.getMessage());
        }
        
        return resultado;
    }





}
