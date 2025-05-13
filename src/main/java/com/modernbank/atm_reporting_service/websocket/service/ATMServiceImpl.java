package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.exceptions.CreateFailedException;
import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.exceptions.ProcessFailedException;
import com.modernbank.atm_reporting_service.model.dto.ATMDTO;
import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import com.modernbank.atm_reporting_service.model.dto.BankDTO;
import com.modernbank.atm_reporting_service.model.entity.ATM;
import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import com.modernbank.atm_reporting_service.repository.ATMRepository;
import com.modernbank.atm_reporting_service.repository.BankRepository;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.*;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_CREATION_FAILED;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_NOT_FOUND_BY_ATMID;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_SUPPORTED_BANK_ADD_PROCESS_FAILED;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements IATMService {

    private final ATMRepository atmRepository;

    private final BankRepository bankRepository;

    private final IMapperService mapperService;

    @Override
    public BaseResponse createATM(CreateATMRequest request) {
        try {
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

        } catch (Exception e) {
            throw new CreateFailedException(ATM_CREATION_FAILED);
        }
    }

    @Override
    public BaseResponse updateATM(UpdateATMRequest request) {
        ATM atm = atmRepository.getATMByATMId(request.getAtmId()).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));

        try {
            if (request.getSupportedBanks() != null) {
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
        } catch (Exception e) {
            throw new ProcessFailedException(ATM_SUPPORTED_BANK_ADD_PROCESS_FAILED);
        }
    }

    @Override
    public ATMStatusUpdateResponse getATMStatusDetail(String atmId) {
        ATM atm = atmRepository.getATMByATMId(atmId).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));

        return ATMStatusUpdateResponse.builder()
                .atmStatusDTO(mapperService.map(atm, ATMStatusUpdateDTO.class))
                .build();
    }

    @Override
    public GetAllATMResponse getAllATMs(String atmId) {
        List<ATM> atmList = atmRepository.getATMsByLocation(atmId).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));

        List<ATMDTO> atmDTOs = atmList.stream()
//                .filter(atm -> atm.getSupportedBanks().isEmpty())
                .map(atm -> mapperService.map(atm, ATMDTO.class))
                .toList();

        return GetAllATMResponse.builder()
                .atmStatusDTOList(atmDTOs)
                .build();
    }

    @Override
    public ATMStatusResponse getATMStatus() {
        return ATMStatusResponse.builder()
                .banks(
                        bankRepository.findBanksByAll("all").stream()
                                .map(bank -> mapperService.map(bank, BankDTO.class))
                                .map(BankDTO::getName)
                                .toList()
                )
                .statuses(Arrays.stream(ATMStatus.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()))
                .depositStatuses(Arrays.stream(ATMDepositStatus.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()))
                .withdrawStatuses(Arrays.stream(ATMWithdrawStatus.values())
                        .map(ATMWithdrawStatus::getStatus)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GetATMNameAndIDResponse getATmById(String atmId) {
        ATM atm = atmRepository.getATMByATMId(atmId).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));
        return GetATMNameAndIDResponse.builder()
                .id(atm.getId())
                .name(atm.getName())
                .build();
    }
}