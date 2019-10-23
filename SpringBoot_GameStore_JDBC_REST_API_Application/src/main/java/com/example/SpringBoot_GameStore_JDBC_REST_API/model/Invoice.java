package com.example.SpringBoot_GameStore_JDBC_REST_API.model;

import javax.validation.constraints.Digits;
import java.util.Objects;

public class Invoice extends Purchase {

    @Digits(integer = 11, fraction = 0)
    private int invoiceId;
    @Digits(integer = 3, fraction = 2)
    private double unitPrice;
    @Digits(integer = 3, fraction = 2)
    private double subtotal;
    @Digits(integer = 3, fraction = 2)
    private double tax;
    @Digits(integer = 3, fraction = 2)
    private double processingFee;
    @Digits(integer = 3, fraction = 2)
    private double total;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Invoice invoice = (Invoice) o;
        return getInvoiceId() == invoice.getInvoiceId() &&
                Double.compare(invoice.getUnitPrice(), getUnitPrice()) == 0 &&
                Double.compare(invoice.getSubtotal(), getSubtotal()) == 0 &&
                Double.compare(invoice.getTax(), getTax()) == 0 &&
                Double.compare(invoice.getProcessingFee(), getProcessingFee()) == 0 &&
                Double.compare(invoice.getTotal(), getTotal()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInvoiceId(), getUnitPrice(), getSubtotal(), getTax(), getProcessingFee(), getTotal());
    }
}