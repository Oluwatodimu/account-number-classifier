package com.nuban.accountclassification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDataDto {
    private String bankName;
    private String uniqueCbnBankCode;
}
