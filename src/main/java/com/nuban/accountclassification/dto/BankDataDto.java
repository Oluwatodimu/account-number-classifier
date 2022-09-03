package com.nuban.accountclassification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDataDto {
    private String bankName;
    private String uniqueCbnBankCode;
}
