package dominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LoginDao {

	private String host = "jdbc:mysql://localhost:3306/";
	private String usuario = "root";
	private String pass = "root";
	private String dbName = "clinicadb";
	
	public int iniciarSesion(Login user)
	{
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
			cn = DriverManager.getConnection(host+dbName, usuario, pass);
			Statement st = cn.createStatement();
			String query = "Select * from usuario where email = '"+user.getEmail()+"' and password = '"+user.getPassword()+"'";
			filas = st.executeUpdate(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return filas;
	}
}
