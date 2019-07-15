
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.TipoPagamento;
import javax.persistence.Entity;
import javax.persistence.Table;

public class TipoPagamentoDao extends GenericDao<TipoPagamento, Long>{
    
    public TipoPagamentoDao() {
        super(TipoPagamento.class);
    }
}
