
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.VendaProduto;


public class VendaProdutoDao extends GenericDao<VendaProduto, Long> {

    public VendaProdutoDao() {
        super(VendaProduto.class);
    }
}
