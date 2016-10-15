/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mas.lineaintimo.measurementapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DineshaK
 */
@Entity
@Table(name = "garment_entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GarmentEntry.findAll", query = "SELECT g FROM GarmentEntry g"),
    @NamedQuery(name = "GarmentEntry.findById", query = "SELECT g FROM GarmentEntry g WHERE g.id = :id"),
    @NamedQuery(name = "GarmentEntry.findByChestWidth", query = "SELECT g FROM GarmentEntry g WHERE g.chestWidth = :chestWidth"),
    @NamedQuery(name = "GarmentEntry.findByHemWidth", query = "SELECT g FROM GarmentEntry g WHERE g.hemWidth = :hemWidth"),
    @NamedQuery(name = "GarmentEntry.findByCbLength", query = "SELECT g FROM GarmentEntry g WHERE g.cbLength = :cbLength"),
    @NamedQuery(name = "GarmentEntry.findByCfLength", query = "SELECT g FROM GarmentEntry g WHERE g.cfLength = :cfLength")})
public class GarmentEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "chest_width", nullable = false, precision = 5, scale = 4)
    private BigDecimal chestWidth;
    @Basic(optional = false)
    @Column(name = "hem_width", nullable = false, precision = 5, scale = 4)
    private BigDecimal hemWidth;
    @Basic(optional = false)
    @Column(name = "cb_length", nullable = false, precision = 5, scale = 4)
    private BigDecimal cbLength;
    @Basic(optional = false)
    @Column(name = "cf_length", nullable = false, precision = 5, scale = 4)
    private BigDecimal cfLength;
    @JoinColumn(name = "batch_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Batch batchId;
    @JoinColumn(name = "stage_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Stage stageId;
    @JoinColumn(name = "size_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private GarmentSize sizeId;

    public GarmentEntry() {
    }

    public GarmentEntry(Integer id) {
        this.id = id;
    }

    public GarmentEntry(Integer id, BigDecimal chestWidth, BigDecimal hemWidth, BigDecimal cbLength, BigDecimal cfLength) {
        this.id = id;
        this.chestWidth = chestWidth;
        this.hemWidth = hemWidth;
        this.cbLength = cbLength;
        this.cfLength = cfLength;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getChestWidth() {
        return chestWidth;
    }

    public void setChestWidth(BigDecimal chestWidth) {
        this.chestWidth = chestWidth;
    }

    public BigDecimal getHemWidth() {
        return hemWidth;
    }

    public void setHemWidth(BigDecimal hemWidth) {
        this.hemWidth = hemWidth;
    }

    public BigDecimal getCbLength() {
        return cbLength;
    }

    public void setCbLength(BigDecimal cbLength) {
        this.cbLength = cbLength;
    }

    public BigDecimal getCfLength() {
        return cfLength;
    }

    public void setCfLength(BigDecimal cfLength) {
        this.cfLength = cfLength;
    }

    public Batch getBatchId() {
        return batchId;
    }

    public void setBatchId(Batch batchId) {
        this.batchId = batchId;
    }

    public Stage getStageId() {
        return stageId;
    }

    public void setStageId(Stage stageId) {
        this.stageId = stageId;
    }

    public GarmentSize getSizeId() {
        return sizeId;
    }

    public void setSizeId(GarmentSize sizeId) {
        this.sizeId = sizeId;
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
        if (!(object instanceof GarmentEntry)) {
            return false;
        }
        GarmentEntry other = (GarmentEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mas.lineaintimo.measurementapp.entity.GarmentEntry[ id=" + id + " ]";
    }
    
}
