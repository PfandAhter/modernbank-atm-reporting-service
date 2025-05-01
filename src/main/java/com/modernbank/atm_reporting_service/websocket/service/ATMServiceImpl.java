package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.exceptions.CreateFailedException;
import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.exceptions.ProcessFailedException;
import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import com.modernbank.atm_reporting_service.model.entity.ATM;
import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import com.modernbank.atm_reporting_service.repository.ATMRepository;
import com.modernbank.atm_reporting_service.repository.BankRepository;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.ATMStatusUpdateResponse;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements IATMService {

    private final ATMRepository atmRepository;

    private final BankRepository bankRepository;

    private final IMapperService mapperService;

    @Override
    public BaseResponse createATM(CreateATMRequest request){
        try{
            atmRepository.save(ATM.builder()
                    .name(request.getName())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .city(request.getCity())
                    .district(request.getDistrict())
                    .address(request.getAddress())
                    .supportedBanks(new HashSet<>())
                    .status(ATMStatus.INACTIVE)
                    .depositStatus(ATMDepositStatus.INACTIVE)
                    .withdrawStatus(ATMWithdrawStatus.INACTIVE)
                    .createdDate(LocalDate.now().toString())
                    .lastUpdatedDate(LocalDate.now().toString())
                    .build());

            return new BaseResponse("ATM CREATED SUCCESSFULLY");

        }catch (Exception e){
            throw new CreateFailedException("ATM CREATION FAILED");
        }
    }

    @Override
    public BaseResponse updateATM(UpdateATMRequest request){
        ATM atm = atmRepository.getATMByATMId(request.getAtmId()).orElseThrow(() -> new NotFoundException("ATM IS NOT FOUND BY THAT ATMID"));

        try{
            if(request.getSupportedBanks() != null){
                request.getSupportedBanks()
                        .stream()
                        .filter(bank -> bankRepository.findBankByName(bank).isPresent())
                        .map(bank -> bankRepository.findBankByName(bank).get())
                        .forEach(bank -> atm.getSupportedBanks().add(bank));
            }

            atm.setStatus(request.getStatus());
            atm.setDepositStatus(request.getDepositStatus());
            atm.setWithdrawStatus(request.getWithdrawStatus());

            atmRepository.save(atm);
            return new BaseResponse("ATM UPDATED SUCCESSFULLY");
        }catch (Exception e){
            throw new ProcessFailedException("ATM SUPPORTED BANK ADD PROCESS FAILED");
        }
    }

    @Override
    public ATMStatusUpdateResponse getATMStatusDetail(String atmId){
        ATM atm = atmRepository.getATMByATMId(atmId).orElseThrow(() -> new NotFoundException("ATM IS NOT FOUND BY THAT ATMID"));

        return ATMStatusUpdateResponse.builder()
                .atmStatusDTO(mapperService.map(atm, ATMStatusUpdateDTO.class))
                .build();
    }
}