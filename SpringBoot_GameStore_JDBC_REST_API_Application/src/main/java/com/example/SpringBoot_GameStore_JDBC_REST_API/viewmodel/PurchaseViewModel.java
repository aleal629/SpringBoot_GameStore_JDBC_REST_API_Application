package com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Purchase;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.PurchaseItem;

import java.util.Objects;

public class PurchaseViewModel {

    private Purchase purchase;
    private double unitPrice;
    private double subtotal;
    private double tax;
    private double processingFee;
    private double total;
    private PurchaseItem purchaseItem;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PurchaseItem getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseViewModel that = (PurchaseViewModel) o;
        return Double.compare(that.getUnitPrice(), getUnitPrice()) == 0 &&
                Double.compare(that.getSubtotal(), getSubtotal()) == 0 &&
                Double.compare(that.getTax(), getTax()) == 0 &&
                Double.compare(that.getProcessingFee(), getProcessingFee()) == 0 &&
                Double.compare(that.getTotal(), getTotal()) == 0 &&
                getPurchase().equals(that.getPurchase()) &&
                getPurchaseItem().equals(that.getPurchaseItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPurchase(), getUnitPrice(), getSubtotal(), getTax(), getProcessingFee(), getTotal(), getPurchaseItem());
    }
}


