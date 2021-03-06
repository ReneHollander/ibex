package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.RecurringOrderItemId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recurringOrder")
@ToString(exclude = "recurringOrder")
public class RecurringOrderItem {

    public static Comparator<RecurringOrderItem> COMPARE_BY_PRODUCT = Comparator.comparingInt(r -> r.getProduct().getId());
    @EmbeddedId
    @JsonIgnore
    private RecurringOrderItemId recurringOrderItemId;
    @MapsId("recurringOrderId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private RecurringOrder recurringOrder;
    @MapsId("product")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;
    private int amount;

    public RecurringOrderItem(RecurringOrder recurringOrder, Product product, int amount) {
        this.recurringOrderItemId = new RecurringOrderItemId(recurringOrder.getRecurringOrderId(), product.getId());
        this.recurringOrder = recurringOrder;
        this.product = product;
        this.amount = amount;
    }
}
