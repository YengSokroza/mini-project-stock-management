package com.company;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private Integer proId;
    private String proName;
    private Integer proQty;
    private Double proPrice;
    private LocalDate importedDate;



    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getProQty() {
        return proQty;
    }

    public void setProQty(Integer proQty) {
        this.proQty = proQty;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
        this.proPrice = proPrice;
    }

    public LocalDate getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(LocalDate importedDate) {
        this.importedDate = importedDate;
    }

    public Product(Integer proId, String proName,double proPrice ,Integer proQty , LocalDate importedDate) {
        this.proId = proId;
        this.proName = proName;
        this.proQty = proQty;
        this.proPrice = proPrice;
        this.importedDate = importedDate;
    }

    Product(){}

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", proQty=" + proQty +
                ", proPrice=" + proPrice +
                ", importedDate=" + importedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(proPrice, product.proPrice) == 0 && Objects.equals(proId, product.proId) && Objects.equals(proName, product.proName) && Objects.equals(proQty, product.proQty) && Objects.equals(importedDate, product.importedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proId, proName, proQty, proPrice, importedDate);
    }
}
