package wms.product.data.access.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier_address")
@Entity
public class SupplierAddressEntity {
    @Id
    private UUID supplierId;

    private String street;

    private String postalCode;

    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierAddressEntity that = (SupplierAddressEntity) o;
        return supplierId.equals(that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }
}
