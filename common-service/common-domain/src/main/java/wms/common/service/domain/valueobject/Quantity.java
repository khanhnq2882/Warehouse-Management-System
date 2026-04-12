package wms.common.service.domain.valueobject;

public record Quantity(Integer quantity) {

    public static final Quantity ZERO = new Quantity(0);

    public boolean isGreaterThanZero() {
        return this.quantity != null && this.quantity > 0;
    }

    public boolean isGreaterThan(Quantity other) {
        return this.quantity != null && this.quantity > other.quantity();
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.quantity + other.quantity());
    }

    public Quantity subtract(Quantity other) {
        return new Quantity(this.quantity - other.quantity());
    }

    public Quantity multiply(int multiplier) {
        return new Quantity(this.quantity * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity.equals(quantity1.quantity);
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}