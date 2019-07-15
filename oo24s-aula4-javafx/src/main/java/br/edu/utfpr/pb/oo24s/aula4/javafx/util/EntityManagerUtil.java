package br.edu.utfpr.pb.oo24s.aula4.javafx.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

    private static EntityManagerFactory emf;
    
    public static EntityManager getEntityManager(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory("AulaSwingPU");
        }
        return emf.createEntityManager();
    }
}
