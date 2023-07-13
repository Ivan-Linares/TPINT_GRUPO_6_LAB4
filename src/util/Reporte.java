package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import dao.impl.Conexion;
import dtos.TurnosPorEspecialidadDTO;
import dtos.TurnosPorMedicoDTO;

public class Reporte {
	public ArrayList<TurnosPorEspecialidadDTO> listarCantidadTurnosPorEspecialdiad(java.util.Date fechaInicio, java.util.Date fechaFin) {
	    ArrayList<TurnosPorEspecialidadDTO> cantTurnos = new ArrayList<>();

	    PreparedStatement statement;
	    Connection conexion = Conexion.getConexion().getSQLConexion();

        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicioString = print.format(fechaInicio);
		Date sqlDateInicio = Date.valueOf(fechaInicioString);
		
		String fechaFinString = print.format(fechaFin);
		Date sqlDateFechaFin = Date.valueOf(fechaFinString);
		
	    String query = "SELECT COUNT(*) AS cantidad, e.Descripcion AS nombre, e.idespecialidad as idespecialidad " +
	            "FROM turnos t " +
	            "INNER JOIN especialidades e ON t.IdEspecialidad = e.IdEspecialidad " +
	            "WHERE t.Fecha BETWEEN '"+ sqlDateInicio+ "' AND '" + sqlDateFechaFin+
	            "' GROUP BY e.Descripcion";
	    
	    System.out.println(query);

	    try {			
	    	statement = conexion.prepareStatement(query);
	        ResultSet rs = statement.executeQuery();
	        while(rs.next()) {
	            String nombre = rs.getString("nombre");
	            int cantidad = rs.getInt("cantidad");
	            int idEspecialidad = rs.getInt("idespecialidad");

	            TurnosPorEspecialidadDTO dto = new TurnosPorEspecialidadDTO(nombre, cantidad, idEspecialidad);
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
				" FROM turnos t" + 
				" INNER JOIN medicos m ON t.IdMedico = m.IdMedico" + 
				" INNER JOIN personas p ON m.DNI = p.DNI" + 
				" GROUP BY p.Nombre, p.Apellido"; 
		System.out.println(query);
		
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













