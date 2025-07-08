package br.com.tcs.treinamento.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EventoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nomeEvento;
    private Date data;
    private String descricaoEvento;
    private String tipoDocumento;
    private String numeroCPF;
    private String numeroCNPJ;

    public EventoVO(String nomeEvento, Date data, String descricaoEvento, String tipoDocumento, String numeroCPF, String numeroCNPJ) {
        this.nomeEvento = nomeEvento;
        this.data = data;
        this.descricaoEvento = descricaoEvento;
        this.tipoDocumento = tipoDocumento;
        this.numeroCPF = numeroCPF;
        this.numeroCNPJ = numeroCNPJ;
    }

    public EventoVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroCPF() {
        return numeroCPF;
    }

    public void setNumeroCPF(String numeroCPF) {
        this.numeroCPF = numeroCPF;
    }

    public String getNumeroCNPJ() {
        return numeroCNPJ;
    }

    public void setNumeroCNPJ(String numeroCNPJ) {
        this.numeroCNPJ = numeroCNPJ;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventoVO eventoVO = (EventoVO) o;
        return Objects.equals(id, eventoVO.id)
                && Objects.equals(nomeEvento, eventoVO.nomeEvento)
                && Objects.equals(data, eventoVO.data)
                && Objects.equals(descricaoEvento, eventoVO.descricaoEvento)
                && Objects.equals(tipoDocumento, eventoVO.tipoDocumento)
                && Objects.equals(numeroCPF, eventoVO.numeroCPF)
                && Objects.equals(numeroCNPJ, eventoVO.numeroCNPJ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeEvento, data, descricaoEvento, tipoDocumento, numeroCPF, numeroCNPJ);
    }
}
