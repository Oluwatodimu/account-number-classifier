package com.nuban.accountclassification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomApiResponseDto {
    private Integer code;
    private String message;
    private Object data;
}
