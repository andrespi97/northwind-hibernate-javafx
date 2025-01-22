package aadd2.javafxtest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false)
    private Integer id;

    @Column(name = "ProductName", nullable = false, length = 40)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID")
    private Supplier supplierID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private Category categoryID;

    @Column(name = "QuantityPerUnit", length = 20)
    private String quantityPerUnit;

    @ColumnDefault("0")
    @Column(name = "UnitPrice")
    private Double unitPrice;

    @ColumnDefault("0")
    @Column(name = "UnitsInStock")
    private Short unitsInStock;

    @ColumnDefault("0")
    @Column(name = "UnitsOnOrder")
    private Short unitsOnOrder;

    @ColumnDefault("0")
    @Column(name = "ReorderLevel")
    private Short reorderLevel;

    @ColumnDefault("0")
    @Column(name = "Discontinued", nullable = false)
    private Boolean discontinued = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Supplier getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Supplier supplierID) {
        this.supplierID = supplierID;
    }

    public Category getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Short getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Short unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Short getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Short unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Short getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Short reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

}