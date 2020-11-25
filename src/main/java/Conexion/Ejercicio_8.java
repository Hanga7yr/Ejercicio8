package Conexion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ORM.AsigProyecto;
import ORM.AsigProyectoId;
import ORM.Proyecto;

public class Ejercicio_8 {
	static Scanner entrada = new Scanner(System.in);
	
	public static void crear_empleado() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				String nombre = "";
				String dni = "";
				
				System.out.print("Introduce el nombre: ");
				nombre = entrada.nextLine();
				
				System.out.print("Introduce el DNI: ");
				dni = entrada.nextLine();
				
				List<ORM.Empleado> le = (List<ORM.Empleado>) s.createQuery("FROM Empleado").getResultList();
				
				boolean existe = false;
				for(ORM.Empleado i : le) {
					existe |= i.getDni().equals(dni);
				}
				
				ORM.Empleado e = new ORM.Empleado();
				e.setNomEmp(nombre);
				e.setDni(dni);
				
				if(nombre == "" || dni == "") {
					System.out.println("Error: Datos en blanco");
					t.rollback();
				}else if(!existe) {
					s.save(e);
					
					t.commit();
					System.out.println("Se ha insertado correctamente el empleado");
				}else{
					System.out.println("Error: El empleado con dni " + dni + " ya existe.\nNo realizando inserción.");
					t.rollback();
				}
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un Empleado con ese DNI.");
				t.rollback();
			}finally {}
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void crear_empleado_plantilla() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				System.out.println("Selecciona el empleado a pasar a plantilla: ");
				List<ORM.Empleado> le = (List<ORM.Empleado>)s.createQuery("FROM Empleado").getResultList();
				int iter = 1;
				for(ORM.Empleado i : le)
					System.out.println(iter++ + ". " + i.getNomEmp() + " " + i.getDni());
				
				System.out.print("Introduce el empleado: ");
				int option = -1;
				try {
					option = Integer.parseInt(entrada.nextLine());
				}catch(NumberFormatException e) {
					option = -1;
					System.out.println("La opción seleccionada no es un número.");
					throw new RollbackException();
				}
				
				System.out.println("Inserte el número de empleado que poner a este nuevo empleado.");
				int num_emp = -1;
				
				try {
					num_emp = Integer.parseInt(entrada.nextLine());
					
					ORM.EmpPlantilla e = new ORM.EmpPlantilla();
					e.setNumEmp(num_emp);
					e.setEmpleado(le.get(option-1));
					s.save(e);
				}catch(NumberFormatException e) {
					num_emp = -1;
					System.out.println("No se ha insertado un número.");
					throw new RollbackException();
				}
				
				t.commit();
				System.out.println("Se ha insertado correctamente el empleado");
			}catch(RollbackException e) {
				t.rollback();
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un Empleado en Plantilla con ese Número de Empleado");
				t.rollback();
			}finally {}
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void crear_proyecto() {
		Transaction t = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			t = s.beginTransaction();
			ORM.Proyecto p = new ORM.Proyecto();
			
			System.out.print("Inserte el nombre de proyecto: ");
			p.setNomProy(entrada.nextLine());
			
			System.out.print("Inserte la fecha de inicio (dd-MM-YYYY): ");
			p.setFInicio(new SimpleDateFormat("dd-MM-YYYY").parse(entrada.nextLine()));
			
			System.out.print("Inserte la fecha de fin (dd-MM-YYYY): ");
			p.setFFin(new SimpleDateFormat("dd-MM-YYYY").parse(entrada.nextLine()));
			
			System.out.println("Selecciona el empleado a hacer jefe de proyecto: ");
			List<ORM.EmpPlantilla> le = (List<ORM.EmpPlantilla>)s.createQuery("FROM EmpPlantilla").getResultList();
			
			int iter = 1;
			for(ORM.EmpPlantilla i : le)
				System.out.println(iter++ + ". " + i.getEmpleado().getNomEmp() + " " + i.getEmpleado().getDni());
			
			
			System.out.print("Introduce el empleado: ");
			try {
				p.setEmpPlantilla(le.get(Integer.parseInt(entrada.nextLine())-1));
			}catch(NumberFormatException e) {
				System.out.println("La opción seleccionada no es un número.");
				throw new RollbackException();
			}
			System.out.println(p.getEmpPlantilla().getEmpleado().getDni());
			
			s.save(p);
			
			t.commit();
			System.out.println("Se ha insertado correctamente el proyecto");
		}catch(RollbackException e) {
			t.rollback();
		}catch(PersistenceException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Error: Ya existe un Proyecto con ese ");
			t.rollback();
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void crear_proyecto_empleado_creado_en_runtime() {
		Transaction t = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			t = s.beginTransaction();
			ORM.Proyecto p = new ORM.Proyecto();
			
			System.out.print("Inserte el nombre de proyecto: ");
			p.setNomProy(entrada.nextLine());
			
			System.out.print("Inserte la fecha de inicio (dd-MM-YYYY): ");
			p.setFInicio(new SimpleDateFormat("dd-MM-YYYY").parse(entrada.nextLine()));
			
			System.out.print("Inserte la fecha de fin (dd-MM-YYYY): ");
			p.setFFin(new SimpleDateFormat("dd-MM-YYYY").parse(entrada.nextLine()));
			
			String nombre = "";
			String dni = "";
			
			System.out.print("Introduce el nombre: ");
			nombre = entrada.nextLine();
			
			System.out.print("Introduce el DNI: ");
			dni = entrada.nextLine();
			
			List<ORM.Empleado> le = (List<ORM.Empleado>) s.createQuery("FROM Empleado").getResultList();
			
			boolean existe = false;
			for(ORM.Empleado i : le) {
				existe |= i.getDni().equals(dni);
			}
			
			ORM.Empleado e = new ORM.Empleado();
			e.setNomEmp(nombre);
			e.setDni(dni);
			

			System.out.println("Inserte el número de empleado que poner a este nuevo empleado.");
			int num_emp = Integer.parseInt(entrada.nextLine());
			
			ORM.EmpPlantilla em = new ORM.EmpPlantilla();
			em.setNumEmp(num_emp);
			em.setEmpleado(e);
			s.save(e);
			s.save(em);
			
			try {
				p.setEmpPlantilla(em);
			}catch(NumberFormatException ex) {
				System.out.println("La opción seleccionada no es un número.");
				throw new RollbackException();
			}
			System.out.println(p.getEmpPlantilla().getEmpleado().getDni());
			
			s.save(p);
			
			t.commit();
			System.out.println("Se ha insertado correctamente el proyecto, Empleado y Emp en Plantilla");
		}catch(RollbackException e) {
			t.rollback();
		}catch(PersistenceException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Error: Ya existe un Proyecto con ese ");
			t.rollback();
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	public static void asignar_proyecto() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				List<ORM.Proyecto> lp = (List<ORM.Proyecto>) s.createQuery("FROM Proyecto order by Id_proy").getResultList();
				List<ORM.Empleado> le = (List<ORM.Empleado>) s.createQuery("FROM Empleado order by DNI").getResultList();
				
				ORM.Proyecto p = null;
				
				ORM.Empleado e = null;
				
				System.out.println("Seleccione el empleado: ");
				for(int i = 0; i < le.size(); i++)
					System.out.println((i+1) + ". " + le.get(i).getNomEmp() + " " + le.get(i).getDni());
				
				int opcion = Integer.parseInt(entrada.nextLine());
				if(opcion >= 1 && opcion <= le.size()) {
					e = le.get(opcion - 1);
				}
				
				System.out.println("Seleccione el proyecto: ");
				for(int i = 0; i < lp.size(); i++)
					System.out.println((i+1) + ". " + lp.get(i).getNomProy() + " " + lp.get(i).getIdProy());
				
				int opcion2 = Integer.parseInt(entrada.nextLine());
				if(opcion2 >= 1 && opcion2 <= lp.size())
					p = lp.get(opcion2 - 1);
				
				ORM.AsigProyectoId pid = new ORM.AsigProyectoId();
				pid.setFInicio(p.getFInicio());
				pid.setDniEmp(e.getDni());
				pid.setIdProy(p.getIdProy());
				
				ORM.AsigProyecto ap = new ORM.AsigProyecto();
				ap.setId(pid);
				ap.setFFin(p.getFFin());
				s.save(ap);
				
				t.commit();
				System.out.println("Se ha asignado correctamente el empleado al proyecto");
			}catch(RollbackException e) {
				t.rollback();
			}catch(PersistenceException e) {
				System.out.println("Error: El empleado ya forma parte del proyecto");
				t.rollback();
			}finally {}
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
