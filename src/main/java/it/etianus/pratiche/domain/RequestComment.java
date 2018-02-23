package it.etianus.pratiche.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.InOutEnum;

/**
 * A RequestComment.
 */
@Entity
@Table(name = "request_comment")
public class RequestComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "title")
    private String title;

    @Column(name = "comment_date")
    private ZonedDateTime commentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "in_out")
    private InOutEnum inOut;

    @ManyToOne
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public RequestComment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public RequestComment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCommentDate() {
        return commentDate;
    }

    public RequestComment commentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    public void setCommentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public InOutEnum getInOut() {
        return inOut;
    }

    public RequestComment inOut(InOutEnum inOut) {
        this.inOut = inOut;
        return this;
    }

    public void setInOut(InOutEnum inOut) {
        this.inOut = inOut;
    }

    public Request getRequest() {
        return request;
    }

    public RequestComment request(Request request) {
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
        RequestComment requestComment = (RequestComment) o;
        if (requestComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestComment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", title='" + getTitle() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            ", inOut='" + getInOut() + "'" +
            "}";
    }
}
