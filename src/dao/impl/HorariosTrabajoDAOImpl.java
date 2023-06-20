package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.IHorariosTrabajo;
import dominio.HorariosTrabajo;

public class HorariosTrabajoDAOImpl implements IHorariosTrabajo {
	private static final String insertHorariosTrabajoMedico = "Insert into HorariosTrabajo (IdMedico, Dia, HoraEntrada, HoraSalida, Libre) values (?,?,?,?,?)";
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
	public boolean eliminar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean listar() {
		// TODO Auto-generated method stub
		return false;
	}


}
