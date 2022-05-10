package com.example.demo.modules.education.domain;

import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.common.type.YN;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String subject;

    @Enumerated(EnumType.STRING)
    private YN isDelete = YN.N;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "education_account",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id")
    )
    private Set<Account> accounts = new HashSet<>();

    private int accountsCount;

    public void addAccount(Account account) {
        this.getAccounts().add(account);
        this.accountsCount++;
    }

    public void removeAccount(Account account) {
        this.getAccounts().remove(account);
        this.accountsCount--;
    }

    public Education(Long id, String name, String subject, YN isDelete, Set<Account> accounts, int accountsCount) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.isDelete = isDelete;
        this.accounts = accounts;
        this.accountsCount = accountsCount;
    }
}
