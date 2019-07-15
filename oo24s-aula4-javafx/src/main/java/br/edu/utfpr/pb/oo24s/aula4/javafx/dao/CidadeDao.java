/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cidade;
import java.util.List;
import javax.persistence.Query;

public class CidadeDao extends GenericDao<Cidade, Long>{

    public CidadeDao() {
        super(Cidade.class);
    }
    public List<Cidade> findByCidadeId(Long id) {
        Query q = em.createQuery("Select c from Cidade c Where c.estado.id = :estadoId");
        q.setParameter("estadoId", id);
        return q.getResultList();
    }
}
