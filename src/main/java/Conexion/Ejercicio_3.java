package Conexion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.build.AllowSysOut;

import ORM.AsigProyecto;
import ORM.AsigProyectoId;
import ORM.Proyecto;

public class Ejercicio_3 {
	
	static Scanner entrada = new Scanner(System.in);
	public static void crear_proyecto() {
		
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				List<ORM.EmpPlantilla> liste = (List<ORM.EmpPlantilla>) s.createQuery("FROM EmpPlantilla order by DNI").getResultList();
				
				
				String nombre = "";
				int id = 0;
				Date fechInit = new Date();
				ORM.EmpPlantilla e = null;
				
				System.out.print("Introduce el nombre de proyecto: ");
				nombre = entrada.nextLine();
				
				System.out.print("Introduce la fecha(dd-MM-YYYY): ");
				fechInit = new SimpleDateFormat("dd-MM-YYYY").parse(entrada.nextLine());
				
				System.out.println("Seleccione el jefe: ");
				for(int i = 0; i < liste.size(); i++)
					System.out.println((i+1) + ". " + liste.get(i).getEmpleado().getNomEmp() + " " + liste.get(i).getEmpleado().getDni());
				
				int opcion = Integer.parseInt(entrada.nextLine());
				if(opcion >= 1 && opcion <= liste.size()) {
					e = liste.get(opcion - 1);
				}
				
				ORM.Proyecto p = new ORM.Proyecto();
				
				p.setEmpPlantilla(e);
				p.setNomProy(nombre);
				p.setFInicio(fechInit);
				
				s.save(p);
				
				t.commit();
				
		        System.out.println("Se ha insertado correctamente el proyecto");
			}catch(ParseException ex) {
				System.out.println("Error: La fecha se ha introducido erroneamente.\nSaliendo de la inserción");
			}catch(NumberFormatException ex) {
				System.out.println("Error: No se ha insertado un número para la selección.");
			}
		}catch(Exception e) {
			System.out.println("Error:  " + e.getMessage());
		}
		
	}
	
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
				
				if(!existe) {
					s.save(e);
					
					t.commit();
					System.out.println("Se ha insertado correctamente el empleado");
				}else {
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
	
	public static void insert_empleado_proyecto() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
			
				List<ORM.Proyecto> lp = (List<ORM.Proyecto>) s.createQuery("FROM Proyecto order by Id_proy").getResultList();
				List<ORM.Empleado> le = (List<ORM.Empleado>) s.createQuery("FROM Empleado order by DNI").getResultList();
				
				List<ORM.Empleado> liste = new ArrayList<ORM.Empleado>();
				
				ORM.Proyecto p = null;
				
				System.out.println("Seleccione el proyecto: ");
				for(int i = 0; i < lp.size(); i++)
					System.out.println((i+1) + ". " + lp.get(i).getNomProy() + " " + lp.get(i).getIdProy());
				
				int opcion = Integer.parseInt(entrada.nextLine());
				if(opcion >= 1 && opcion <= lp.size()) {
					p = lp.get(opcion - 1);
				}
				
				boolean salir = false;
				do {
					System.out.println("Inserte -1 para salir.");
					System.out.println("Seleccione el jefe: ");
					for(int i = 0; i < le.size(); i++)
						System.out.println((i+1) + ". " + le.get(i).getNomEmp() + " " + le.get(i).getDni());
					
					int opcion2 = Integer.parseInt(entrada.nextLine());
					if(opcion2 >= 1 && opcion2 <= le.size()) {
						liste.add(le.get(opcion2 - 1));
					}else if(opcion2 == -1)
						salir = true;
				}while(!salir);
				
				for(ORM.Empleado i : liste)
					insert_empleado_proyecto(i, p);
				
				t.commit();
			}catch(NumberFormatException ex) {
				System.out.println("Error: No se ha insertado un número para la selección.");
			}
		}catch(Exception ex) {
			
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public static void set_proyecto_empleado() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
		Transaction t = null;
			try {
				t = s.beginTransaction();
			
				List<ORM.Proyecto> lp = (List<ORM.Proyecto>) s.createQuery("FROM Proyecto order by Id_proy").getResultList();
				List<ORM.Empleado> le = (List<ORM.Empleado>) s.createQuery("FROM Empleado order by DNI").getResultList();
				
				List<ORM.Proyecto> listp = new ArrayList<ORM.Proyecto>();
				
				ORM.Empleado e = null;
				
				System.out.println("Seleccione el empleado: ");
				for(int i = 0; i < le.size(); i++)
					System.out.println((i+1) + ". " + le.get(i).getNomEmp() + " " + le.get(i).getDni());
				
				int opcion = Integer.parseInt(entrada.nextLine());
				if(opcion >= 1 && opcion <= le.size()) {
					e = le.get(opcion - 1);
				}
				
				boolean salir = false;
				do {
					System.out.println("Inserte -1 para salir.");
					System.out.println("Seleccione el proyecto: ");
					for(int i = 0; i < lp.size(); i++)
						System.out.println((i+1) + ". " + lp.get(i).getNomProy() + " " + lp.get(i).getIdProy());
					
					int opcion2 = Integer.parseInt(entrada.nextLine());
					if(opcion2 >= 1 && opcion2 <= lp.size()) {
						listp.add(lp.get(opcion2 - 1));
					}else if(opcion2 == -1)
						salir = true;
				}while(!salir);
				
				
				for(ORM.Proyecto i: listp)
					insert_empleado_proyecto(e, i);
				
				t.commit();
			}catch(NumberFormatException ex) {
				System.out.println("Error: No se ha insertado un número para la selección.");
			}
		}catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	

	
	public static void insert_empleado_proyecto(ORM.Empleado e, ORM.Proyecto p) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				List<ORM.AsigProyecto> le = (List<ORM.AsigProyecto>)s.createQuery("FROM AsigProyecto").getResultList();
				
				boolean existe = false;
				for(ORM.AsigProyecto i : le){
					existe |= (i.getEmpleado().getDni().equals(e.getDni()) && i.getProyecto().getIdProy() == p.getIdProy());
				}
				
				if(!existe) {
					ORM.AsigProyectoId id = new ORM.AsigProyectoId();
					
					id.setDniEmp(e.getDni());
					id.setIdProy(p.getIdProy());
					id.setFInicio(p.getFInicio());
					
					ORM.AsigProyecto asig = new ORM.AsigProyecto();
					asig.setId(id);
					s.save(asig);
					
					t.commit();
					
					System.out.println("Se ha insertado correctamente el empleado al proyecto");
				}else {
					System.out.println("Error: El empleado ya esta enlazado al proyecto.\nNo se realizara la inserción.");
					t.rollback();
				}
			}finally {}
		}catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
}
