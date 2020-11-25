package Conexion;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ORM_conexion {

  public static void main(String[] args) {

    try (Session s = HibernateUtil.getSessionFactory().openSession()) {
      Transaction t = null;
      
      try {
        t = s.beginTransaction();

        ORM.Empleado et = new ORM.Empleado();
        et.setDni("89674341K");
        et.setNomEmp("aadf");
        s.save(et);
        
        ORM.EmpPlantilla e = new ORM.EmpPlantilla();
        e.setEmpleado(et);
        s.save(e);
        
        Date d = new Date();
        ORM.Proyecto p = new ORM.Proyecto();
        p.setIdProy(4);
        p.setNomProy("1");
        p.setEmpPlantilla(e);
        p.setFInicio(d);
        p.setFFin(d);
        s.save(p);

        ORM.AsigProyectoId api = new ORM.AsigProyectoId();
        api.setDniEmp(et.getDni());
        api.setFInicio(new Date());
        api.setIdProy(p.getIdProy());
        
        ORM.AsigProyecto ap = new ORM.AsigProyecto();
        ap.setId(api);
        s.save(ap);
        t.commit();
      } catch (Exception e) {
        e.printStackTrace(System.err);
        if (t != null) {
          t.rollback();
        }
      }
    }
  }
  
}
