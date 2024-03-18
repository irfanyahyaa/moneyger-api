package com.irfan.moneyger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_transaction")
public class TTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MUser user;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "income")
    private Long income;

    @Column(name = "expense")
    private Long expense;

    @Column(name = "balance", nullable = false)
    private Long balance;
}
