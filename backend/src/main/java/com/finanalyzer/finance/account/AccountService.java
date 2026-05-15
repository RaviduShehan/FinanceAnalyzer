package com.finanalyzer.finance.account;

import com.finanalyzer.finance.account.dto.AccountResponseDTO;
import com.finanalyzer.finance.account.dto.CreateAccountRequestDTO;
import com.finanalyzer.finance.account.dto.UpdateAccountRequestDTO;
import com.finanalyzer.finance.common.exception.DuplicateResourceException;
import com.finanalyzer.finance.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDTO create(CreateAccountRequestDTO request) {
        String name = normalizeText(request.name());
        String institutionName = normalizeText(request.institutionName());
        String currency = normalizeCurrency(request.currency());

        if (accountRepository.existsByNameIgnoreCaseAndInstitutionNameIgnoreCase(name, institutionName)) {
            throw new DuplicateResourceException(
                    "Account already exists with name: " + name + " for institution: " + institutionName
            );
        }

        validateAccountRules(request.type(), request.creditLimit());

        Account account = new Account();
        account.setName(name);
        account.setInstitutionName(institutionName);
        account.setType(request.type());
        account.setBalance(request.balance());
        account.setCurrency(currency);
        account.setCreditLimit(resolveCreditLimit(request.type(), request.creditLimit()));

        Account savedAccount = accountRepository.save(account);
        return toResponse(savedAccount);
    }

    public List<AccountResponseDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AccountResponseDTO findById(Long id) {
        Account account = getAccountOrThrow(id);
        return toResponse(account);
    }

    public AccountResponseDTO update(Long id, UpdateAccountRequestDTO request) {
        Account account = getAccountOrThrow(id);

        String name = normalizeText(request.name());
        String institutionName = normalizeText(request.institutionName());
        String currency = normalizeCurrency(request.currency());

        if (accountRepository.existsByNameIgnoreCaseAndInstitutionNameIgnoreCaseAndIdNot(
                name,
                institutionName,
                id
        )) {
            throw new DuplicateResourceException(
                    "Account already exists with name: " + name + " for institution: " + institutionName
            );
        }

        validateAccountRules(request.type(), request.creditLimit());

        account.setName(name);
        account.setInstitutionName(institutionName);
        account.setType(request.type());
        account.setBalance(request.balance());
        account.setCurrency(currency);
        account.setCreditLimit(resolveCreditLimit(request.type(), request.creditLimit()));

        Account updatedAccount = accountRepository.save(account);
        return toResponse(updatedAccount);
    }

    public void deleteById(Long id) {
        Account account = getAccountOrThrow(id);
        accountRepository.delete(account);
    }

    private Account getAccountOrThrow(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + id
                ));
    }

    private void validateAccountRules(AccountType type, BigDecimal creditLimit) {
        if (type == AccountType.CREDIT_CARD && creditLimit == null) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "Credit limit is required for credit card accounts"
            );
        }
    }

    private BigDecimal resolveCreditLimit(AccountType type, BigDecimal creditLimit) {
        if (type == AccountType.CREDIT_CARD) {
            return creditLimit;
        }

        return null;
    }

    private String normalizeText(String value) {
        return value.trim();
    }

    private String normalizeCurrency(String currency) {
        return currency.trim().toUpperCase();
    }

    private AccountResponseDTO toResponse(Account account) {
        BigDecimal availableCredit = null;

        if (account.getType() == AccountType.CREDIT_CARD && account.getCreditLimit() != null) {
            availableCredit = account.getCreditLimit().subtract(account.getBalance());
        }

        return new AccountResponseDTO(
                account.getId(),
                account.getName(),
                account.getInstitutionName(),
                account.getType(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreditLimit(),
                availableCredit,
                account.getCreatedAt()
        );
    }
}