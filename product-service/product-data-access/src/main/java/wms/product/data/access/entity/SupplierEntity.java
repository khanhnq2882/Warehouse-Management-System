package wms.product.data.access.entity;

import lombok.*;
import wms.common.service.domain.valueobject.SupplierStatus;
import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "suppliers")
@Entity
public class SupplierEntity {
    @Id
    private UUID supplierId;

    private String supplierCode;

    private String supplierName;

    private String contactName;

    private String contactPhone;

    private String email;

    private String supplierDescription;

    @Enumerated(EnumType.STRING)
    private SupplierStatus supplierStatus;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private SupplierAddressEntity supplierAddress;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private ProductEntity product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierEntity that = (SupplierEntity) o;
        return Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }
}
