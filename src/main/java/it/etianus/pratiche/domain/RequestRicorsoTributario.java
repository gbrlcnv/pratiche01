package it.etianus.pratiche.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.TipologiaAttoEnum;

/**
 * A RequestRicorsoTributario.
 */
@Entity
@Table(name = "request_ricorso_tributario")
public class RequestRicorsoTributario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "notifica_date", nullable = false)
    private ZonedDateTime notificaDate;

    /**
     * nome o ragione sociale
     */
    @NotNull
    @ApiModelProperty(value = "nome o ragione sociale", required = true)
    @Column(name = "emissione_ruolo_date", nullable = false)
    private ZonedDateTime emissioneRuoloDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia_atto")
    private TipologiaAttoEnum tipologiaAtto;

    @OneToOne
    @JoinColumn(unique = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getNotificaDate() {
        return notificaDate;
    }

    public RequestRicorsoTributario notificaDate(ZonedDateTime notificaDate) {
        this.notificaDate = notificaDate;
        return this;
    }

    public void setNotificaDate(ZonedDateTime notificaDate) {
        this.notificaDate = notificaDate;
    }

    public ZonedDateTime getEmissioneRuoloDate() {
        return emissioneRuoloDate;
    }

    public RequestRicorsoTributario emissioneRuoloDate(ZonedDateTime emissioneRuoloDate) {
        this.emissioneRuoloDate = emissioneRuoloDate;
        return this;
    }

    public void setEmissioneRuoloDate(ZonedDateTime emissioneRuoloDate) {
        this.emissioneRuoloDate = emissioneRuoloDate;
    }

    public TipologiaAttoEnum getTipologiaAtto() {
        return tipologiaAtto;
    }

    public RequestRicorsoTributario tipologiaAtto(TipologiaAttoEnum tipologiaAtto) {
        this.tipologiaAtto = tipologiaAtto;
        return this;
    }

    public void setTipologiaAtto(TipologiaAttoEnum tipologiaAtto) {
        this.tipologiaAtto = tipologiaAtto;
    }

    public Request getRequest() {
        return request;
    }

    public RequestRicorsoTributario request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestRicorsoTributario requestRicorsoTributario = (RequestRicorsoTributario) o;
        if (requestRicorsoTributario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestRicorsoTributario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestRicorsoTributario{" +
            "id=" + getId() +
            ", notificaDate='" + getNotificaDate() + "'" +
            ", emissioneRuoloDate='" + getEmissioneRuoloDate() + "'" +
            ", tipologiaAtto='" + getTipologiaAtto() + "'" +
            "}";
    }
}
