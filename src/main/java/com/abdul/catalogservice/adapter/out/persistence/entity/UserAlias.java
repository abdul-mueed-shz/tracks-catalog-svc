package com.abdul.catalogservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_aliases",
        indexes = @Index(name = "idx_user_aliases_normalized_name", columnList = "normalized_name"),
        uniqueConstraints = @UniqueConstraint(name = "uq_user_alias", columnNames = {"user_id", "normalized_name"})
)
public class UserAlias extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "alias_name", nullable = false)
    private String aliasName;

    @Column(name = "normalized_name", nullable = false)
    private String normalizedName;

    public void assignUser(User user) {
        this.user = user;
    }
}
