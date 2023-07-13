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

import negocioImpl.EspecialidadNegocioImpl;
import negocioImpl.HorariosTrabajoNegocioImpl;
import negocioImpl.MedicoNegocioImpl;
import negocioImpl.PacienteNegocioImpl;
import negocioImpl.PaisNegocioImpl;
import negocioImpl.TelefonoNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
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
			else if (request.getParameter("btn-buscar") != null) {
				listarMedicosFiltro(request);
				RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
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

		MedicoNegocioImpl medicoNegocio = new MedicoNegocioImpl();
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
			    
			    if (AgregarMedico(medicoNegocio, request)) {	
			    	AgregarUsuario(request);
			         dniMedico = request.getParameter("dni").toString();
			         agregarDetallesVerMedico(request, dniMedico, idMedico);
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

			agregarDetallesVerMedico(request, dniMedico, idMedico);
			if(request.getAttribute("estadoHorario") != null) request.setAttribute("estadoHorario", request.getAttribute("estadoHorario"));
			
			RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-editar-medico") != null) {
			agregarDetallesVerMedico(request, dniMedico, idMedico);
			if(request.getAttribute("estadoHorario") != null) request.setAttribute("estadoHorario", request.getAttribute("estadoHorario"));
			request.setAttribute("editar-medico", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-guardar-medico") != null) {
			try {
				if(ModificarMedico(medicoNegocio, request))request.setAttribute("mensaje", "El Médico fue modificado Correctamente!");
				else request.setAttribute("mensaje", "Ocurrió un error al intentar modificar al Médico!");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			agregarDetallesVerMedico(request, dniMedico, idMedico);
			request.setAttribute("editar-medico", true);
			
			RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-eliminar-medico") != null) {
			String mensaje ="";
			if(EliminarMedico(medicoNegocio, dniMedico, idMedico) && EliminarUsuarioMedico(dniMedico)) mensaje ="El médico fue eliminado con exito!";
			else  mensaje ="Hubo un error al intentar eliminar el Médico";
			request.setAttribute("mensaje", mensaje);	
			listarMedicos(request);			
			RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-reactivar-medico") != null) {
			String mensaje ="";
			if(ReactivarMedico(medicoNegocio, dniMedico, idMedico) && ReactivarUsuarioMedico(dniMedico)) mensaje ="El médico fue reactivado con exito!";
			else  mensaje ="Hubo un error al intentar reactivar el Médico";
			request.setAttribute("mensaje", mensaje);	
			listarMedicos(request);			
			RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
			rd.forward(request, response);
		}
		
	}
	
	protected void agregarDetallesVerMedico(HttpServletRequest request, String dniMedico, int idMedico) {
		MedicoNegocioImpl medicoNegocio = new MedicoNegocioImpl();
		Medico medico = medicoNegocio.obtenerMedico(dniMedico);
		agregarDetallesUsuarioMedico(request, dniMedico);
		request.setAttribute("medico", medico);
		agregarListaPaises(request);
		listarHorariosTrabajoPorMedico(request, medico.getIdMedico());
		listarTelefonosPorMedico(request, medico.getDni());
		listarEspecialidadesPorMedico(request, idMedico);
	}
	
	protected void agregarDetallesUsuarioMedico(HttpServletRequest request, String dni) {
		Usuario usuarioMedico = new Usuario();
		UsuarioNegocioImpl uNegocio = new UsuarioNegocioImpl();
		usuarioMedico = uNegocio.obtener(dni);
		request.setAttribute("usuarioMedico", usuarioMedico);
	}
	
	protected void listarMedicosFiltro(HttpServletRequest request) {
		String campo = "";
		String valor = "";
		if(request.getParameter("filtro") != null) campo = request.getParameter("filtro");
		if(request.getParameter("filtro-valor") != null) valor = request.getParameter("filtro-valor");
		
		MedicoNegocioImpl medicoNegocio = new MedicoNegocioImpl();
		ArrayList<Medico> listaMedicos  = new ArrayList<Medico>();
		if(campo.equals("dia")) {
			listaMedicos = medicoNegocio.filtrarMedicosPorDia(valor);
		}
		else if(campo.equals("especialidad")) {
			listaMedicos = medicoNegocio.filtrarMedicosPorEspecialidad(valor);
		}
		else {			
			listaMedicos  = medicoNegocio.filtrarMedicos(campo, valor);		
		}
		request.setAttribute("listaMedicos", listaMedicos);
		listarHorariosTrabajoMedico(request);
		listarEspecialidadesMedico(request);
	}
	
	protected void listarMedicos(HttpServletRequest request) {
		MedicoNegocioImpl medicoNegocio = new MedicoNegocioImpl();
		ArrayList<Medico> listaMedicos  = medicoNegocio.listarMedicos();
		request.setAttribute("listaMedicos", listaMedicos);
		listarHorariosTrabajoMedico(request);
		listarEspecialidadesMedico(request);
	}
	
	protected void listarTelefonosPorMedico(HttpServletRequest request, String dniMedico) {
		TelefonoNegocioImpl tNegocio = new TelefonoNegocioImpl();
		ArrayList<Telefono> listaT = tNegocio.listarPorPersona(dniMedico);
		request.setAttribute("listaTelefonosMedico", listaT);
	}
	
	protected void listarEspecialidadesPorMedico(HttpServletRequest request, int idMedico) {
		EspecialidadNegocioImpl eNegocio = new EspecialidadNegocioImpl();
		ArrayList<Especialidad> listaEspecialidadesMedico = eNegocio.listarEspecialidadesPorMedico(idMedico);
		request.setAttribute("listaEspecialidadesMedico", listaEspecialidadesMedico);
	}
	
	protected void listarHorariosTrabajoPorMedico(HttpServletRequest request, int idMedico) {
		HorariosTrabajoNegocioImpl htNegocio = new HorariosTrabajoNegocioImpl();
		ArrayList<HorariosTrabajo> listaHT = htNegocio.listarPorMedico(idMedico);
		request.setAttribute("listaHorariosMedico", listaHT);
	}
	
	protected void listarHorariosTrabajoMedico(HttpServletRequest request) {
		HorariosTrabajoNegocioImpl htNegocio = new HorariosTrabajoNegocioImpl();
		ArrayList<HorariosTrabajo> listaHT = htNegocio.listar();
		request.setAttribute("listaHT", listaHT);
	}
	
	protected void listarEspecialidadesMedico(HttpServletRequest request) {
		MedicoNegocioImpl espMedNegocio = new MedicoNegocioImpl();
		ArrayList<Medico> listaEspecialidadesMedico = (ArrayList<Medico>) espMedNegocio.listarEspecialidadesMedico();
		request.setAttribute("listaEspMedico", listaEspecialidadesMedico);
	}
	
	protected void agregarListaPaises(HttpServletRequest request) {
		PaisNegocioImpl paisNegocio = new PaisNegocioImpl();
		ArrayList<Pais> listaPaises = (ArrayList<Pais>) paisNegocio.listarPaises();
		request.setAttribute("listaPaises", listaPaises);
	}
	
	
	protected boolean AgregarMedico(MedicoNegocioImpl mNegocio, HttpServletRequest request) throws ParseException {
		Medico nuevoMedico = setearDatosMedico(request);
		boolean agregado = mNegocio.agregar(nuevoMedico);		
		return agregado;
	}
	
	protected boolean ModificarMedico(MedicoNegocioImpl mNegocio, HttpServletRequest request) throws ParseException {
		Medico medico = setearDatosMedico(request);
		return mNegocio.modificar(medico);
	}
	
	protected Medico setearDatosMedico(HttpServletRequest request) throws ParseException {
		Medico medico = new Medico();
		
		medico.setDni(request.getParameter("dni").toString());
		medico.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		medico.setSexo(request.getParameter("sexoSelect"));
		medico.setNombre(request.getParameter("nombre"));
		medico.setApellido(request.getParameter("apellido"));

		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		medico.setNacionalidad(nacionalidad);
		medico.setCorreo(request.getParameter("correo"));

		Domicilio domicilioMedico = new Domicilio();
		domicilioMedico.setDireccion(request.getParameter("direccion"));
		domicilioMedico.setLocalidad(request.getParameter("localidad"));
		domicilioMedico.setProvincia(request.getParameter("provincia"));
		Pais paisDomicilioMedico = new Pais();
		paisDomicilioMedico.setIdPais(Integer.parseInt(request.getParameter("paisSelect")));
		domicilioMedico.setPais(paisDomicilioMedico);
		
		medico.setDomicilio(domicilioMedico);
		
		return medico;
	}
	
	protected boolean AgregarUsuario(HttpServletRequest request) throws ParseException  {
		Usuario usuario = new Usuario();
		usuario.setDni(request.getParameter("dni").toString());
		usuario.setCorreo(request.getParameter("correo"));
		usuario.setPassword(request.getParameter("password").toString());
		usuario.setEsAdministrador(false);
		
		UsuarioNegocioImpl uNegocio = new UsuarioNegocioImpl();
		return uNegocio.agregar(usuario);
	}
	
	protected boolean EliminarMedico(MedicoNegocioImpl mNegocio, String dniMedico, int idMedico) {		
		return mNegocio.eliminar(dniMedico, idMedico);	
	}
	protected boolean EliminarUsuarioMedico(String dniMedico) {		
		UsuarioNegocioImpl uNegocio = new UsuarioNegocioImpl();
		return uNegocio.eliminar(dniMedico);
	}
	
	protected boolean ReactivarMedico(MedicoNegocioImpl mNegocio, String dniMedico, int idMedico) {		
		return mNegocio.reactivar(dniMedico, idMedico);	
	}
	protected boolean ReactivarUsuarioMedico(String dniMedico) {		
		UsuarioNegocioImpl uNegocio = new UsuarioNegocioImpl();
		return uNegocio.reactivar(dniMedico);
	}
}
