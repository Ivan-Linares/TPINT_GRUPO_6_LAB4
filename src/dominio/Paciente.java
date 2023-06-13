package dominio;

import java.util.Date;

public class Paciente {
	//private int Id;
	private int Dni;
	private String Nombre;
	private String Apellido;
	private char Sexo;
	private String Nacionalidad;
	private Date FechaNacimiento;
	private String Direccion;
	private String Localidad;
	private String Provincia;
	private String CorreoElectronico;
	private String Telefono;
	private boolean Estado;
	
	public int getDni() {
		return Dni;
	}
	public void setDni(int dni) {
		Dni = dni;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	public char getSexo() {
		return Sexo;
	}
	public void setSexo(char sexo) {
		Sexo = sexo;
	}
	public String getNacionalidad() {
		return Nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}
	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getLocalidad() {
		return Localidad;
	}
	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}
	public String getProvincia() {
		return Provincia;
	}
	public void setProvincia(String provincia) {
		Provincia = provincia;
	}
	public String getCorreoElectronico() {
		return CorreoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		CorreoElectronico = correoElectronico;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public boolean isEstado() {
		return Estado;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	
	public Paciente() {
		
	}
	
	public Paciente(int dni, String nombre, String apellido, char sexo, String nacionalidad, Date fechaNacimiento,
			String direccion, String localidad, String provincia, String correoElectronico, String telefono,
			boolean estado) {		
		Dni = dni;
		Nombre = nombre;
		Apellido = apellido;
		Sexo = sexo;
		Nacionalidad = nacionalidad;
		FechaNacimiento = fechaNacimiento;
		Direccion = direccion;
		Localidad = localidad;
		Provincia = provincia;
		CorreoElectronico = correoElectronico;
		Telefono = telefono;
		Estado = estado;
	}
	@Override
	public String toString() {
		return "Paciente [Dni=" + Dni + ", Nombre=" + Nombre + ", Apellido=" + Apellido + ", Sexo=" + Sexo
				+ ", Nacionalidad=" + Nacionalidad + ", FechaNacimiento=" + FechaNacimiento + ", Direccion=" + Direccion
				+ ", Localidad=" + Localidad + ", Provincia=" + Provincia + ", CorreoElectronico=" + CorreoElectronico
				+ ", Telefono=" + Telefono + ", Estado=" + Estado + "]";
	}	
	
}
