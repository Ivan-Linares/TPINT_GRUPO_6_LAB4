package dominio;

import java.time.LocalDateTime;

public class Turnos {
	
	private int IdTurno;
	private LocalDateTime fechaHora;
	private Especialidad especialidad;
	private Paciente paciente;
	private Medico medico;
	private Estado estado;
	private String observacion;
	private boolean activo;
	
	
	public Turnos(int idTurno, LocalDateTime fechaHora, Especialidad especialidad, Paciente paciente, Medico medico, Estado estado,
			String observacion, boolean activo) {
		super();
		IdTurno = idTurno;
		this.fechaHora = fechaHora;
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
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
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
	
}
