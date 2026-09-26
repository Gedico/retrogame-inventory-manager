package com.retrogamer.inventory_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "item_economics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEconomics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, unique = true)
    private InventoryItem item;

    @Size(max = 50)
    @Column(length = 50)
    private String provenance;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal purchasePrice = BigDecimal.ZERO;

    @Column(name = "estimated_sell_price", precision = 10, scale = 2)
    private BigDecimal estimatedSellPrice;

    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "platform_fees", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal platformFees = BigDecimal.ZERO;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "net_profit", precision = 10, scale = 2)
    private BigDecimal netProfit;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    @Column(name = "sold_at")
    private OffsetDateTime soldAt;
}