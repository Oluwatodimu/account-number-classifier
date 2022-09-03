package com.nuban.accountclassification.service;

import com.nuban.accountclassification.dto.BankDataDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Todimu Isewon
 * Date : 04/09/2022
 */

@Service
public class BankAccountClassifierService {

    public static final int MOD_VALUE = 10;
    private Long ACCOUNT_NUMBER_LENGTH = 10L;
    private Long SERIAL_NUMBER_LENGTH = 9L;
    private String SEED = "373373373373";

    /**
     * Function to return list of possible bank
     * @param accountNumber
     * @return List of banks that satisfy the validation condition
     */
    public List<BankDataDto> getListOfPossibleBanks(String accountNumber) {
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
    public ArrayList<BankDataDto> getBankAndCbnCodes() {

        ArrayList<BankDataDto> bankDataList = new ArrayList<>();
        bankDataList.add(new BankDataDto("Access Bank", "044"));
        bankDataList.add(new BankDataDto("Access Money", "323"));
        bankDataList.add(new BankDataDto("ASO Savings and & Loans", "401"));
        bankDataList.add(new BankDataDto("Cellulant", "317"));
        bankDataList.add(new BankDataDto("ChamsMobile", "303"));
        bankDataList.add(new BankDataDto("Citi Bank", "023"));
        bankDataList.add(new BankDataDto("Coronation Merchant Bank", "559"));
        bankDataList.add(new BankDataDto("Covenant Microfinance Bank", "551"));
        bankDataList.add(new BankDataDto("Diamond Bank", "063"));
        bankDataList.add(new BankDataDto("Eartholeum", "302"));
        bankDataList.add(new BankDataDto("Ecobank Nigeria", "050"));
        bankDataList.add(new BankDataDto("EcoMobile", "307"));
        bankDataList.add(new BankDataDto("Enterprise Bank", "084"));
        bankDataList.add(new BankDataDto("eTranzact", "306"));
        bankDataList.add(new BankDataDto("FBNMobile", "309"));
        bankDataList.add(new BankDataDto("Fidelity Bank", "070"));
        bankDataList.add(new BankDataDto("Fidelity Mobile", "318"));
        bankDataList.add(new BankDataDto("First Bank", "011"));
        bankDataList.add(new BankDataDto("FCMB", "214"));
        bankDataList.add(new BankDataDto("FET", "314"));
        bankDataList.add(new BankDataDto("Fortis Microfinance Bank", "501"));
        bankDataList.add(new BankDataDto("Fortis Mobile", "308"));
        bankDataList.add(new BankDataDto("FSDH", "601"));
        bankDataList.add(new BankDataDto("Globus Bank", "103"));
        bankDataList.add(new BankDataDto("GTB", "058"));
        bankDataList.add(new BankDataDto("GTMobile", "315"));
        bankDataList.add(new BankDataDto("Hedonmark", "324"));
        bankDataList.add(new BankDataDto("Heritage Bank", "030"));
        bankDataList.add(new BankDataDto("Imperial Homes Mortgage Bank", "415"));
        bankDataList.add(new BankDataDto("Jaiz Bank", "301"));
        bankDataList.add(new BankDataDto("Jubilee Life Mortgage Bank", "402"));
        bankDataList.add(new BankDataDto("Keystone Bank", "082"));
        bankDataList.add(new BankDataDto("Kuda Bank", "611"));
        bankDataList.add(new BankDataDto("Mainstreet bank", "014"));
        bankDataList.add(new BankDataDto("Mkudi", "313"));
        bankDataList.add(new BankDataDto("MoneyBox", "325"));
        bankDataList.add(new BankDataDto("NIP Virtual Bank", "999"));
        bankDataList.add(new BankDataDto("NPF MicroFinance Bank", "552"));
        bankDataList.add(new BankDataDto("Omoluabi Mortgage Bank", "990"));
        bankDataList.add(new BankDataDto("Pagatech", "327"));
        bankDataList.add(new BankDataDto("Page MFBank", "560"));
        bankDataList.add(new BankDataDto("Parralex", "526"));
        bankDataList.add(new BankDataDto("PayAttitude Online", "329"));
        bankDataList.add(new BankDataDto("Paycom", "305"));
        bankDataList.add(new BankDataDto("Polaris Bank", "076"));
        bankDataList.add(new BankDataDto("Providus Bank", "101"));
        bankDataList.add(new BankDataDto("ReadyCash (Parkway)", "311"));
        bankDataList.add(new BankDataDto("Rubies Bank", "587"));
        bankDataList.add(new BankDataDto("SafeTrust Mortgage Bank", "403"));
        bankDataList.add(new BankDataDto("Skye Bank", "076"));
        bankDataList.add(new BankDataDto("Stanbic IBTC Bank", "221"));
        bankDataList.add(new BankDataDto("Stanbic Mobile Money", "304"));
        bankDataList.add(new BankDataDto("Standard Chartered Bank", "068"));
        bankDataList.add(new BankDataDto("Sterling Bank", "232"));
        bankDataList.add(new BankDataDto("Sterling Mobile", "326"));
        bankDataList.add(new BankDataDto("Suntrust", "100"));
        bankDataList.add(new BankDataDto("TagPay", "328"));
        bankDataList.add(new BankDataDto("TeasyMobile", "319"));
        bankDataList.add(new BankDataDto("Titan Trust Bank", "102"));
        bankDataList.add(new BankDataDto("Trustbond", "523"));
        bankDataList.add(new BankDataDto("Union Bank", "032"));
        bankDataList.add(new BankDataDto("UBA", "033"));
        bankDataList.add(new BankDataDto("Unity Bank", "215"));
        bankDataList.add(new BankDataDto("V Bank", "566"));
        bankDataList.add(new BankDataDto("VTNetworks", "320"));
        bankDataList.add(new BankDataDto("Wema Bank", "035"));
        bankDataList.add(new BankDataDto("Zenith Bank", "057"));
        bankDataList.add(new BankDataDto("Zenith Mobile", "322"));

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
