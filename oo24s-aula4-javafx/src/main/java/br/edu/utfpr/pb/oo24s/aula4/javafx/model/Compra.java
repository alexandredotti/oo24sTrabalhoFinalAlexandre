
package br.edu.utfpr.pb.oo24s.aula4.javafx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "compra")
public class Compra implements AbstractModel, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "O campo 'fornecedor' deve ser selecionado.")
    @ManyToOne()
    @JoinColumn(name="fornecedor_id", referencedColumnName = "id")
    private Fornecedor fornecedor;
    
    @ManyToOne()
    @JoinColumn(name="contaPagar_id", referencedColumnName = "id")
    private ContaPagar contaPagar;
    
    @Column(name = "data", nullable = false)
    private LocalDate data;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "compra",
            fetch = FetchType.EAGER)
    private List<CompraProduto> compraProdutos;
    @Transient
    private Double vlrTotal;

    public Compra() {
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<CompraProduto> getCompraProdutos() {
        return compraProdutos;
    }

    public void setCompraProdutos(List<CompraProduto> compraProdutos) {
        this.compraProdutos = compraProdutos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Compra other = (Compra) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    public Double getValorTotal() {
        return compraProdutos.stream().mapToDouble(cp -> 
            cp.getQuantidade() * cp.getValor() ).sum();
    }
}
