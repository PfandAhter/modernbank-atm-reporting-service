package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import com.modernbank.atm_reporting_service.model.entity.ATM;
import com.modernbank.atm_reporting_service.model.entity.Bank;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.repository.ATMRepository;
import com.modernbank.atm_reporting_service.repository.BankRepository;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements IATMService {

    private final ATMRepository atmRepository;

    private final BankRepository bankRepository;

    private final IMapperService mapperService;

    @Override
    public ATMStatusUpdateResponse getATMStatusDetail(String atmId){
        ATM atm = atmRepository.getATMStatusDetailsByATMId(atmId).orElseThrow(() -> new NotFoundException("ATM IS NOT FOUND BY THAT ATMID"));

        return ATMStatusUpdateResponse.builder()
                .atmStatusDTO(mapperService.map(atm, ATMStatusUpdateDTO.class))
                .build();
    }


    public void createATMs(){
        /*Bank bank = new Bank();
        bank.setName("Modern Bank");
        bank.setActive(true);
        bank.setCreateDate("2023-10-01T12:00:00Z");
        bank.setLastUpdateDate("2023-10-01T12:00:00Z");

        Bank bank2 = new Bank();
        bank2.setName("Modern Bank V2 TEST");
        bank2.setActive(true);
        bank2.setCreateDate("2023-10-01T12:00:00Z");
        bank2.setLastUpdateDate("2023-10-01T12:00:00Z");

        Set<Bank> testBanks = new HashSet<>(){
            {
                add(bank);
                add(bank2);
            }
        };

        ATM atm = new ATM();
        atm.setStatus(ATMStatus.ACTIVE);
        atm.setLatitude("38.024050");
        atm.setLongitude("32.510783");
        atm.setCreatedDate("2023-10-01T12:00:00Z");
        atm.setLastUpdatedDate("2023-10-01T12:00:00Z");
        atm.setSupportedBanks(testBanks);

        ATM atm2 = new ATM();
        atm2.setStatus(ATMStatus.ACTIVE);
        atm2.setLatitude("38.023999");
        atm2.setLongitude("32.510850");
        atm2.setCreatedDate("2023-10-01T12:00:00Z");
        atm2.setLastUpdatedDate("2023-10-01T12:00:00Z");
        atm2.setSupportedBanks(testBanks);

        bankRepository.save(bank);
        bankRepository.save(bank2);

        atmRepository.save(atm);
        atmRepository.save(atm2);
*/

    }
}