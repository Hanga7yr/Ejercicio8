package Conexion;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Ejercicio_7 {
	public static void mostrar_asig_proyecto_vigente() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			try {
				Transaction t = s.beginTransaction();
				
				Date d = new Date();
				List<ORM.AsigProyecto> lasig = s.createQuery("FROM AsigProyecto").getResultList();
				
				int iter = 1;
				for(ORM.AsigProyecto i : lasig)
					if((i.getFFin() != null && i.getFFin().after(d)) && i.getId().getFInicio().before(d))
						System.out.println(iter++ + ". DNI: " + i.getEmpleado().getDni() + ". ID_PROYECTO: " + i.getProyecto().getIdProy() + ". FECHA_INIT: " + i.getId().getFInicio() + ". FECHA_FIN: " + i.getFFin());
				t.commit();
			}catch(PersistenceException e) {
				System.out.println("Error: Valor de fecha erroneo en una de las filas");
			}
		}
	}
}
