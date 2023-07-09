package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IEspecialidadDAO;
import dominio.Especialidad;
import dominio.Medico;
import dominio.Telefono;

public class EspecialidadesDAOImpl implements IEspecialidadDAO{
	private static final String listarEspecialidadesPorMedico = "select esp.IdEspecialidad as idespecialidad, esp.Descripcion as descripcion, espMed.Activo as activo from especialidadxmedico espMed inner join especialidades esp on espMed.idEspecialidad = esp.idEspecialidad where espMed.IdMedico=";
	private static final String listarEspecialidades = "select IdEspecialidad, Descripcion from especialidades";
	private static final String insertEspecialidadMedico = "Insert into especialidadxmedico (idEspecialidad, idMedico) values (?,?)";
	private static final String deleteEspecialidadMedico = "update especialidadxmedico set activo = 0 where idEspecialidad=? and idMedico =?";
	@Override
	public List<Especialidad> listarEspecialidades() {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarEspecialidades);
			
			while(rs.next()) {
				Especialidad esp = new Especialidad();
				esp.setIdEspecialidad(rs.getInt("IdEspecialidad"));
				esp.setDescripcion(rs.getString("Descripcion"));
				
				listaEspecialidades.add(esp);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaEspecialidades;
	}

	@Override
	public boolean agregarEspecialidadMedico(int idEspecialidad, int idMedico) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean state = false;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try {
			statement = conexion.prepareStatement(insertEspecialidadMedico);
			statement.setInt(1, idEspecialidad);
			statement.setInt(2, idMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				state = true;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public boolean eliminarEspecialidadMedico(int idEspecialidad, int idMedico) {
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
			statement = conexion.prepareStatement(deleteEspecialidadMedico);
			statement.setInt(1, idEspecialidad);
			statement.setInt(2, idMedico);
			
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

	@Override
	public ArrayList<Especialidad> listarEspecialidadesPorMedico(int idMedico) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Especialidad> listaEspecialidadesMedico = new ArrayList<Especialidad>();
		
		try 
		{
			st = conexion.createStatement();
			
			ResultSet rs = st.executeQuery(listarEspecialidadesPorMedico + idMedico);
			
			while(rs.next()) {
				
				Especialidad especialidad =  new Especialidad();
				especialidad.setDescripcion(rs.getString("Descripcion"));
				especialidad.setIdEspecialidad(rs.getInt("IdEspecialidad"));
				especialidad.setActivo(rs.getBoolean("activo"));

				listaEspecialidadesMedico.add(especialidad);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaEspecialidadesMedico;
	}

}
