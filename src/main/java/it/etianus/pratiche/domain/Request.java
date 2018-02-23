package it.etianus.pratiche.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * nome o ragione sociale
     */
    @NotNull
    @ApiModelProperty(value = "nome o ragione sociale", required = true)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "submission_date")
    private ZonedDateTime submissionDate;

    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private Set<RequestComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private Set<RequestDoc> docs = new HashSet<>();

    @OneToMany(mappedBy = "campaign")
    @JsonIgnore
    private Set<RequestStatusLog> statusLogs = new HashSet<>();

    @ManyToOne
    private Person owner;

    @ManyToOne
    private RequestType type;

    @ManyToOne
    private RequestStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Request subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public Request code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getSubmissionDate() {
        return submissionDate;
    }

    public Request submissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Set<RequestComment> getComments() {
        return comments;
    }

    public Request comments(Set<RequestComment> requestComments) {
        this.comments = requestComments;
        return this;
    }

    public Request addComment(RequestComment requestComment) {
        this.comments.add(requestComment);
        requestComment.setRequest(this);
        return this;
    }

    public Request removeComment(RequestComment requestComment) {
        this.comments.remove(requestComment);
        requestComment.setRequest(null);
        return this;
    }

    public void setComments(Set<RequestComment> requestComments) {
        this.comments = requestComments;
    }

    public Set<RequestDoc> getDocs() {
        return docs;
    }

    public Request docs(Set<RequestDoc> requestDocs) {
        this.docs = requestDocs;
        return this;
    }

    public Request addDoc(RequestDoc requestDoc) {
        this.docs.add(requestDoc);
        requestDoc.setRequest(this);
        return this;
    }

    public Request removeDoc(RequestDoc requestDoc) {
        this.docs.remove(requestDoc);
        requestDoc.setRequest(null);
        return this;
    }

    public void setDocs(Set<RequestDoc> requestDocs) {
        this.docs = requestDocs;
    }

    public Set<RequestStatusLog> getStatusLogs() {
        return statusLogs;
    }

    public Request statusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.statusLogs = requestStatusLogs;
        return this;
    }

    public Request addStatusLog(RequestStatusLog requestStatusLog) {
        this.statusLogs.add(requestStatusLog);
        requestStatusLog.setCampaign(this);
        return this;
    }

    public Request removeStatusLog(RequestStatusLog requestStatusLog) {
        this.statusLogs.remove(requestStatusLog);
        requestStatusLog.setCampaign(null);
        return this;
    }

    public void setStatusLogs(Set<RequestStatusLog> requestStatusLogs) {
        this.statusLogs = requestStatusLogs;
    }

    public Person getOwner() {
        return owner;
    }

    public Request owner(Person person) {
        this.owner = person;
        return this;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public RequestType getType() {
        return type;
    }

    public Request type(RequestType requestType) {
        this.type = requestType;
        return this;
    }

    public void setType(RequestType requestType) {
        this.type = requestType;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Request status(RequestStatus requestStatus) {
        this.status = requestStatus;
        return this;
    }

    public void setStatus(RequestStatus requestStatus) {
        this.status = requestStatus;
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
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", code='" + getCode() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
