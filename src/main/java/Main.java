import java.util.Date;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Conexion.Ejercicio_2;
import Conexion.Ejercicio_3;
import Conexion.Ejercicio_4;
import Conexion.Ejercicio_5;
import Conexion.Ejercicio_6;
import Conexion.Ejercicio_7;
import Conexion.Ejercicio_8;
import Conexion.HibernateUtil;
import ORM.Empleado;

public class Main {
	public static void main(String[] args) {
		
		
		//Manuel Martín Vázquez
		//Generar_datos(10);
		
		//System.out.println("Crear Empleado");
		//Ejercicio_3.crear_empleado();
		//System.out.println("Crear Proyecto");
		//Ejercicio_3.crear_proyecto();
		//System.out.println("Insertar multiples empleado a un proyecto");
		//Ejercicio_3.insert_empleado_proyecto();
		//System.out.println("Asignar un empleado a multiples proyectos");
		//Ejercicio_3.set_proyecto_empleado();

		//System.out.println("Creación multiples empleados");
		//Ejercicio_2.prueba_empleados();
		//System.out.println("Creación multiples proyectos");
		//Ejercicio_2.prueba_proyectos();
		
		//System.out.println("Asignar datos prof a empleado");
		//Ejercicio_4.asig_datos_prof_empleado();
		//System.out.println("Asignar datos prof a nuevo empleado");
		//Ejercicio_4.asig_datos_prof_empleado(Ejercicio_4.create_empleado());
		
		//System.out.println("Asignar Proyecto a Empleado con fecha ayer");
		//Ejercicio_5.asignar_fecha_antigua();
		//System.out.println("Asignar Proyecto a empleado con fecha ayer random cantidad");
		//Ejercicio_5.asignar_fecha_antigua_random();
		
		//System.out.println("Listar datos Asig Proyecto");
		//Ejercicio_6.mostrar_detalles_asig_proyect();
		
		//System.out.println("Listar datos Asig Proyecto Vigente");
		//Ejercicio_7.mostrar_asig_proyecto_vigente();
		
		//Los anteriores ejercicios ahora no funcionan debido a la modificación de la base de datos.
		//Estan arreglado pero no se si funcionan correctamente
		/*						^
		 * 						|
		 * 						|
		 * 						|
		 * 						|
		 * 						|
		 * 						|
		 * 						|
		 * 				Leer este comentario 
		 */
		
		System.out.println("Ejercicio8: Prueba Crear Empleado");
		Ejercicio_8.crear_empleado();
		System.out.println("Ejercicio8: Prueba Crear Empleado en plantilla");
		Ejercicio_8.crear_empleado_plantilla();
		System.out.println("Ejercicio8: Prueba Crear Proyecto");
		Ejercicio_8.crear_proyecto();
		System.out.println("Ejercicio8: Prueba Asignar Proyecto");
		Ejercicio_8.asignar_proyecto();
		
		
		//System.out.println("Ejercicio8: Prueba Crear Proyecto con nuevo empleado");
		//Ejercicio_8.crear_proyecto_empleado_creado_en_runtime();
		/*
		 *						^
		 *						|
		 *						|
		 *						|
		 *						|
		 *						|
		 *			No se si es necesario
		 */
		
	}

	/*
	public static void Generar_datos(int count) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				System.out.println("Generando datos sobre empleados");
				
				for(int i = 0; i < count; i++) {
					ORM.Empleado e = new ORM.Empleado();
					String dni = "";
					for(int j = 0; j < 8; j++)
						dni += (int)(Math.random()*10);
					dni += (char)(Math.random()*((int)'Z' - (int)'A')+(int)('A'));
					e.setDni(dni);
					e.setNomEmp("Empleado " + i);
					s.save(e);
				}
				
				System.out.println("Generando datos sobre proyecto");
				
				for(int i = 0; i < count; i++) {
					ORM.Proyecto p = new ORM.Proyecto();
					String dni = "";
					for(int j = 0; j < 8; j++)
						dni += (int)Math.random()*10;
					dni += (char)(Math.random()*((int)'Z' - (int)'A')+(int)('A'));
					p.setEmpleado((ORM.Empleado)s.createQuery("FROM Empleado ORDER BY rand()").setMaxResults(1).getSingleResult());
					p.setNomProy("Proyecto " + i);
					p.setFInicio(new Date());
					p.setFFin(new Date());
					s.save(p);
				}
				
				
				t.commit();
			}catch(RollbackException e) {
				t.rollback();
				System.out.println("Error: Haciendo rollback.");
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un Empleado con ese DNI.");
				t.rollback();
			}
		}
	}
	*/
}
