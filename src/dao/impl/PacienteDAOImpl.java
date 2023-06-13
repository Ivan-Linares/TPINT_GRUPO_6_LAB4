package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import dao.IPacienteDAO;
import dominio.Paciente;

public class PacienteDAOImpl implements IPacienteDAO{
	private static final String insert = "";
	private static final String delete = "";
	private static final String update = "";
	private static final String listar = "";
	
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String pass = "root";
	private String dbName = "clinicadb";
	
	@Override
	public boolean agregar(Paciente paciente) {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		int filas=0;
		Connection cn = null;
		
		try
		{
			cn = DriverManager.getConnection(host+dbName, user,pass);
			Statement st = cn.createStatement();
			String query = "Insert into persona (dni, nombre, apellido, sexo, nacionalidad, fechaNac, correo, idDomicilio, activo) values ('"+paciente.getIdPaciente()+"','"+paciente.getNombre()+"','"+paciente.getApellido()+"','"+paciente.getSexo()+"','"+paciente.getNacionalidad()+"','"+paciente.getFechaNacimiento()+"','"+paciente.getCorreo()+"','"+paciente.getDomicilio()+"','"+paciente.isActivo()+"')";
			filas = st.executeUpdate(query);
			
			Statement st2 = cn.createStatement();
			String query2 = "Insert into paciente (dni, idCobertura, activo) values ('"+paciente.getDni()+"','"+paciente.getCobertura()+"','"+paciente.isActivo()+"')";
			filas += st2.executeUpdate(query2);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(filas==2) {
			return true;
		}else
			return false;
	}

	@Override
	public boolean eliminar(Paciente paciente_a_eliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Paciente> listarPacientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modificar(int ID) {
		// TODO Auto-generated method stub
		return false;
	}

}
