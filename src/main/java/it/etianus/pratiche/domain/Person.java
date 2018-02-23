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

import it.etianus.pratiche.domain.enumeration.YesNotEnum;

import it.etianus.pratiche.domain.enumeration.IDTypeEnum;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * nome o ragione sociale
     */
    @NotNull
    @ApiModelProperty(value = "nome o ragione sociale", required = true)
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private ZonedDateTime birthDate;

    @Column(name = "piva")
    private String piva;

    @Column(name = "cf")
    private String cf;

    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Si per le persone giuridiche
     */
    @NotNull
    @ApiModelProperty(value = "Si per le persone giuridiche", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "flag_legal", nullable = false)
    private YesNotEnum flagLegal;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_type")
    private IDTypeEnum idType;

    /**
     * tipo di documento utilizzato per l'identificazione
     */
    @ApiModelProperty(value = "tipo di documento utilizzato per l'identificazione")
    @Column(name = "id_number")
    private String idNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flag_newsletter", nullable = false)
    private YesNotEnum flagNewsletter;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public Person birthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPiva() {
        return piva;
    }

    public Person piva(String piva) {
        this.piva = piva;
        return this;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCf() {
        return cf;
    }

    public Person cf(String cf) {
        this.cf = cf;
        return this;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public YesNotEnum getFlagLegal() {
        return flagLegal;
    }

    public Person flagLegal(YesNotEnum flagLegal) {
        this.flagLegal = flagLegal;
        return this;
    }

    public void setFlagLegal(YesNotEnum flagLegal) {
        this.flagLegal = flagLegal;
    }

    public IDTypeEnum getIdType() {
        return idType;
    }

    public Person idType(IDTypeEnum idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(IDTypeEnum idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public Person idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public YesNotEnum getFlagNewsletter() {
        return flagNewsletter;
    }

    public Person flagNewsletter(YesNotEnum flagNewsletter) {
        this.flagNewsletter = flagNewsletter;
        return this;
    }

    public void setFlagNewsletter(YesNotEnum flagNewsletter) {
        this.flagNewsletter = flagNewsletter;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public Person requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public Person addRequest(Request request) {
        this.requests.add(request);
        request.setOwner(this);
        return this;
    }

    public Person removeRequest(Request request) {
        this.requests.remove(request);
        request.setOwner(null);
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
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", piva='" + getPiva() + "'" +
            ", cf='" + getCf() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", flagLegal='" + getFlagLegal() + "'" +
            ", idType='" + getIdType() + "'" +
            ", idNumber='" + getIdNumber() + "'" +
            ", flagNewsletter='" + getFlagNewsletter() + "'" +
            "}";
    }
}
