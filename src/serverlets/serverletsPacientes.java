package serverlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.impl.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.impl.PacienteDAOImpl;
import dominio.Cobertura;
import dominio.Domicilio;
import dominio.Paciente;
import dominio.Pais;
import dominio.Telefono;
import dominio.Usuario;

@WebServlet("/serverletsPacientes")
public class serverletsPacientes extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	
	public serverletsPacientes() {}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		   
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			
			if(request.getParameter("btn-agregar-paciente") != null) {
				agregarListaPaises(request);
				agregarListaCoberturas(request);
			
				RequestDispatcher rd = request.getRequestDispatcher("InsertarPaciente.jsp");
				rd.forward(request, response);
			}
			else {
				listarPacientes(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);

				if(request.getParameter("btnBuscarPacientes") != null) {
					//filtrado
			}		
			}
		}
		
	}
	
	private void listarPacientes(HttpServletRequest request) {
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
		
		request.setAttribute("listaPacientes", listaPacientes);
		
	}
	
	protected void agregarListaPaises(HttpServletRequest request) {
		PaisDAOImpl paisDao = new PaisDAOImpl();
		ArrayList<Pais> listaPaises = (ArrayList<Pais>) paisDao.listarPaises();
		request.setAttribute("listaPaises", listaPaises);
	}
	
	protected void agregarListaCoberturas(HttpServletRequest request) {
		CoberturaDAOImpl coberturaDao = new CoberturaDAOImpl();
		ArrayList<Cobertura> listaCoberturas = (ArrayList<Cobertura>) coberturaDao.listarCoberturas();
		request.setAttribute("listaCoberturas", listaCoberturas);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 String dniPaciente ="";
		if(request.getParameter("dniPaciente") != null) dniPaciente = request.getParameter("dniPaciente").toString();
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		
		if(request.getParameter("btn-ver-paciente") != null) 
		{
			Paciente paciente = pDao.obtenerPaciente(dniPaciente);
			request.setAttribute("paciente", paciente);
			
			agregarListaPaises(request);
			agregarListaCoberturas(request);
		
			RequestDispatcher rd = request.getRequestDispatcher("EditarPaciente.jsp");
			rd.forward(request, response);
		}
		
		else if(request.getParameter("btn-editar-paciente") != null) {
			try {
				ModificarPaciente(pDao, request);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else if(request.getParameter("btn-eliminar-paciente") != null) {
			
			EliminarPaciente(pDao, dniPaciente);
			listarPacientes(request);
			request.setAttribute("mensajeExito", "El Paciente fue eliminado Correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-agregar-paciente") != null) {
			try {				
				if(!AgregarPaciente(pDao, request)) {					
					request.setAttribute("mensajeError", "Ya existe un registro con el DNI ingresado.");					
					listarPacientes(request);
						
					RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
					rd.forward(request, response);
				}	
				else {
					request.setAttribute("mensajeExito", "Registro dado de alta con exito ");
					listarPacientes(request);
						
					RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
					rd.forward(request, response);
				}
				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}		
	}

	protected void EliminarPaciente(PacienteDAOImpl pDao, String dniPaciente) {		
		pDao.eliminar(dniPaciente);	
	}
	
	protected boolean AgregarPaciente(PacienteDAOImpl pDao, HttpServletRequest request) throws ParseException {
		
		Paciente nuevoPaciente = new Paciente();
		nuevoPaciente.setDni(request.getParameter("dni").toString());
		if(pDao.existe(nuevoPaciente.getDni())){
			return false;
		}
		nuevoPaciente.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		nuevoPaciente.setSexo(request.getParameter("sexoSelect"));
		nuevoPaciente.setNombre(request.getParameter("nombre"));
		nuevoPaciente.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		nuevoPaciente.setNacionalidad(nacionalidad);
		nuevoPaciente.setCorreo(request.getParameter("correo"));
		
		Telefono telefono = new Telefono();
		telefono.setDni(nuevoPaciente.getDni());
		telefono.setTelefono(request.getParameter("telefono"));
		nuevoPaciente.setTelefono(telefono);
		
		Domicilio domicilioPaciente = new Domicilio();
		domicilioPaciente.setDireccion(request.getParameter("direccion"));
		domicilioPaciente.setLocalidad(request.getParameter("localidad"));
		domicilioPaciente.setProvincia(request.getParameter("provincia"));
		
		nuevoPaciente.setDomicilio(domicilioPaciente);
		
		Cobertura cobertura = new Cobertura();
		cobertura.setId(Integer.parseInt(request.getParameter("coberturaSelect").toString()));
		nuevoPaciente.setCobertura(cobertura);
		
		boolean agregado = pDao.agregar(nuevoPaciente);
		return agregado;
	}
	

	
	protected boolean ModificarPaciente(PacienteDAOImpl pDao, HttpServletRequest request) throws ParseException {
		Paciente UpdatePaciente = new Paciente();
		UpdatePaciente.setDni(request.getParameter("dni").toString());
		UpdatePaciente.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		System.out.println(UpdatePaciente.getFechaNacimiento());
		
		UpdatePaciente.setSexo(request.getParameter("sexoSelect"));
		UpdatePaciente.setNombre(request.getParameter("nombre"));
		UpdatePaciente.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		
		UpdatePaciente.setCorreo(request.getParameter("correo"));
		
		Telefono telefono = new Telefono();
		telefono.setTelefono(request.getParameter("telefono"));
		UpdatePaciente.setTelefono(telefono);
		
		Domicilio domicilioPaciente = new Domicilio();
		domicilioPaciente.setDireccion(request.getParameter("direccion"));
		domicilioPaciente.setLocalidad(request.getParameter("localidad"));
		domicilioPaciente.setProvincia(request.getParameter("provincia"));
		
		UpdatePaciente.setDomicilio(domicilioPaciente);
		
		Cobertura cobertura = new Cobertura();
		cobertura.setId(Integer.parseInt(request.getParameter("coberturaSelect").toString()));
		UpdatePaciente.setCobertura(cobertura);
		
		boolean Modificado = pDao.modificar(UpdatePaciente);
		
		return Modificado;
	}
	
}
