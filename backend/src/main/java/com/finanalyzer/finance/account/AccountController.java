package com.finanalyzer.finance.account;

import com.finanalyzer.finance.account.dto.AccountResponseDTO;
import com.finanalyzer.finance.account.dto.CreateAccountRequestDTO;
import com.finanalyzer.finance.account.dto.UpdateAccountRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public AccountResponseDTO create(@Valid @RequestBody CreateAccountRequestDTO request) {
        return accountService.create(request);
    }

    @GetMapping("/findAll")
    public List<AccountResponseDTO> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/findById/{id}")
    public AccountResponseDTO findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PutMapping("/update/{id}")
    public AccountResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAccountRequestDTO request
    ) {
        return accountService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}