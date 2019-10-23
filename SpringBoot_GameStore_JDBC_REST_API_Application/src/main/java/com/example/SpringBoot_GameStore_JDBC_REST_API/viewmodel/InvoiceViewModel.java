package com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Invoice;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.PurchaseItem;

import java.util.Objects;

public class InvoiceViewModel {

        private Invoice invoice;
        private PurchaseItem purchaseItem;

        public Invoice getInvoice() {
                return invoice;
        }

        public void setInvoice(Invoice invoice) {
                this.invoice = invoice;
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
                InvoiceViewModel that = (InvoiceViewModel) o;
                return getInvoice().equals(that.getInvoice()) &&
                        getPurchaseItem().equals(that.getPurchaseItem());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getInvoice(), getPurchaseItem());
        }
}
