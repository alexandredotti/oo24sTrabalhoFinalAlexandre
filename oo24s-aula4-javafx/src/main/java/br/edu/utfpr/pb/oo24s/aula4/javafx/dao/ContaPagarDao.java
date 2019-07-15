
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.ContaPagar;
import br.edu.utfpr.pb.oo24s.aula4.javafx.model.TipoPagamento;
import java.util.List;
import javax.persistence.Query;


public class ContaPagarDao extends GenericDao<ContaPagar, Long>{
    public ContaPagarDao() {
        super(ContaPagar.class);
    }
    public List<TipoPagamento> findByTipoPagamentoId(Long id) {
        Query q = em.createQuery("Select c from ContaPagar c Where c.tipoPagamento = :tipoPagamentoId");
        q.setParameter("tipoPagamentoId", id);
        return q.getResultList();
    }
}
