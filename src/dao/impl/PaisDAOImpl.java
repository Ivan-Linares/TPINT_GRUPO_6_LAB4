package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IPaisDAO;
import dominio.Cobertura;
import dominio.Pais;

public class PaisDAOImpl implements IPaisDAO {
	private static final String listarPaises = "select IdPais, Descripcion from Paises where Activo = 1";
	@Override
	public List<Pais> listarPaises() {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Pais> listaPaises = new ArrayList<Pais>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarPaises);
			while(rs.next()) {
				Pais pais = new Pais();
				pais.setIdPais(rs.getInt("idPais"));	
				pais.setDescripcion(rs.getString("descripcion"));	
				listaPaises.add(pais);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaPaises;
	}

}
