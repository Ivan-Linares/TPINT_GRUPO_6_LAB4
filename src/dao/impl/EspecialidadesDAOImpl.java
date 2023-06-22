package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IEspecialidadDAO;
import dominio.Especialidad;

public class EspecialidadesDAOImpl implements IEspecialidadDAO{

	private static final String listarEspecialidades = "select IdEspecialidad, Descripcion from especialidades";
	
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

}
