package serverlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.HorariosTrabajoDAOImpl;
import dao.impl.MedicoDAOImpl;
import dao.impl.PacienteDAOImpl;
import dao.impl.PaisDAOImpl;
import dao.impl.TelefonoDAOImpl;
import dao.impl.UsuarioDAOImpl;
import dominio.Cobertura;
import dominio.Domicilio;
import dominio.Especialidad;
import dominio.HorariosTrabajo;
import dominio.Medico;
import dominio.Paciente;
import dominio.Pais;
import dominio.Telefono;
import dominio.Usuario;
import util.ValidadorFormulario;

@WebServlet("/serverletsMedicos")
public class serverletsMedicos extends HttpServlet   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		   
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			
			if(request.getParameter("btn-nuevo-medico") != null) {
				agregarListaPaises(request);
				RequestDispatcher rd = request.getRequestDispatcher("InsertarMedico.jsp");
				rd.forward(request, response);
			}

			else {
				listarMedicos(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
				rd.forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String dniMedico ="";
		if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico").toString();
		
		int idMedico = 0;
		if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));

		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		if(request.getParameter("btn-agregar-medico") != null) {
			try {
			    ValidadorFormulario validar = new ValidadorFormulario();
			    
			    if (validar.existeDni(request.getParameter("dni").toString())) {
			        request.setAttribute("mensaje", "Existe registro con el DNI ingresado");
			        RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
			        rd.forward(request, response);
			        return;
			    }
			    
			    if (validar.existeEmail(request.getParameter("correo"))) {
			        request.setAttribute("mensaje", "Existe registro con el Email ingresado");
			        RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
			        rd.forward(request, response);
			        return;
			    }
			    
			    if (AgregarMedico(medicoDao, request)) {	
			    	AgregarUsuario(request);
			         dniMedico = request.getParameter("dni").toString();
			         agregarDetallesVerMedico(request, dniMedico);
						RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
						rd.forward(request, response);

			    }
			} catch (ParseException e) {
			    e.printStackTrace();
			    request.setAttribute("mensaje", "Error al procesar la solicitud");
			    RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
			    rd.forward(request, response);
			    return;
			}
		}
		else if(request.getParameter("btn-ver-medico") != null) {

			agregarDetallesVerMedico(request, dniMedico);
			if(request.getAttribute("estadoHorario") != null) request.setAttribute("estadoHorario", request.getAttribute("estadoHorario"));
			
			RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-editar-medico") != null) {
			
		}
		else if(request.getParameter("btn-eliminar-medico") != null) {
			String mensaje ="";
			if(EliminarMedico(medicoDao, dniMedico, idMedico) && EliminarUsuarioMedico(dniMedico)) mensaje ="El médico fue eliminado con exito!";
			else  mensaje ="Hubo un error al intentar eliminar el Médico";
			request.setAttribute("mensaje", mensaje);	
			listarMedicos(request);			
			RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
			rd.forward(request, response);
		}
		
	}
	
	protected void agregarDetallesVerMedico(HttpServletRequest request, String dniMedico) {
		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		Medico medico = medicoDao.obtenerMedico(dniMedico);
		request.setAttribute("medico", medico);
		agregarListaPaises(request);
		listarHorariosTrabajoPorMedico(request, medico.getIdMedico());
		listarTelefonosPorMedico(request, medico.getDni());
	}
	
	protected void listarMedicos(HttpServletRequest request) {
		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		ArrayList<Medico> listaMedicos  = medicoDao.listarMedicos();
		request.setAttribute("listaMedicos", listaMedicos);
		System.out.println(listaMedicos.size());
		listarHorariosTrabajoMedico(request);
	}
	
	protected void listarTelefonosPorMedico(HttpServletRequest request, String dniMedico) {
		TelefonoDAOImpl tDao = new TelefonoDAOImpl();
		ArrayList<Telefono> listaT = tDao.listarPorPersona(dniMedico);
		request.setAttribute("listaTelefonosMedico", listaT);
	}
	
	protected void listarHorariosTrabajoPorMedico(HttpServletRequest request, int idMedico) {
		HorariosTrabajoDAOImpl htDao = new HorariosTrabajoDAOImpl();
		ArrayList<HorariosTrabajo> listaHT = htDao.listarPorMedico(idMedico);
		request.setAttribute("listaHorariosMedico", listaHT);
	}
	
	protected void listarHorariosTrabajoMedico(HttpServletRequest request) {
		HorariosTrabajoDAOImpl htDao = new HorariosTrabajoDAOImpl();
		ArrayList<HorariosTrabajo> listaHT = htDao.listar();
		request.setAttribute("listaHT", listaHT);
	}
	
	protected void listarEspecialidadesMedico(HttpServletRequest request) {
		MedicoDAOImpl espMedDao = new MedicoDAOImpl();
		ArrayList<Medico> listaEspecialidadesMedico = (ArrayList<Medico>) espMedDao.listarEspecialidadesMedico();
		request.setAttribute("listaEspMedico", listaEspecialidadesMedico);
	}
	
	protected void agregarListaPaises(HttpServletRequest request) {
		PaisDAOImpl paisDao = new PaisDAOImpl();
		ArrayList<Pais> listaPaises = (ArrayList<Pais>) paisDao.listarPaises();
		request.setAttribute("listaPaises", listaPaises);
	}
	
	
	protected boolean AgregarMedico(MedicoDAOImpl mDao, HttpServletRequest request) throws ParseException {
		Medico nuevoMedico = new Medico();
		nuevoMedico.setDni(request.getParameter("dni").toString());
		nuevoMedico.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		nuevoMedico.setSexo(request.getParameter("sexoSelect"));
		nuevoMedico.setNombre(request.getParameter("nombre"));
		nuevoMedico.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		nuevoMedico.setNacionalidad(nacionalidad);
		nuevoMedico.setCorreo(request.getParameter("correo"));
		
		Domicilio domicilioMedico = new Domicilio();
		domicilioMedico.setDireccion(request.getParameter("direccion"));
		domicilioMedico.setLocalidad(request.getParameter("localidad"));
		domicilioMedico.setProvincia(request.getParameter("provincia"));
		Pais paisDomicilioMedico = new Pais();
		paisDomicilioMedico.setIdPais(Integer.parseInt(request.getParameter("paisSelect")));
		domicilioMedico.setPais(paisDomicilioMedico);
		
		nuevoMedico.setDomicilio(domicilioMedico);
		boolean agregado = mDao.agregar(nuevoMedico);		
		return agregado;
	}
	
	protected boolean AgregarUsuario(HttpServletRequest request) throws ParseException  {
		Usuario usuario = new Usuario();
		usuario.setDni(request.getParameter("dni").toString());
		usuario.setCorreo(request.getParameter("correo"));
		usuario.setPassword(request.getParameter("password").toString());
		usuario.setEsAdministrador(false);
		
		UsuarioDAOImpl uDao = new UsuarioDAOImpl();
		return uDao.agregar(usuario);
	}
	
	protected boolean EliminarMedico(MedicoDAOImpl mDao, String dniMedico, int idMedico) {		
		return mDao.eliminar(dniMedico, idMedico);	
	}
	protected boolean EliminarUsuarioMedico(String dniMedico) {		
		UsuarioDAOImpl uDao = new UsuarioDAOImpl();
		return uDao.eliminar(dniMedico);
	}
}
