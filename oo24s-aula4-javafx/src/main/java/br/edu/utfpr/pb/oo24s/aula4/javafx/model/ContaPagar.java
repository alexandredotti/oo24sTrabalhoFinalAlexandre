
package br.edu.utfpr.pb.oo24s.aula4.javafx.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contaPagar")
public class ContaPagar implements AbstractModel{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "O campo 'tipo pagamento' deve ser selecionado.")
    @ManyToOne()
    @JoinColumn(name="tipoPagamento_id", referencedColumnName = "id")
    private TipoPagamento tipoPagamento;
    
    @DecimalMin(value = "0.01", 
            message = "O valor deve ser maior que R$ 0.00.")
    @Column(name = "valor")
    private Double valor;
    
     @NotNull(message = "O campo 'Data de Vencimento' é obrigatório!")
    @Column(name = "dataVenc", nullable = false)
    private LocalDate dataVenc;
     
     @NotEmpty(message = "O campo 'observação' deve ser preenchido.")
    @Column(name = "obs", length = 500, nullable = false)
    private String obs;

    public ContaPagar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(LocalDate dataVenc) {
        this.dataVenc = dataVenc;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final ContaPagar other = (ContaPagar) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContaPagar{" + "id=" + id + ", tipoPagamento=" + tipoPagamento + ", valor=" + valor + ", dataVenc=" + dataVenc + '}';
    }
     
      
}
