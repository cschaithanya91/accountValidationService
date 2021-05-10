package com.account.validation.model;

import java.util.List;

import lombok.Data;

@Data
public class ServiceResponse {
    private List<ProviderStatus> result;
}
