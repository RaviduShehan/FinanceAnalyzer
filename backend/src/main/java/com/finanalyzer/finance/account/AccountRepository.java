package com.finanalyzer.finance.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByNameIgnoreCaseAndInstitutionNameIgnoreCase(
            String name,
            String institutionName
    );

    boolean existsByNameIgnoreCaseAndInstitutionNameIgnoreCaseAndIdNot(
            String name,
            String institutionName,
            Long id
    );
}