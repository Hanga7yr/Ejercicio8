package Conexion;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ORM.AsigProyecto;
import ORM.AsigProyectoId;
import ORM.Empleado;

public class Ejercicio_5 {
	public static void asignar_fecha_antigua() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				ORM.Proyecto p = (ORM.Proyecto)s.createQuery("FROM Proyecto ORDER BY rand()").setMaxResults(1).getSingleResult();
				ORM.Empleado e = (ORM.Empleado)s.createQuery("FROM Empleado ORDER BY rand()").setMaxResults(1).getSingleResult();
				
				ORM.AsigProyectoId agi = new ORM.AsigProyectoId();
				agi.setDniEmp(e.getDni());
				agi.setFInicio(new Date());
				agi.setIdProy(p.getIdProy());
				
				ORM.AsigProyecto ap = new ORM.AsigProyecto();
				
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -1);
				ap.setFFin(c.getTime());
				
				ap.setId(agi);
				ap.setProyecto(p);
				ap.setEmpleado(e);
				
				s.save(ap);
				
				t.commit();
			}catch(RollbackException e) {
				t.rollback();
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un el asigProyecto Creado.");
				t.rollback();
			}
		}
	}
	
	public static void asignar_fecha_antigua_random() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
			
				List<ORM.AsigProyecto> ap = s.createQuery("FROM AsigProyecto ORDER BY rand()").setMaxResults((int) (Math.random()*s.createQuery("FROM AsigProyecto").getResultList().size())).getResultList();
				
				for(ORM.AsigProyecto i : ap) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -1);
					i.setFFin(c.getTime());
					
					s.update(i);
				}
				
				t.commit();
			}catch(RollbackException e) {
				t.rollback();
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un el asigProyecto Creado.");
				t.rollback();
			}
		}
	}
}
