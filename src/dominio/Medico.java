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
	private String DiaAtencion;
	private String HorarioAtencionDesde;
	private String HorarioAtencionHasta;
	private ArrayList<Especialidad> especialidades;
	
	public int getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}
	public String getDiaAtencion() {
		return DiaAtencion;
	}
	public void setDiaAtencion(String diaAtencion) {
		DiaAtencion = diaAtencion;
	}
	public String getHorarioAtencionDesde() {
		return HorarioAtencionDesde;
	}
	public void setHorarioAtencionDesde(String horarioAtencionDesde) {
		HorarioAtencionDesde = horarioAtencionDesde;
	}
	public String getHorarioAtencionHasta() {
		return HorarioAtencionHasta;
	}
	public void setHorarioAtencionHasta(String horarioAtencionHasta) {
		HorarioAtencionHasta = horarioAtencionHasta;
	}
	public ArrayList<Especialidad> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(ArrayList<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}
	
}
