package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.exceptions.CreateFailedException;
import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.model.entity.Bank;
import com.modernbank.atm_reporting_service.model.enums.BankStatus;
import com.modernbank.atm_reporting_service.repository.BankRepository;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateBankRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements IBankService {

    private final BankRepository bankRepository;

    @Override
    public BaseResponse createBank(CreateBankRequest request) {
        try {
            bankRepository.save(Bank.builder()
                    .name(request.getName())
                    .status(BankStatus.INACTIVE)
                    .atms(new HashSet<>())
                    .createDate(LocalDate.now().toString())
                    .lastUpdateDate(LocalDate.now().toString())
                    //.createdById(request.getCreatedById())
                    .build());

            return new BaseResponse("Bank Created Successfully");
        } catch (Exception e) {
            throw new CreateFailedException("Bank Creation Failed");
        }
    }

    @Override
    public BaseResponse activateBank(UpdateBankRequest request) {
        Bank bank = bankRepository.findById(request.getBankId()).orElseThrow(() -> new NotFoundException("Bank Not Found"));
        bank.setStatus(request.getStatus());
        //bank.getUpdatedById();
        bankRepository.save(bank);
        return new BaseResponse("Bank status updated Successfully");
    }

    @Override
    public BaseResponse updateBank(UpdateBankRequest request) {
        Bank bank = bankRepository.findById(request.getBankId()).orElseThrow(() -> new NotFoundException("Bank Not Found"));
        bank.setName(request.getName());
        bank.setStatus(request.getStatus());
        bank.setLastUpdateDate(LocalDate.now().toString());
        //bank.setUpdatedById(request.getUpdatedById());

        bankRepository.save(bank);
        return new BaseResponse("Bank Updated Successfully");
    }
}