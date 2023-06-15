package dominio;

import java.util.Date;

public class Paciente extends Persona{
	private int idPaciente;
	private Cobertura cobertura;
			
	 public Paciente(String dni, String nombre, String apellido, String sexo, String nacionalidad, Date fechaNacimiento,
	            String correo, Domicilio domicilio, Telefono telefono, boolean activo, int idPaciente, Cobertura cobertura) {
	        super(dni, nombre, apellido, sexo, nacionalidad, fechaNacimiento, correo, domicilio, telefono, activo);
	        this.idPaciente = idPaciente;
	        this.cobertura = cobertura;
	  }
	 
	 public Paciente() {}
	 
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public Cobertura getCobertura() {
		return cobertura;
	}
	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;
	}
	
	
}
