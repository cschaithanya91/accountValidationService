package com.account.validation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderRequest {
    private String sortCode;
    private String bankAccount;
}
