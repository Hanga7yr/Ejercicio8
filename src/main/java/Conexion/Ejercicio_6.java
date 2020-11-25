package Conexion;

import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Ejercicio_6 {
	public static void mostrar_detalles_asig_proyect() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			try {
				List<ORM.AsigProyecto> lasig = s.createQuery("FROM AsigProyecto").getResultList();
				
				int iter = 1;
				for(ORM.AsigProyecto i : lasig) {
					System.out.println(iter++ + ". DNI: " + i.getEmpleado().getDni() + ". ID_PROYECTO: " + i.getProyecto().getIdProy() + ". FECHA_INIT: " + i.getId().getFInicio() + ". FECHA_FIN: " + i.getFFin());
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
