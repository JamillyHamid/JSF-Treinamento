package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Evento;
import br.com.tcs.treinamento.model.EventoVO;
import br.com.tcs.treinamento.service.EventoService;
import br.com.tcs.treinamento.service.impl.EventoServiceImpl;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "cadastroEventoBean")
@ViewScoped
public class CadastroEventoBean implements Serializable {
    private static final long serialVersionUID = 3450069247988201468L;

    private EventoVO cadastrarEvento = new EventoVO();

    private String errorMessage;

    private transient EventoService eventoService = new EventoServiceImpl();

    public void confirmar() {
        Evento  evento = new Evento();
        evento.setNomeEvento(cadastrarEvento.getNomeEvento());
        evento.setData(cadastrarEvento.getData());
        evento.setTipoDocumento(cadastrarEvento.getTipoDocumento());
        evento.setNumeroCPF(cadastrarEvento.getNumeroCPF());
        evento.setNumeroCNPJ(cadastrarEvento.getNumeroCNPJ());
        evento.setDescricaoEvento(cadastrarEvento.getDescricaoEvento());

        try {
            eventoService.cadastrarEvento(evento);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao cadastrar evento: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    public void limpar() {
        cadastrarEvento.setNomeEvento(null);
        cadastrarEvento.setData(null);
        cadastrarEvento.setTipoDocumento(null);
        cadastrarEvento.setNumeroCPF(null);
        cadastrarEvento.setNumeroCNPJ(null);
        cadastrarEvento.setDescricaoEvento(null);
        errorMessage = null;
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (cadastrarEvento.getNomeEvento() == null || cadastrarEvento.getNomeEvento().trim().isEmpty()) {
            erros.add("Nome do Evento não informado.");
        }
        if (cadastrarEvento.getData() == null) {
            erros.add("Data do Evento não informado.");
        }
        if (cadastrarEvento.getDescricaoEvento() == null || cadastrarEvento.getDescricaoEvento().trim().isEmpty()) {
            erros.add("Descrição do Evento não informado.");
        }
        if (cadastrarEvento.getTipoDocumento() == null || cadastrarEvento.getTipoDocumento().trim().isEmpty()) {
            erros.add("Tipo de Documento não informado.");
        } else {
            if ("CPF".equals(cadastrarEvento.getTipoDocumento())) {
                if (cadastrarEvento.getNumeroCPF() == null || cadastrarEvento.getNumeroCPF().trim().isEmpty() || cadastrarEvento.getNumeroCPF().trim().length() < 11) {
                        erros.add("CPF não informado ou incompleto.");
                }
            } else if ("CNPJ".equals(cadastrarEvento.getTipoDocumento())) {
                if (cadastrarEvento.getNumeroCNPJ() == null || cadastrarEvento.getNumeroCNPJ().trim().isEmpty() || cadastrarEvento.getNumeroCNPJ().trim().length() < 14) {
                        erros.add("CNPJ não informado ou incompleto.");
                }
            }
        }

        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public EventoVO getCadastrarEvento() {
        return cadastrarEvento;
    }

    public void setCadastrarEvento(EventoVO cadastrarEvento) {
        this.cadastrarEvento = cadastrarEvento;
    }

    public EventoService getEventoService() {
        return eventoService;
    }

    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.eventoService = new EventoServiceImpl();
    }
}
