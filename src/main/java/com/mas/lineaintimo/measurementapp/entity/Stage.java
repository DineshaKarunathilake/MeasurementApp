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
@Table(name = "stage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stage.findAll", query = "SELECT s FROM Stage s"),
    @NamedQuery(name = "Stage.findById", query = "SELECT s FROM Stage s WHERE s.id = :id"),
    @NamedQuery(name = "Stage.findByName", query = "SELECT s FROM Stage s WHERE s.name = :name")})
public class Stage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currentStageId")
    private Collection<Batch> batchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stageId")
    private Collection<SleeveEntry> sleeveEntryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stageId")
    private Collection<GarmentEntry> garmentEntryCollection;

    public Stage() {
    }

    public Stage(Integer id) {
        this.id = id;
    }

    public Stage(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Batch> getBatchCollection() {
        return batchCollection;
    }

    public void setBatchCollection(Collection<Batch> batchCollection) {
        this.batchCollection = batchCollection;
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
        if (!(object instanceof Stage)) {
            return false;
        }
        Stage other = (Stage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mas.lineaintimo.measurementapp.entity.Stage[ id=" + id + " ]";
    }
    
}
