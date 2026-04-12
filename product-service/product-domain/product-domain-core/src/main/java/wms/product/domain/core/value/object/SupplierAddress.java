package wms.product.domain.core.value.object;

import java.util.Objects;
import java.util.UUID;

public record SupplierAddress(UUID id, String street, String postalCode, String city) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierAddress that = (SupplierAddress) o;
        return street.equals(that.street) && postalCode.equals(that.postalCode) && city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postalCode, city);
    }
}
