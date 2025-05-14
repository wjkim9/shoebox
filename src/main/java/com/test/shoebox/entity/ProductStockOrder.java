package com.test.shoebox.entity;

import com.test.shoebox.dto.ProductStockOrderDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductStockOrder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productstockorder_seq_generator")
    @SequenceGenerator(name = "productstockorder_seq_generator", sequenceName = "productstockorder_seq", allocationSize = 1)
    @Column(name = "productstockorder_id")
    private Long productStockOrderId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "productstock_id", nullable = false)
    private ProductStock productStock;

    public ProductStockOrderDTO toDTO() {
        return ProductStockOrderDTO.builder()
                .productStockOrderId(this.productStockOrderId)
                .quantity(this.quantity)
                .orderPrice(this.orderPrice)
                .ordersId(this.orders.getOrdersId())
                .productStockId(this.productStock.getProductStockId())
                .build();
    }
}
