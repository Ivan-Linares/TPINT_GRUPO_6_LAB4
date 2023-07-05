package dominio;

public class HorariosTrabajo {

	private int IdMedico;
	private String dia;
	private String horaEntrada;
	private String horaSalida;
	private boolean libre;
	private boolean activo;
	
	public int getIdMedico() {
		return IdMedico;
	}
	public void setIdMedico(int idMedico) {
		IdMedico = idMedico;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public String getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
	public boolean isLibre() {
		return libre;
	}
	public void setLibre(boolean libre) {
		this.libre = libre;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
