package it.etianus.pratiche.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import it.etianus.pratiche.domain.enumeration.LocaleEnum;

/**
 * A DocStore.
 */
@Entity
@Table(name = "doc_store")
public class DocStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "locale", nullable = false)
    private LocaleEnum locale;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "content_binary")
    private byte[] contentBinary;

    @Column(name = "content_binary_content_type")
    private String contentBinaryContentType;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Lob
    @Column(name = "content_text")
    private byte[] contentText;

    @Column(name = "content_text_content_type")
    private String contentTextContentType;

    @Column(name = "mime_type")
    private String mimeType;

    /**
     * valutare se serve, nel caso converrebbe mettere enum
     */
    @ApiModelProperty(value = "valutare se serve, nel caso converrebbe mettere enum")
    @Column(name = "path")
    private String path;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocaleEnum getLocale() {
        return locale;
    }

    public DocStore locale(LocaleEnum locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(LocaleEnum locale) {
        this.locale = locale;
    }

    public String getCode() {
        return code;
    }

    public DocStore code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public DocStore title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public DocStore description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getContentBinary() {
        return contentBinary;
    }

    public DocStore contentBinary(byte[] contentBinary) {
        this.contentBinary = contentBinary;
        return this;
    }

    public void setContentBinary(byte[] contentBinary) {
        this.contentBinary = contentBinary;
    }

    public String getContentBinaryContentType() {
        return contentBinaryContentType;
    }

    public DocStore contentBinaryContentType(String contentBinaryContentType) {
        this.contentBinaryContentType = contentBinaryContentType;
        return this;
    }

    public void setContentBinaryContentType(String contentBinaryContentType) {
        this.contentBinaryContentType = contentBinaryContentType;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public DocStore creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getContentText() {
        return contentText;
    }

    public DocStore contentText(byte[] contentText) {
        this.contentText = contentText;
        return this;
    }

    public void setContentText(byte[] contentText) {
        this.contentText = contentText;
    }

    public String getContentTextContentType() {
        return contentTextContentType;
    }

    public DocStore contentTextContentType(String contentTextContentType) {
        this.contentTextContentType = contentTextContentType;
        return this;
    }

    public void setContentTextContentType(String contentTextContentType) {
        this.contentTextContentType = contentTextContentType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public DocStore mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public DocStore path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
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
        DocStore docStore = (DocStore) o;
        if (docStore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docStore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocStore{" +
            "id=" + getId() +
            ", locale='" + getLocale() + "'" +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", contentBinary='" + getContentBinary() + "'" +
            ", contentBinaryContentType='" + getContentBinaryContentType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", contentText='" + getContentText() + "'" +
            ", contentTextContentType='" + getContentTextContentType() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
