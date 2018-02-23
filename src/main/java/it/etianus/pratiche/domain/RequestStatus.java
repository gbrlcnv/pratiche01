package it.etianus.pratiche.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.RequestStatusEnum;

/**
 * A RequestStatus.
 */
@Entity
@Table(name = "request_status")
public class RequestStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private RequestStatusEnum code;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "oldStatus")
    @JsonIgnore
    private Set<RequestStatusLog> requestStatusLogs = new HashSet<>();

    @OneToMany(mappedBy = "newStatus")
    @JsonIgnore
    private Set<RequestStatusLog> requestStatusLogs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatusEnum getCode() {
        return code;
    }

    public RequestStatus code(RequestStatusEnum code) {
        this.code = code;
        return this;
    }

    public void setCode(RequestStatusEnum code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public RequestStatus requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public RequestStatus addRequest(Request request) {
        this.requests.add(request);
        request.setStatus(this);
        return this;
    }

    public RequestStatus removeRequest(Request request) {
        this.requests.remove(request);
        request.setStatus(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<RequestStatusLog> getRequestStatusLogs() {
        return requestStatusLogs;
    }

    public RequestStatus requestStatusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.requestStatusLogs = requestStatusLogs;
        return this;
    }

    public RequestStatus addRequestStatusLog(RequestStatusLog requestStatusLog) {
        this.requestStatusLogs.add(requestStatusLog);
        requestStatusLog.setOldStatus(this);
        return this;
    }

    public RequestStatus removeRequestStatusLog(RequestStatusLog requestStatusLog) {
        this.requestStatusLogs.remove(requestStatusLog);
        requestStatusLog.setOldStatus(null);
        return this;
    }

    public void setRequestStatusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.requestStatusLogs = requestStatusLogs;
    }

    public Set<RequestStatusLog> getRequestStatusLogs() {
        return requestStatusLogs;
    }

    public RequestStatus requestStatusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.requestStatusLogs = requestStatusLogs;
        return this;
    }

    public RequestStatus addRequestStatusLog(RequestStatusLog requestStatusLog) {
        this.requestStatusLogs.add(requestStatusLog);
        requestStatusLog.setNewStatus(this);
        return this;
    }

    public RequestStatus removeRequestStatusLog(RequestStatusLog requestStatusLog) {
        this.requestStatusLogs.remove(requestStatusLog);
        requestStatusLog.setNewStatus(null);
        return this;
    }

    public void setRequestStatusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.requestStatusLogs = requestStatusLogs;
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
        RequestStatus requestStatus = (RequestStatus) o;
        if (requestStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
