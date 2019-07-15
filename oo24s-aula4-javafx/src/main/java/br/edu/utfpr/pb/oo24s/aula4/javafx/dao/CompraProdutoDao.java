package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.CompraProduto;

public class CompraProdutoDao extends GenericDao<CompraProduto, Long> {

    public CompraProdutoDao() {
        super(CompraProduto.class);
    }
}