package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dao.impl.Conexion;

public class ValidadorFormulario {
	
    public boolean existeEmail(String email) {
        String query = "SELECT COUNT(*) AS total FROM Personas WHERE Correo = ?";
        int resultado = 0;
        
        try {
            Connection conexion = Conexion.getConexion().getSQLConexion();
            PreparedStatement statement = conexion.prepareStatement(query);
         
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                resultado = rs.getInt("total");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado == 1;
    }
    
    public boolean existeDni(String dni) {
    	String query = "SELECT COUNT(*) as total FROM PERSONAS WHERE Dni = ?";
    	int resultado = 0;
    	try {
    		Connection conexion = Conexion.getConexion().getSQLConexion();
    		PreparedStatement statement = conexion.prepareStatement(query);
    		
    		statement.setString(1, dni);
    		ResultSet rs = statement.executeQuery();
    		
    		if(rs.next()) {
    			resultado = rs.getInt("total");
    		}
    	}
    	catch(Exception e) {
    	e.printStackTrace();
    	}
    	
    	return resultado == 1;
    }
}


