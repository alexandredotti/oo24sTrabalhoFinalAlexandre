
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Venda;

public class VendaDao extends GenericDao<Venda, Long> {

    public VendaDao() {
        super(Venda.class);
    }
}
