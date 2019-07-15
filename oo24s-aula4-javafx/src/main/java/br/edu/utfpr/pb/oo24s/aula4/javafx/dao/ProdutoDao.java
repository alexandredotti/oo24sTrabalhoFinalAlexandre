package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Produto;
import java.util.List;
import javax.persistence.Query;

public class ProdutoDao extends GenericDao<Produto, Long> {

    public ProdutoDao() {
        super(Produto.class);
    }
    
    public List<Produto> findByCategoriaId(Long id) {
        Query q = em.createQuery("Select p from Produto p Where p.categoria.id = :id");
        return q.getResultList();
    }
}
