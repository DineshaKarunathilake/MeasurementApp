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
@Table(name = "sleeve_entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SleeveEntry.findAll", query = "SELECT s FROM SleeveEntry s"),
    @NamedQuery(name = "SleeveEntry.findById", query = "SELECT s FROM SleeveEntry s WHERE s.id = :id"),
    @NamedQuery(name = "SleeveEntry.findByOpening", query = "SELECT s FROM SleeveEntry s WHERE s.opening = :opening"),
    @NamedQuery(name = "SleeveEntry.findByLength", query = "SELECT s FROM SleeveEntry s WHERE s.length = :length"),
    @NamedQuery(name = "SleeveEntry.findByWidth", query = "SELECT s FROM SleeveEntry s WHERE s.width = :width")})
public class SleeveEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "opening", nullable = false, precision = 5, scale = 4)
    private BigDecimal opening;
    @Basic(optional = false)
    @Column(name = "length", nullable = false, precision = 5, scale = 4)
    private BigDecimal length;
    @Basic(optional = false)
    @Column(name = "width", nullable = false, precision = 5, scale = 4)
    private BigDecimal width;
    @JoinColumn(name = "stage_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Stage stageId;
    @JoinColumn(name = "size_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private GarmentSize sizeId;
    @JoinColumn(name = "batch_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Batch batchId;

    public SleeveEntry() {
    }

    public SleeveEntry(Integer id) {
        this.id = id;
    }

    public SleeveEntry(Integer id, BigDecimal opening, BigDecimal length, BigDecimal width) {
        this.id = id;
        this.opening = opening;
        this.length = length;
        this.width = width;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOpening() {
        return opening;
    }

    public void setOpening(BigDecimal opening) {
        this.opening = opening;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
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

    public Batch getBatchId() {
        return batchId;
    }

    public void setBatchId(Batch batchId) {
        this.batchId = batchId;
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
        if (!(object instanceof SleeveEntry)) {
            return false;
        }
        SleeveEntry other = (SleeveEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mas.lineaintimo.measurementapp.entity.SleeveEntry[ id=" + id + " ]";
    }
    
}
