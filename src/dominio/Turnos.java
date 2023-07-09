package dominio;

import java.util.Date;
import java.time.LocalDateTime;

public class Turnos {
	
	private int IdTurno;
	private Date fecha;
	private int hora;
	private Especialidad especialidad;
	private Paciente paciente;
	private Medico medico;
	private Estado estado;
	private String observacion;
	private boolean activo;
	
	
	public Turnos(int idTurno, Date fecha, Especialidad especialidad, Paciente paciente, Medico medico, Estado estado,
			String observacion, boolean activo) {
		super();
		IdTurno = idTurno;
		this.fecha = fecha;
		this.especialidad = especialidad;
		this.paciente = paciente;
		this.estado = estado;
		this.observacion = observacion;
		this.activo = activo;
	}
	
	public Turnos() {}
	
	public int getIdTurno() {
		return IdTurno;
	}
	public void setIdTurno(int idTurno) {
		IdTurno = idTurno;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date date) {
		this.fecha = date;
	}
	public Especialidad getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "Turnos [IdTurno=" + IdTurno + ", fecha=" + fecha + ", hora=" + hora + ", especialidad=" + especialidad
				+ ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", observacion="
				+ observacion + ", activo=" + activo + "]";
	}
	
}
