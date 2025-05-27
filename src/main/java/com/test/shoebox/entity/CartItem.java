package com.test.shoebox.entity;

import com.test.shoebox.dto.CartItemDTO;

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
import jakarta.persistence.FetchType;

@Entity
@Table(name = "CartItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartitem_seq_generator")
    @SequenceGenerator(name = "cartitem_seq_generator", sequenceName = "cartitem_seq", allocationSize = 1)
    @Column(name = "cartitem_id")
    private Long cartItemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productstock_id", nullable = false)
    private ProductStock productStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public CartItemDTO toDTO() {
        return CartItemDTO.builder()
                .cartItemId(this.cartItemId)
                .quantity(this.quantity)
                .productStockId(this.productStock.getProductStockId())
                .membersId(this.members.getMembersId())
                .build();
    }



}
