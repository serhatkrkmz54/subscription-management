package org.abonehatirlatici.neoduyorum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "user_statistics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;

    @Column(name = "monthly_spending",nullable = false)
    private BigDecimal monthlySpending;

    @Column(name = "yearly_spending",nullable = false)
    private BigDecimal yearlySpending;
    private Integer year;


}
