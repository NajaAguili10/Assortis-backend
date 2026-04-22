package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "confirmed_org_matches")
public class ConfirmedOrgMatch {
    @Id
    @ColumnDefault("nextval('confirmed_org_matches_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tender_id", nullable = false)
    private Tender tender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org1_id", nullable = false)
    private Organization org1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org2_id", nullable = false)
    private Organization org2;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "match_date")
    private Instant matchDate;

    @ColumnDefault("false")
    @Column(name = "contact_exchanged")
    private Boolean contactExchanged;

}
