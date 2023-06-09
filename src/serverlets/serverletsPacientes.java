package serverlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import negocioImpl.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocioImpl.PacienteNegocioImpl;
import dominio.Cobertura;
import dominio.Domicilio;
import dominio.Paciente;
import dominio.Pais;
import dominio.Telefono;
import dominio.Usuario;
import negocioImpl.CoberturaNegocioImpl;

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
			else if (request.getParameter("btn-buscar") != null) {
				listarPacientesFiltro(request);
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);
			}
			else {
				listarPacientes(request);		
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);
			}		
		}
	}

	private void listarPacientesFiltro(HttpServletRequest request) {
		String campo = "";
		String valor = "";
		if(request.getParameter("filtro") != null) campo = request.getParameter("filtro");
		if(request.getParameter("filtro-valor") != null) valor = request.getParameter("filtro-valor");
		
		PacienteNegocioImpl pNegocio = new PacienteNegocioImpl();
		ArrayList<Paciente> listaPacientes  = pNegocio.filtrarPacientes(campo, valor);
	
		request.setAttribute("listaPacientes", listaPacientes);
	}
	
	private void listarPacientes(HttpServletRequest request) {
		PacienteNegocioImpl pNegocio = new PacienteNegocioImpl();
		ArrayList<Paciente> listaPacientes  = (ArrayList<Paciente>) pNegocio.listarPacientes();
		
		request.setAttribute("listaPacientes", listaPacientes);
	}
	
	protected void agregarListaPaises(HttpServletRequest request) {
		PaisNegocioImpl paisNegocio = new PaisNegocioImpl();
		ArrayList<Pais> listaPaises = (ArrayList<Pais>) paisNegocio.listarPaises();
		request.setAttribute("listaPaises", listaPaises);
	}
	
	protected void agregarListaCoberturas(HttpServletRequest request) {
		CoberturaNegocioImpl coberturaNegocio = new CoberturaNegocioImpl();
		ArrayList<Cobertura> listaCoberturas = (ArrayList<Cobertura>) coberturaNegocio.listarCoberturas();
		request.setAttribute("listaCoberturas", listaCoberturas);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 String dniPaciente ="";
		if(request.getParameter("dniPaciente") != null) dniPaciente = request.getParameter("dniPaciente").toString();
		PacienteNegocioImpl pNegocio = new PacienteNegocioImpl();
		
		if(request.getParameter("btn-ver-paciente") != null) 
		{
			Paciente paciente = pNegocio.obtenerPaciente(dniPaciente);
			request.setAttribute("paciente", paciente);
			if(request.getParameter("idTurno") != null) {
				int idTurno = 0;
				idTurno = Integer.parseInt(request.getParameter("idTurno").toString());
				request.setAttribute("idTurno", idTurno);
			}
			
			agregarListaPaises(request);
			agregarListaCoberturas(request);
		
			RequestDispatcher rd = request.getRequestDispatcher("EditarPaciente.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-editar-paciente") != null) {
			Paciente paciente = pNegocio.obtenerPaciente(dniPaciente);
			request.setAttribute("paciente", paciente);
			
			agregarListaPaises(request);
			agregarListaCoberturas(request);
			request.setAttribute("editar-paciente", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditarPaciente.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-guardar-paciente") != null) {
			dniPaciente = request.getParameter("dni").toString();
			int modificado = 0;
			try {
				modificado = ModificarPaciente(pNegocio, request);
				if(modificado == 1)request.setAttribute("mensaje", "El Paciente fue modificado Correctamente!");
				else request.setAttribute("mensaje", "Ocurri� un error al intentar modificar al Paciente!");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Paciente paciente = pNegocio.obtenerPaciente(dniPaciente);
			request.setAttribute("paciente", paciente);
			
			agregarListaPaises(request);
			agregarListaCoberturas(request);
		
			request.setAttribute("editar-paciente", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditarPaciente.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-eliminar-paciente") != null) {
			
			EliminarPaciente(pNegocio, dniPaciente);
			listarPacientes(request);
			request.setAttribute("mensajeExito", "El Paciente fue eliminado Correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-reactivar-paciente") != null) {
			
			ReactivarPaciente(pNegocio, dniPaciente);
			listarPacientes(request);
			request.setAttribute("mensajeExito", "El Paciente fue reactivado Correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-agregar-paciente") != null) {
			try {				
				if(!AgregarPaciente(pNegocio, request)) {					
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

	protected void EliminarPaciente(PacienteNegocioImpl pNegocio, String dniPaciente) {		
		pNegocio.eliminar(dniPaciente);	
	}

	protected void ReactivarPaciente(PacienteNegocioImpl pNegocio, String dniPaciente) {		
		pNegocio.reactivar(dniPaciente);	
	}
	
	protected boolean AgregarPaciente(PacienteNegocioImpl pNegocio, HttpServletRequest request) throws ParseException {
		
		if(pNegocio.existe(request.getParameter("dni").toString())){
			return false;
		}
		
		Paciente nuevoPaciente = setearDatosPaciente(pNegocio, request);
		
		boolean agregado = pNegocio.agregar(nuevoPaciente);
		return agregado;
	}
	

	
	protected int ModificarPaciente(PacienteNegocioImpl pNegocio, HttpServletRequest request) throws ParseException {
		Paciente UpdatePaciente = setearDatosPaciente(pNegocio, request);
		boolean Modificado = pNegocio.modificar(UpdatePaciente) == 1;
		
		return 1;
	}
	
	protected Paciente setearDatosPaciente(PacienteNegocioImpl pNegocio, HttpServletRequest request) throws ParseException {
		Paciente paciente = new Paciente();
		paciente.setDni(request.getParameter("dni").toString());

		paciente.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		paciente.setSexo(request.getParameter("sexoSelect"));
		paciente.setNombre(request.getParameter("nombre"));
		paciente.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		paciente.setNacionalidad(nacionalidad);
		paciente.setCorreo(request.getParameter("correo"));
		
		Telefono telefono = new Telefono();
		telefono.setDni(paciente.getDni());
		telefono.setTelefono(request.getParameter("telefono"));
		paciente.setTelefono(telefono);
		
		Domicilio domicilioPaciente = new Domicilio();
		domicilioPaciente.setDireccion(request.getParameter("direccion"));
		domicilioPaciente.setLocalidad(request.getParameter("localidad"));
		domicilioPaciente.setProvincia(request.getParameter("provincia"));
		Pais paisDomicilioPaciente = new Pais();
		paisDomicilioPaciente.setIdPais(Integer.parseInt(request.getParameter("paisSelect")));
		domicilioPaciente.setPais(paisDomicilioPaciente);
		
		paciente.setDomicilio(domicilioPaciente);
		
		Cobertura cobertura = new Cobertura();
		cobertura.setId(Integer.parseInt(request.getParameter("coberturaSelect").toString()));
		paciente.setCobertura(cobertura);
		
		return paciente;
	}
	
}
