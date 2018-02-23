package it.etianus.pratiche.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RequestStatusLog.
 */
@Entity
@Table(name = "request_status_log")
public class RequestStatusLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "status_from_date", nullable = false)
    private ZonedDateTime statusFromDate;

    @NotNull
    @Column(name = "status_change_date", nullable = false)
    private ZonedDateTime statusChangeDate;

    @ManyToOne
    private Request campaign;

    @ManyToOne
    private RequestStatus oldStatus;

    @ManyToOne
    private RequestStatus newStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public RequestStatusLog note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getStatusFromDate() {
        return statusFromDate;
    }

    public RequestStatusLog statusFromDate(ZonedDateTime statusFromDate) {
        this.statusFromDate = statusFromDate;
        return this;
    }

    public void setStatusFromDate(ZonedDateTime statusFromDate) {
        this.statusFromDate = statusFromDate;
    }

    public ZonedDateTime getStatusChangeDate() {
        return statusChangeDate;
    }

    public RequestStatusLog statusChangeDate(ZonedDateTime statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
        return this;
    }

    public void setStatusChangeDate(ZonedDateTime statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Request getCampaign() {
        return campaign;
    }

    public RequestStatusLog campaign(Request request) {
        this.campaign = request;
        return this;
    }

    public void setCampaign(Request request) {
        this.campaign = request;
    }

    public RequestStatus getOldStatus() {
        return oldStatus;
    }

    public RequestStatusLog oldStatus(RequestStatus requestStatus) {
        this.oldStatus = requestStatus;
        return this;
    }

    public void setOldStatus(RequestStatus requestStatus) {
        this.oldStatus = requestStatus;
    }

    public RequestStatus getNewStatus() {
        return newStatus;
    }

    public RequestStatusLog newStatus(RequestStatus requestStatus) {
        this.newStatus = requestStatus;
        return this;
    }

    public void setNewStatus(RequestStatus requestStatus) {
        this.newStatus = requestStatus;
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
        RequestStatusLog requestStatusLog = (RequestStatusLog) o;
        if (requestStatusLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestStatusLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestStatusLog{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", statusFromDate='" + getStatusFromDate() + "'" +
            ", statusChangeDate='" + getStatusChangeDate() + "'" +
            "}";
    }
}
