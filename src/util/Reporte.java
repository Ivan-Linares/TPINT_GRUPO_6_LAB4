package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import dao.impl.Conexion;
import dtos.TurnosPorEspecialidadDTO;
import dtos.TurnosPorMedicoDTO;

public class Reporte {
	public ArrayList<TurnosPorEspecialidadDTO> listarCantidadTurnosPorEspecialdiad(Date fechaInicio, Date fechaFin) {
	    ArrayList<TurnosPorEspecialidadDTO> cantTurnos = new ArrayList<>();

	    PreparedStatement statement;
	    Connection conexion = Conexion.getConexion().getSQLConexion();

	    String query = "SELECT COUNT(*) AS cantidad, e.Descripcion AS nombre " +
	            "FROM turnos t " +
	            "INNER JOIN especialidades e ON t.IdEspecialidad = e.IdEspecialidad " +
	            "WHERE t.Fecha BETWEEN ? AND ? " +
	            "GROUP BY e.Descripcion";

	    try {
	        statement = conexion.prepareStatement(query);
	        statement.setDate(1, new java.sql.Date(fechaInicio.getTime()));
	        statement.setDate(2, new java.sql.Date(fechaFin.getTime()));

	        ResultSet rs = statement.executeQuery();
	        while(rs.next()) {
	            String nombre = rs.getString("nombre");
	            int cantidad = rs.getInt("cantidad");

	            TurnosPorEspecialidadDTO dto = new TurnosPorEspecialidadDTO(nombre, cantidad);
	            cantTurnos.add(dto);
	        }
	    }
	    catch(SQLException ex) {
	        ex.printStackTrace();
	    }

	    if(!cantTurnos.isEmpty()) {
	        return cantTurnos;
	    }
	    return null;
	}
	
	public ArrayList<TurnosPorMedicoDTO> listarCantidadTurnosPorMedico(){
		ArrayList<TurnosPorMedicoDTO> cantidadTurnosPorMedico = new ArrayList<>();
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		String query = "SELECT COUNT(*) as cantidad, p.Nombre as nombre, p.Apellido as apellido" + 
				"FROM turnos" + 
				"INNER JOIN medicos m ON t.IdMedico = m.IdMedico" + 
				"INNER JOIN personas p ON m.DNI = p.DNI" + 
				"GROUP BY p.Nombre, p.Apellido"; 
		
		try {
			statement = conexion.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				int cantidad = rs.getInt("cantidad");
				TurnosPorMedicoDTO dto = new TurnosPorMedicoDTO(nombre, apellido, cantidad);
				cantidadTurnosPorMedico.add(dto);
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		if(!cantidadTurnosPorMedico.isEmpty()) {
			return cantidadTurnosPorMedico;
		}
		return null;
	}
}













