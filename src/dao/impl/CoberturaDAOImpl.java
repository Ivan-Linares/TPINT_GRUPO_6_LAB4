package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dao.ICoberturaDAO;
import dominio.Cobertura;

public class CoberturaDAOImpl implements ICoberturaDAO {
	private static final String listarCoberturas = "select IdCobertura, Descripcion from Coberturas where Activo = 1";
	@Override
	public List<Cobertura> listarCoberturas() {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Cobertura> listaCoberturas = new ArrayList<Cobertura>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarCoberturas);
			
			while(rs.next()) {
				Cobertura cobertura = new Cobertura();
				cobertura.setId(rs.getInt("idCobertura"));	
				cobertura.setDescripcion(rs.getString("descripcion"));					
				listaCoberturas.add(cobertura);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaCoberturas;
	}

}
