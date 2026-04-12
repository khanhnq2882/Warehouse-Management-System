package wms.product.domain.core.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.service.domain.valueobject.SupplierId;
import wms.product.domain.core.value.object.SupplierAddress;
import wms.common.service.domain.valueobject.SupplierStatus;
import java.time.LocalDateTime;

public class Supplier extends AggregateRoot<SupplierId> {
    private String supplierCode;
    private String supplierName;
    private String contactName;
    private String contactPhone;
    private String email;
    private SupplierAddress supplierAddress;
    private String supplierDescription;
    private SupplierStatus supplierStatus;

    private Supplier(Builder builder) {
        super.setId(builder.supplierId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        supplierName = builder.supplierName;
        supplierCode = builder.supplierCode;
        contactName = builder.contactName;
        contactPhone = builder.contactPhone;
        email = builder.email;
        supplierAddress = builder.supplierAddress;
        supplierDescription = builder.supplierDescription;
        supplierStatus = builder.supplierStatus;
    }

    public Supplier(SupplierId supplierId) {
        super.setId(supplierId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public SupplierAddress getSupplierAddress() {
        return supplierAddress;
    }

    public String getSupplierDescription() {
        return supplierDescription;
    }

    public SupplierStatus getSupplierStatus() {
        return supplierStatus;
    }

    public static final class Builder {
        private SupplierId supplierId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String supplierCode;
        private String supplierName;
        private String contactName;
        private String contactPhone;
        private String email;
        private SupplierAddress supplierAddress;
        private String supplierDescription;
        private SupplierStatus supplierStatus;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder supplierCode(String val) {
            supplierCode = val;
            return this;
        }

        public Builder supplierName(String val) {
            supplierName = val;
            return this;
        }

        public Builder contactName(String val) {
            contactName = val;
            return this;
        }

        public Builder contactPhone(String val) {
            contactPhone = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder supplierAddress(SupplierAddress val) {
            supplierAddress = val;
            return this;
        }

        public Builder supplierDescription(String val) {
            supplierDescription = val;
            return this;
        }

        public Builder supplierStatus(SupplierStatus val) {
            supplierStatus = val;
            return this;
        }

        public Supplier build() {
            return new Supplier(this);
        }
    }
}
