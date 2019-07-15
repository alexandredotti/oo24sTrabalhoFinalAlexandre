
package br.edu.utfpr.pb.oo24s.aula4.javafx.dao;


import br.edu.utfpr.pb.oo24s.aula4.javafx.model.Cliente;

public class ClienteDao extends GenericDao<Cliente, Long>{

    public ClienteDao() {
        super(Cliente.class);
    }

}
