package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "contract_lots", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "contract_lots_contract_id_lot_id_key", columnNames = { "contract_id", "lot_id" }),
        @UniqueConstraint(name = "contract_lots_lot_id_unique", columnNames = { "lot_id" })
})
public class ContractLot {
    private Long id;

    private Contract contract;

    private TenderLot lot;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "contract_id", nullable = false)
    public Contract getContract() {
        return contract;
    }

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lot_id", nullable = false)
    public TenderLot getLot() {
        return lot;
    }

}
