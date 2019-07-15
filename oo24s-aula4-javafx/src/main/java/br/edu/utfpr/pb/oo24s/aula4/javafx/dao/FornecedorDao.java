/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;

import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Fornecedor;


public class FornecedorDao extends GenericDao<Fornecedor, Long>{

    public FornecedorDao() {
        super(Fornecedor.class);
    }

}