package it.etianus.pratiche.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.InOutEnum;

/**
 * A RequestDoc.
 */
@Entity
@Table(name = "request_doc")
public class RequestDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_date")
    private ZonedDateTime submissionDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * in inviati dall'utente, out inseriti dalla piattaforma
     */
    @NotNull
    @ApiModelProperty(value = "in inviati dall'utente, out inseriti dalla piattaforma", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "in_out", nullable = false)
    private InOutEnum inOut;

    @ManyToOne
    private Request request;

    @OneToOne
    @JoinColumn(unique = true)
    private DocStore doc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSubmissionDate() {
        return submissionDate;
    }

    public RequestDoc submissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public RequestDoc updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public RequestDoc description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InOutEnum getInOut() {
        return inOut;
    }

    public RequestDoc inOut(InOutEnum inOut) {
        this.inOut = inOut;
        return this;
    }

    public void setInOut(InOutEnum inOut) {
        this.inOut = inOut;
    }

    public Request getRequest() {
        return request;
    }

    public RequestDoc request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public DocStore getDoc() {
        return doc;
    }

    public RequestDoc doc(DocStore docStore) {
        this.doc = docStore;
        return this;
    }

    public void setDoc(DocStore docStore) {
        this.doc = docStore;
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
        RequestDoc requestDoc = (RequestDoc) o;
        if (requestDoc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestDoc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestDoc{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", inOut='" + getInOut() + "'" +
            "}";
    }
}
