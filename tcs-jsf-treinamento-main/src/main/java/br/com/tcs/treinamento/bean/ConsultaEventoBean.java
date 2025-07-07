package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Evento;
import br.com.tcs.treinamento.service.EventoService;
import br.com.tcs.treinamento.service.impl.EventoServiceImpl;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "consultaEventoBean")
@ViewScoped
public class ConsultaEventoBean implements Serializable {

    private List<Evento> eventos;
    private Evento eventoSelecionado;
    private String errorMessage;
    private Long eventoId;
    private transient PDFOptions pdfOpt;
    private transient ExcelOptions excelOpt;
    private Boolean tpAtualizacao;

    private transient EventoService eventoService = new EventoServiceImpl();

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Evento getEventoSelecionado() {
        return eventoSelecionado;
    }

    public void setEventoSelecionado(Evento eventoSelecionado) {
        this.eventoSelecionado = eventoSelecionado;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Boolean getTpAtualizacao() {
        return tpAtualizacao;
    }

    public void setTpAtualizacao(Boolean tpAtualizacao) {
        this.tpAtualizacao = tpAtualizacao;
    }

    public EventoService getEventoService() {
        return eventoService;
    }

    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    public PDFOptions getExportOptions() {
        return pdfOpt;
    }

    public ExcelOptions getExcelOptions() {
        return excelOpt;
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idParam = params.get("eventoId");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                eventoId = Long.valueOf(idParam);
                eventoSelecionado = eventoService.buscarEventoPorId(eventoId);
            } catch (NumberFormatException e) {
                errorMessage = "ID de Evento inválido!";
            }
        }

        String tpParam = params.get("tpAtualizacao");
        if (tpParam != null && !tpParam.trim().isEmpty()) {
            setTpAtualizacao(Boolean.valueOf(tpParam));
        } else {
            setTpAtualizacao(true);
        }
        eventos = eventoService.listarEventos();

        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#f2f2f2");
        pdfOpt.setFacetFontSize("12");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("10");
        pdfOpt.setCellFontColor("#000000");

        excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#d0e3fa");
        excelOpt.setFacetFontSize("12");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontSize("10");
        excelOpt.setCellFontColor("#000000");

        if (eventoSelecionado == null) {
            eventoSelecionado = new Evento();
        }
    }

    public String prepararVisualizacao(Evento evento) {
        this.eventoSelecionado = evento;
        return "visualizarEvento?faces-redirect=true&eventoId=" + evento.getId() + "&tpAtualizacao=true";
    }

    public void prepararEdicao(Evento evento) {
        this.eventoSelecionado = evento;
        System.out.println("Evento recebido: " + evento.getNomeEvento());
    }

    public String prepararExclusao(Evento evento) {
        this.eventoSelecionado = evento;
        return "excluir?faces-redirect=true&eventoId=" + evento.getId() + "&tpAtualizacao=false";
    }

    public String atualizarConsulta() {
        eventoService.atualizarEvento(eventoSelecionado);
        eventos = eventoService.listarEventos();
        return "conslutaEventos?faces-redirect=true";
    }

    public void limparAlteracoes() {
        if (eventoSelecionado != null) {
            eventoSelecionado = eventoService.buscarEventoPorId(eventoId);
        }
    }

    public String navegarParaCadastro() {
        return "cadastroEvento?faces-redirect=true";
    }

    private Evento mapEventoEntity() {
        Evento evento = new Evento();
        evento.setId(eventoSelecionado.getId());
        evento.setNomeEvento(eventoSelecionado.getNomeEvento());
        evento.setData(eventoSelecionado.getData());
        evento.setTipoDocumento(eventoSelecionado.getTipoDocumento());
        evento.setNumeroCPF(eventoSelecionado.getNumeroCPF());
        evento.setNumeroCNPJ(eventoSelecionado.getNumeroCNPJ());
        evento.setDescricaoEvento(eventoSelecionado.getDescricaoEvento());
        return evento;
    }

    public void confirmar() {
        Evento evento = mapEventoEntity();

        try {
            eventoService.atualizarEvento(evento);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao cadastrar Evento:" + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show()");
        }
    }

    public void confirmarExclusao() {
        Evento evento = mapEventoEntity();

        try {
            eventoService.excluirEvento(evento);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao excluir Evento:" + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show()");
        }
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (eventoSelecionado.getNomeEvento() == null || eventoSelecionado.getNomeEvento().trim().isEmpty()) {
            erros.add("Nome do Evento não informado.");
        }
        if (eventoSelecionado.getData() == null) {
            erros.add("Data do Evento não informado.");
        }
        if (eventoSelecionado.getDescricaoEvento() == null || eventoSelecionado.getDescricaoEvento().trim().isEmpty()) {
            erros.add("Descrição do Evento não informado.");
        }
        if (eventoSelecionado.getTipoDocumento() == null || eventoSelecionado.getTipoDocumento().trim().isEmpty()) {
            erros.add("Tipo de Documento não informado.");
        } else {
            if ("CPF".equals(eventoSelecionado.getTipoDocumento())) {
                if (eventoSelecionado.getNumeroCPF() == null || eventoSelecionado.getNumeroCPF().trim().isEmpty() || eventoSelecionado.getNumeroCPF().trim().length() < 11) {
                    erros.add("CPF não informado ou incompleto.");
                }
            } else if ("CNPJ".equals(eventoSelecionado.getTipoDocumento())) {
                if (eventoSelecionado.getNumeroCNPJ() == null || eventoSelecionado.getNumeroCNPJ().trim().isEmpty() || eventoSelecionado.getNumeroCNPJ().trim().length() < 14) {
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


}
