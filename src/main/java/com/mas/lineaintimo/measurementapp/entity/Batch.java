/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mas.lineaintimo.measurementapp.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DineshaK
 */
@Entity
@Table(name = "batch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batch.findAll", query = "SELECT b FROM Batch b"),
    @NamedQuery(name = "Batch.findById", query = "SELECT b FROM Batch b WHERE b.id = :id"),
    @NamedQuery(name = "Batch.findByBatchNumber", query = "SELECT b FROM Batch b WHERE b.batchNumber = :batchNumber"),
    @NamedQuery(name = "Batch.findByTotalCount", query = "SELECT b FROM Batch b WHERE b.totalCount = :totalCount")})
public class Batch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "batch_number", nullable = false)
    private int batchNumber;
    @Basic(optional = false)
    @Column(name = "total_count", nullable = false)
    private int totalCount;
    @JoinColumn(name = "style_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Style styleId;
    @JoinColumn(name = "current_stage_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Stage currentStageId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchId")
    private Collection<SleeveEntry> sleeveEntryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchId")
    private Collection<GarmentEntry> garmentEntryCollection;

    public Batch() {
    }

    public Batch(Integer id) {
        this.id = id;
    }

    public Batch(Integer id, int batchNumber, int totalCount) {
        this.id = id;
        this.batchNumber = batchNumber;
        this.totalCount = totalCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Style getStyleId() {
        return styleId;
    }

    public void setStyleId(Style styleId) {
        this.styleId = styleId;
    }

    public Stage getCurrentStageId() {
        return currentStageId;
    }

    public void setCurrentStageId(Stage currentStageId) {
        this.currentStageId = currentStageId;
    }

    @XmlTransient
    public Collection<SleeveEntry> getSleeveEntryCollection() {
        return sleeveEntryCollection;
    }

    public void setSleeveEntryCollection(Collection<SleeveEntry> sleeveEntryCollection) {
        this.sleeveEntryCollection = sleeveEntryCollection;
    }

    @XmlTransient
    public Collection<GarmentEntry> getGarmentEntryCollection() {
        return garmentEntryCollection;
    }

    public void setGarmentEntryCollection(Collection<GarmentEntry> garmentEntryCollection) {
        this.garmentEntryCollection = garmentEntryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Batch)) {
            return false;
        }
        Batch other = (Batch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mas.lineaintimo.measurementapp.entity.Batch[ id=" + id + " ]";
    }
    
}
