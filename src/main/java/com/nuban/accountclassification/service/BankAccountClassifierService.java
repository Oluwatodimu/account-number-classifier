package com.nuban.accountclassification.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuban.accountclassification.dto.BankDataDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Todimu Isewon
 * Date : 04/09/2022
 */

@Service
public class BankAccountClassifierService {

    private static final int MOD_VALUE = 10;
    private static final Long ACCOUNT_NUMBER_LENGTH = 10L;
    private static final Long SERIAL_NUMBER_LENGTH = 9L;
    private static final String SEED = "373373373373";

    /**
     * Function to return list of possible bank
     * @param accountNumber
     * @return List of banks that satisfy the validation condition
     */
    public List<BankDataDto> getListOfPossibleBanks(String accountNumber) throws IOException {
        List<BankDataDto> listOfBanks = getBankAndCbnCodes();

        List<BankDataDto> possibleBanksForAccountNumber = listOfBanks
                .stream()
                .filter(
                        bankDataDTO -> {
                            if (bankAccountValid(accountNumber, bankDataDTO.getUniqueCbnBankCode())) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                )
                .collect(Collectors.toList());

        return possibleBanksForAccountNumber;
    }

    /**
     * a list of all banks and their unique CBN codes which can be updated.
     * @return list of banks and their associated bank codes
     */
    public List<BankDataDto> getBankAndCbnCodes() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
       List<BankDataDto> bankDataList = objectMapper.readValue(
               new File("src/main/resources/bank_list.json"),
               new TypeReference<>() {
               }
       );

        return bankDataList;
    }

    /**
     * check digit is used to check what bank an account number belongs to and to validate account
     * @param serialNumber
     * @param bankCode
     * @return checkDigit to be used for verifications
     */
    public Long generateCheckDigit(String serialNumber, String bankCode) {
        if (serialNumber.length() > SERIAL_NUMBER_LENGTH) {
            throw new RuntimeException("serial number length cannot be more than 9 digits");
        }

        String paddedSerialNumber = padStart(serialNumber, SERIAL_NUMBER_LENGTH, "0");
        String bankCodeAndSerialNumber = paddedSerialNumber + bankCode;
        Long computationSum = makeComputationWithSerialNumberAndBankCode(bankCodeAndSerialNumber, SEED);
        Long modOfComputationSum = computationSum % MOD_VALUE;
        Long checkDigit = MOD_VALUE - modOfComputationSum;

        if (checkDigit == MOD_VALUE) {
            checkDigit = Long.valueOf(0L);
        }

        return checkDigit;
    }

    /**
     * Calculate A*3+B*7+C*3+D*3+E*7+F*3+G*3+H*7+I*3+J*3+K*7+L*3
     * @param serialNumber
     * @param computationValues
     * @return
     */
    public Long makeComputationWithSerialNumberAndBankCode(String serialNumber, String computationValues) {
        if (serialNumber.length() != computationValues.length()) {
            throw new RuntimeException("parameters must be of same length");
        }

        if (!serialNumber.matches("^[0-9]*$") || !computationValues.matches("^[0-9]*$")) {
            throw new RuntimeException("values must contain only digits");
        }

        String[] splitSerialNumber = serialNumber.split("");
        String[] splitComputationValues = computationValues.split("");
        Long productSum = 0L;

        for (int i = 0; i < splitSerialNumber.length; i++) {
            Long serialNumVal = Long.valueOf((splitSerialNumber[i]));
            Long compVal = Long.valueOf(splitComputationValues[i]);

            productSum += (serialNumVal * compVal);
        }

        return productSum;
    }

    /**
     * checks if a bank account is valid
     * @param accountNumber
     * @param bankCode
     * @return boolean value
     */
    public Boolean bankAccountValid(String accountNumber, String bankCode) {
        if (!accountNumber.matches("^[0-9]*$") || !(accountNumber.length() == ACCOUNT_NUMBER_LENGTH)) {
            return false;
        }

        String serialNumber = accountNumber.substring(0, 9);
        Long checkDigit = generateCheckDigit(serialNumber, bankCode);
        return checkDigit == Long.valueOf(accountNumber.substring(accountNumber.length() - 1));
    }

    /**
     * created the javascript equivalent of padStart in Java
     * @param serialNumber
     * @param serialNumberLength
     * @param padWith
     * @return padded string value
     */
    public String padStart(String serialNumber, Long serialNumberLength, String padWith) {
        StringBuilder paddedSerialNumber = new StringBuilder();
        paddedSerialNumber.append(serialNumber);

        while (paddedSerialNumber.length() < serialNumberLength) {
            paddedSerialNumber.insert(0, padWith);
        }

        return paddedSerialNumber.toString();
    }
}
