package dominio;
import java.util.ArrayList;
import java.util.Date;

public class Medico extends Persona {
	public Medico(String dni, String nombre, String apellido, String sexo, Pais nacionalidad, Date fechaNacimiento,
			String correo, Domicilio domicilio, Telefono telefono, boolean activo) {
		super(dni, nombre, apellido, sexo, nacionalidad, fechaNacimiento, correo, domicilio, telefono, activo);
		// TODO Auto-generated constructor stub
	}
	private int idMedico;
	private ArrayList<Especialidad> especialidades;
	private ArrayList<HorariosTrabajo> horariosTrabajo;
	
	public Medico() {}
	
	public int getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}
	
	public ArrayList<Especialidad> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(ArrayList<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	public ArrayList<HorariosTrabajo> getHorariosTrabajo() {
		return horariosTrabajo;
	}

	public void setHorariosTrabajo(ArrayList<HorariosTrabajo> horariosTrabajo) {
		this.horariosTrabajo = horariosTrabajo;
	}
	
}
