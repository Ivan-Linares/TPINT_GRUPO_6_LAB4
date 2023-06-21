package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dao.IHorariosTrabajo;
import dominio.HorariosTrabajo;

public class HorariosTrabajoDAOImpl implements IHorariosTrabajo {
	
	private static final String insertHorariosTrabajoMedico = "Insert into HorariosTrabajo (IdMedico, Dia, HoraEntrada, HoraSalida, Libre) values (?,?,?,?,?)";
	private static final String deleteHorariosTrabajoMedico = "Update horariostrabajo set Activo=0 where idMedico=?";
	private static final String listarHorariosTrabajoMedico = "select IdMedico, Dia, HoraEntrada, HoraSalida from horariostrabajo";
	private static final String updateHorariosTrabajoMedico = "update horariostrabajo set Dia=?, HorarioEntrada=?, HorarioSalida=? where idMedico=?";
	
	@Override
	public boolean agregar(HorariosTrabajo horarioTrabajo) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean agregoHorario = false;
		
		try {
			
			statement = conexion.prepareStatement(insertHorariosTrabajoMedico);
			statement.setInt(1, horarioTrabajo.getIdMedico());
			statement.setString(2, horarioTrabajo.getDia());
			statement.setInt(3, horarioTrabajo.getHoraEntrada());
			statement.setInt(3, horarioTrabajo.getHoraSalida());
			statement.setBoolean(4, horarioTrabajo.isLibre());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				agregoHorario = true;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agregoHorario;
	}


	@Override
	public boolean eliminar(String idMedico) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		boolean eliminoHT = false;
		
		try
		{

			statement = conexion.prepareStatement(deleteHorariosTrabajoMedico);
			statement.setString(1, idMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoHT = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(eliminoHT == true) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean modificar(HorariosTrabajo horarioTrabajo) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean modificado = false;
		
		try {
			statement = conexion.prepareStatement(updateHorariosTrabajoMedico);
			
			statement.setString(1, horarioTrabajo.getDia());
			statement.setInt(2, horarioTrabajo.getHoraEntrada());
			statement.setInt(3, horarioTrabajo.getHoraSalida());
			statement.setInt(4, horarioTrabajo.getIdMedico());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificado = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modificado;
		
	}

	@Override
	public ArrayList<HorariosTrabajo> listar() {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<HorariosTrabajo> listaHT = new ArrayList<HorariosTrabajo>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarHorariosTrabajoMedico);
			
			while(rs.next()) {
				HorariosTrabajo ht = new HorariosTrabajo();
				ht.setIdMedico(rs.getInt("idMedico"));
				ht.setDia(rs.getString("Dia"));
				ht.setHoraEntrada(rs.getInt("HoraEntrada"));
				ht.setHoraSalida(rs.getInt("HoraSalida"));
				
				listaHT.add(ht);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaHT;
	}
}
