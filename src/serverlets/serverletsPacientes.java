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
			
			listarPacientes(request);
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);

				if(request.getParameter("btnBuscarPacientes") != null) {
					//filtrado
			}
		}
		
	}
	
	private void listarPacientes(HttpServletRequest request) {
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
		
		request.setAttribute("listaPacientes", listaPacientes);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		if(request.getParameter("btn-ver-paciente") != null) 
		{
			String dniPaciente = request.getParameter("dniPaciente").toString();
			System.out.println("entrooo ver" + dniPaciente);
		}
		
		else if(request.getParameter("btn-editar-paciente") != null) {
			String dniPaciente =request.getParameter("dniPaciente").toString();
			System.out.println("entrooo editar");
		}
		else if(request.getParameter("btn-eliminar-paciente") != null) {
			String dniPaciente = request.getParameter("dniPaciente").toString();
			EliminarPaciente(pDao, dniPaciente);
		}
		else if(request.getParameter("btn-agregar-paciente") != null) {
			try {
				AgregarPaciente(pDao, request);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		listarPacientes(request);
		RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
		rd.forward(request, response);
	}

	protected void EliminarPaciente(PacienteDAOImpl pDao, String dniPaciente) {		
		pDao.eliminar(dniPaciente);	
	}
	
	protected boolean AgregarPaciente(PacienteDAOImpl pDao, HttpServletRequest request) throws ParseException {
		Paciente nuevoPaciente = new Paciente();
		nuevoPaciente.setDni(request.getParameter("dni").toString());
		nuevoPaciente.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		System.out.println(nuevoPaciente.getFechaNacimiento());
		
		nuevoPaciente.setSexo(request.getParameter("sexoSelect"));
		nuevoPaciente.setNombre(request.getParameter("nombre"));
		nuevoPaciente.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		
		nuevoPaciente.setCorreo(request.getParameter("correo"));
		
		Telefono telefono = new Telefono();
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
}
