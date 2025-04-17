package com.company.homeworkcarstore.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "CAR", indexes = {
        @Index(name = "IDX_CAR_MODEL", columnList = "MODEL_ID"),
        @Index(name = "IDX_CAR_UNIQUE_REGISTRATION_NUMBER", columnList = "REGISTRATION_NUMBER", unique = true)
})
@Entity
public class Car {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Length(min = 6, max = 6)
    @Column(name = "REGISTRATION_NUMBER", length = 6)
    @InstanceName
    private String registrationNumber;

    @JoinColumn(name = "MODEL_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Model model;

    @Max(2030)
    @Min(1990)
    @Column(name = "PRODUCTION_YEAR", nullable = false)
    @NotNull
    private Integer productionYear;

    @Column(name = "STATUS", nullable = false)
    @NotNull
    private String status;

    @Column(name = "DATE_OF_SALE")
    private LocalDate dateOfSale;

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public CarStatus getStatus() {
        return status == null ? null : CarStatus.fromId(status);
    }

    public void setStatus(CarStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}