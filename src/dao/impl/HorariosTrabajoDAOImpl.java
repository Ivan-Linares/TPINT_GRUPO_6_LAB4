package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.IHorariosTrabajo;
import dominio.HorariosTrabajo;
import dominio.Medico;

public class HorariosTrabajoDAOImpl implements IHorariosTrabajo {
	
	private static final String insertHorariosTrabajoMedico = "Insert into HorariosTrabajo (IdMedico, Dia, HoraEntrada, HoraSalida, Libre) values (?,?,?,?,?)";
	private static final String deleteHorariosTrabajoMedico = "Update horariostrabajo set Activo=0 where idMedico=? and dia=? and horaentrada=?";
	private static final String reactiveHorariosTrabajoMedico = "Update horariostrabajo set Activo=1 where idMedico=? and dia=? and horaentrada=?";
	private static final String deleteAllHorariosTrabajoMedico = "Update horariostrabajo set Activo=0 where idMedico=?";
	private static final String listarHorariosTrabajoMedico = "select IdMedico, Dia, HoraEntrada, HoraSalida from horariostrabajo where activo = 1";
	private static final String listarHorariosTrabajoPorMedico = "select IdMedico, Dia, HoraEntrada, HoraSalida, Activo from horariostrabajo where idMedico = ";
	private static final String updateHorariosTrabajoMedico = "update horariostrabajo set Dia=?, HorarioEntrada=?, HorarioSalida=? where idMedico=?";
	private static final String listarFechayHoraxMedico = "select IdMedico, Dia, HoraEntrada, HoraSalida from horariostrabajo where IdMedico =";
	
	@Override
	public boolean agregar(HorariosTrabajo horarioTrabajo) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean agregoHorario = false;
		
		try {
			
			statement = conexion.prepareStatement(insertHorariosTrabajoMedico);
			statement.setInt(1, horarioTrabajo.getIdMedico());
			statement.setString(2, horarioTrabajo.getDia());
			statement.setString(3, horarioTrabajo.getHoraEntrada());
			statement.setString(4, horarioTrabajo.getHoraSalida());
			statement.setBoolean(5, true);
			
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
	public boolean eliminar(int idMedico, String dia, String horaEntrada) {
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
			statement.setInt(1, idMedico);
			statement.setString(2, dia);
			statement.setString(3, horaEntrada);
			
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
			
			SimpleDateFormat print = new SimpleDateFormat("hh:mm");
			String fechaNacimientoString = print.format(horarioTrabajo.getHoraEntrada());
			Time sqlHoraEntrada = Time.valueOf(fechaNacimientoString);
			statement.setTime(2, sqlHoraEntrada);
			
			SimpleDateFormat print2 = new SimpleDateFormat("hh:mm");
			String fechaNacimientoString2 = print.format(horarioTrabajo.getHoraSalida());
			Time sqlHoraSalida = Time.valueOf(fechaNacimientoString2);
			statement.setTime(3, sqlHoraSalida);
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
				ht.setHoraEntrada(rs.getString("HoraEntrada"));
				ht.setHoraSalida(rs.getString("HoraSalida"));
				
				listaHT.add(ht);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaHT;
	}


	@Override
	public ArrayList<HorariosTrabajo> listarPorMedico(int IdMedico) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<HorariosTrabajo> listaHT = new ArrayList<HorariosTrabajo>();
		
		try 
		{
			st = conexion.createStatement();
			
			ResultSet rs = st.executeQuery(listarHorariosTrabajoPorMedico + IdMedico);
			
			while(rs.next()) {
				HorariosTrabajo ht = new HorariosTrabajo();
				ht.setIdMedico(rs.getInt("idMedico"));
				ht.setDia(rs.getString("Dia"));
				ht.setHoraEntrada(rs.getString("HoraEntrada"));
				ht.setHoraSalida(rs.getString("HoraSalida"));
				ht.setActivo(rs.getBoolean("activo"));
				
				listaHT.add(ht);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaHT;
	}


	@Override
	public boolean eliminarTodos(int idMedico) {
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

			statement = conexion.prepareStatement(deleteAllHorariosTrabajoMedico);
			statement.setInt(1, idMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoHT = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return eliminoHT;
	}
	
	public ArrayList<HorariosTrabajo> listarMedicosFechayHora(int idMedico) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<HorariosTrabajo> listaHT = new ArrayList<HorariosTrabajo>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarFechayHoraxMedico+idMedico);
			
			while(rs.next()) {
				HorariosTrabajo ht = new HorariosTrabajo();
				ht.setIdMedico(rs.getInt("IdMedico"));
				ht.setDia(rs.getString("Dia"));
				ht.setHoraEntrada(rs.getString("HoraEntrada"));
				ht.setHoraSalida(rs.getString("HoraSalida"));
				
				listaHT.add(ht);
			}		
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaHT;
	}


	@Override
	public boolean reactivar(int idMedico, String dia, String horaEntrada) {
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
		
		boolean state = false;
		
		try
		{

			statement = conexion.prepareStatement(reactiveHorariosTrabajoMedico);
			statement.setInt(1, idMedico);
			statement.setString(2, dia);
			statement.setString(3, horaEntrada);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				state = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return state;
	}
}
