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
@Table(name = "style")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Style.findAll", query = "SELECT s FROM Style s"),
    @NamedQuery(name = "Style.findById", query = "SELECT s FROM Style s WHERE s.id = :id"),
    @NamedQuery(name = "Style.findByName", query = "SELECT s FROM Style s WHERE s.name = :name"),
    @NamedQuery(name = "Style.findByCustomer", query = "SELECT s FROM Style s WHERE s.customer = :customer")})
public class Style implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @Column(name = "customer", nullable = false, length = 255)
    private String customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "styleId")
    private Collection<Batch> batchCollection;

    public Style() {
    }

    public Style(Integer id) {
        this.id = id;
    }

    public Style(Integer id, String name, String customer) {
        this.id = id;
        this.name = name;
        this.customer = customer;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @XmlTransient
    public Collection<Batch> getBatchCollection() {
        return batchCollection;
    }

    public void setBatchCollection(Collection<Batch> batchCollection) {
        this.batchCollection = batchCollection;
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
        if (!(object instanceof Style)) {
            return false;
        }
        Style other = (Style) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mas.lineaintimo.measurementapp.entity.Style[ id=" + id + " ]";
    }
    
}
