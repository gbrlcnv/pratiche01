package it.etianus.pratiche.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.ReqTypeEnum;

/**
 * A RequestType.
 */
@Entity
@Table(name = "request_type")
public class RequestType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "req_type")
    private ReqTypeEnum reqType;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReqTypeEnum getReqType() {
        return reqType;
    }

    public RequestType reqType(ReqTypeEnum reqType) {
        this.reqType = reqType;
        return this;
    }

    public void setReqType(ReqTypeEnum reqType) {
        this.reqType = reqType;
    }

    public String getCode() {
        return code;
    }

    public RequestType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public RequestType requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public RequestType addRequest(Request request) {
        this.requests.add(request);
        request.setType(this);
        return this;
    }

    public RequestType removeRequest(Request request) {
        this.requests.remove(request);
        request.setType(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
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
        RequestType requestType = (RequestType) o;
        if (requestType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestType{" +
            "id=" + getId() +
            ", reqType='" + getReqType() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
