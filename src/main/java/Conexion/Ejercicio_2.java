package Conexion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Ejercicio_2 {
	public static void prueba_proyectos() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				
				for(int i = 0; i < (Math.random() + 1)*10; i++) {
					ORM.Proyecto p = new ORM.Proyecto();
					p.setEmpPlantilla((ORM.EmpPlantilla)s.createQuery("FROM EmpPlantilla order by rand()").setMaxResults(1).getSingleResult());
					p.setNomProy("Prueba " + i);
					p.setFInicio(new SimpleDateFormat("dd-MM-YYYY").parse(i + "-01-2000" ));
					
					s.save(p);
				}

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
	
	public static void prueba_empleados() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				
				for(int i = 0; i < (Math.random() + 1)*10; i++) {
					ORM.Empleado e = new ORM.Empleado();
					e.setNomEmp("Prueba " + i);
					e.setDni("12345" + i);
					
					s.save(e);
				}

				t.commit();
				
		        System.out.println("Se ha insertado correctamente el proyecto");
			}catch(NumberFormatException ex) {
				System.out.println("Error: No se ha insertado un número para la selección.");
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un Empleado con ese DNI.");
				t.rollback();
			}
		}catch(Exception e) {
			System.out.println("Error:  " + e.getMessage());
		}
		
	}
}
