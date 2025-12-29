package com.modernbank.atm_reporting_service.websocket.service;

import com.modernbank.atm_reporting_service.api.client.NotificationServiceClient;
import com.modernbank.atm_reporting_service.api.request.ChatNotificationSendRequest;
import com.modernbank.atm_reporting_service.api.request.NotificationMessage;
import com.modernbank.atm_reporting_service.exceptions.CreateFailedException;
import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.exceptions.ProcessFailedException;
import com.modernbank.atm_reporting_service.model.dto.ATMDTO;
import com.modernbank.atm_reporting_service.model.dto.ATMStatusUpdateDTO;
import com.modernbank.atm_reporting_service.model.dto.BankDTO;
import com.modernbank.atm_reporting_service.model.dto.RouteInfo;
import com.modernbank.atm_reporting_service.model.entity.ATM;
import com.modernbank.atm_reporting_service.model.enums.ATMDepositStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMStatus;
import com.modernbank.atm_reporting_service.model.enums.ATMWithdrawStatus;
import com.modernbank.atm_reporting_service.repository.ATMRepository;
import com.modernbank.atm_reporting_service.repository.BankRepository;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.GenerateRouteToATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.GetNearestATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.UpdateATMRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.*;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IATMService;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IMapperService;
import com.modernbank.atm_reporting_service.websocket.service.route.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_CREATION_FAILED;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_NOT_FOUND_BY_ATMID;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ATM_SUPPORTED_BANK_ADD_PROCESS_FAILED;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ATMServiceImpl implements IATMService {

    private final ATMRepository atmRepository;

    private final BankRepository bankRepository;

    private final IMapperService mapperService;

    private final NotificationServiceClient notificationServiceClient;

    private final RouteService routeService;

    @Override
    public BaseResponse createATM(CreateATMRequest request) {
        try {
            atmRepository.save(ATM.builder()
                    .name(request.getName())
                    .latitude(Double.valueOf(request.getLatitude()))
                    .longitude(Double.valueOf(request.getLongitude()))
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
        log.info("Fetching ATM status details for ATM ID: {}", atmId);
        ATM atm = atmRepository.getATMByATMId(atmId).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));

        log.info("ATM found: {} with status: {}", atm.getName(), atm.getStatus());
        return ATMStatusUpdateResponse.builder()
                .atmStatusDTO(mapperService.map(atm, ATMStatusUpdateDTO.class))
                .build();
    }

    @Override
    public GetAllATMResponse getAllATMs(String atmId) {
        log.info("Fetching all ATMs for ATM ID: {}", atmId);
        List<ATM> atmList = atmRepository.getATMsByLocation(atmId).orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND_BY_ATMID));

        List<ATMDTO> atmDTOs = atmList.stream()
//                .filter(atm -> atm.getSupportedBanks().isEmpty())
                .map(atm -> mapperService.map(atm, ATMDTO.class))
                .toList();

        log.info("Total ATMs fetched: {}", atmDTOs.size());
        return GetAllATMResponse.builder()
                .atmStatusDTOList(atmDTOs)
                .build();
    }

    @Override
    public ATMStatusResponse getATMStatus() {
        log.info("Fetching ATM status information");
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

    @Override
    public NearestATMResponse getNearestATM(GetNearestATMRequest request) {
        double lat, lon;
        try {
            lat = Double.parseDouble(request.getLatitude());
            lon = Double.parseDouble(request.getLongitude());
        } catch (NumberFormatException e) {
            throw new ProcessFailedException("INVALID_COORDINATES");
        }

        List<String> nearestIds = atmRepository.findNearestAtmIds(lat, lon, 50);

        if (nearestIds == null || nearestIds.isEmpty()) {
            throw new NotFoundException("ATM_NOT_FOUND_NEARBY");
        }

        String bankName = request.getBankName() != null ? request.getBankName().trim() : null;
        List<ATM> nearestAtms = atmRepository.findAllById(nearestIds);

        if (nearestAtms.isEmpty()) {
            throw new NotFoundException("ATM_NOT_FOUND");
        }

        List<ATM> candidateAtms = filterCandidateAtms(nearestAtms, bankName, lat, lon);

        if (candidateAtms.isEmpty()) {
            throw new NotFoundException("ATM_NOT_FOUND");
        }

        ATMWithRouteInfo nearest = findNearestByRealRoute(candidateAtms, lat, lon);

        String matchedBankName = resolveMatchedBankName(nearest.atm(), bankName);

        return NearestATMResponse.builder()
                .selectedAtmId(nearest.atm().getId())
                .bankName(matchedBankName)
                .selectedAtmLatitude(String.valueOf(nearest.atm().getLatitude()))
                .selectedAtmLongitude(String.valueOf(nearest.atm().getLongitude()))
                .userLatitude(request.getLatitude())
                .userLongitude(request.getLongitude())
                //.distanceMeters(nearest.routeInfo().distanceMeters())
                //.durationMinutes(nearest.routeInfo().getDurationMinutes())
                //.routeProvider(nearest.routeInfo().providerName())
                .build();
    }

    /**
     * Kuş uçuşu ve durum bazlı aday ATM listesi oluşturur
     */
    private List<ATM> filterCandidateAtms(List<ATM> nearestAtms, String bankName,
                                          double lat, double lon) {
        List<ATM> sorted = nearestAtms.stream()
                .sorted(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)))
                .collect(Collectors.toList());

        if (bankName == null || bankName.isEmpty()) {
            return sorted.stream().limit(10).collect(Collectors.toList());
        }

        List<ATM> candidates = new ArrayList<>();

        List<ATM> fullyActive = sorted.stream()
                .filter(atm -> atm.getName().equalsIgnoreCase(bankName))
                .filter(atm -> ATMStatus.ACTIVE.equals(atm.getStatus())
                        && ATMDepositStatus.ACTIVE.equals(atm.getDepositStatus())
                        && ATMWithdrawStatus.ACTIVE.equals(atm.getWithdrawStatus()))
                .limit(5)
                .collect(Collectors.toList());
        candidates.addAll(fullyActive);

        List<ATM> finalCandidates = candidates;
        List<ATM> depositActive = sorted.stream()
                .filter(atm -> atm.getName().equalsIgnoreCase(bankName))
                .filter(atm -> ATMDepositStatus.ACTIVE.equals(atm.getDepositStatus()))
                .filter(atm -> !finalCandidates.contains(atm))
                .limit(3)
                .collect(Collectors.toList());
        candidates.addAll(depositActive);

        List<ATM> anyMatching = sorted.stream()
                .filter(atm -> atm.getName().equalsIgnoreCase(bankName))
                .filter(atm -> !finalCandidates.contains(atm))
                .limit(2)
                .collect(Collectors.toList());
        candidates.addAll(anyMatching);
        if (candidates.isEmpty()) {
            candidates = sorted.stream().limit(5).collect(Collectors.toList());
        }

        return candidates;
    }

    private ATMWithRouteInfo findNearestByRealRoute(List<ATM> candidates,
                                                    double userLat, double userLon) {
        log.info("Calculating real routes for {} candidate ATMs using provider: {}",
                candidates.size(), routeService.getActiveProvider());

        return candidates.stream()
                .map(atm -> {
                    RouteInfo route = routeService.getRoute(
                            userLat, userLon,
                            atm.getLatitude(), atm.getLongitude()
                    );
                    return new ATMWithRouteInfo(atm, route);
                })
                .filter(awr -> awr.routeInfo() != null)
                .min(Comparator.comparingDouble(awr -> awr.routeInfo().distanceMeters()))
                .orElseThrow(() -> new ProcessFailedException("ROUTE_CALCULATION_FAILED"));
    }

    private String resolveMatchedBankName(ATM atm, String requestedBankName) {
        if (requestedBankName == null || atm.getSupportedBanks() == null) {
            return atm.getName();
        }

        return atm.getSupportedBanks().stream()
                .filter(b -> b.getName() != null && b.getName().equalsIgnoreCase(requestedBankName))
                .map(b -> b.getName())
                .findFirst()
                .orElse(atm.getName());
    }

    private record ATMWithRouteInfo(ATM atm, RouteInfo routeInfo) {}

    private double distanceMeters(ATM atm, double userLat, double userLon) {
        try {
            double atmLat = atm.getLatitude();
            double atmLon = atm.getLongitude();
            int R = 6371000;
            double phi1 = Math.toRadians(userLat);
            double phi2 = Math.toRadians(atmLat);
            double dPhi = Math.toRadians(atmLat - userLat);
            double dLambda = Math.toRadians(atmLon - userLon);
            double a = Math.sin(dPhi/2) * Math.sin(dPhi/2) +
                    Math.cos(phi1) * Math.cos(phi2) *
                            Math.sin(dLambda/2) * Math.sin(dLambda/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return R * c;
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }


    /*@Override
    public NearestATMResponse getNearestATM(GetNearestATMRequest request) {
        double lat, lon;
        try {
            lat = Double.parseDouble(request.getLatitude());
            lon = Double.parseDouble(request.getLongitude());
        } catch (NumberFormatException e) {
            throw new ProcessFailedException("INVALID_COORDINATES");
        }

        // Öncelikle en yakın 50 ATM id'sini al (limit ihtiyaca göre)
        List<String> nearestIds = atmRepository.findNearestAtmIds(lat, lon, 50);

        if (nearestIds == null || nearestIds.isEmpty()) {
            // Hiç ATM yoksa anlamlı hata dön veya boş response ile mesaj ver
            throw new NotFoundException("ATM_NOT_FOUND_NEARBY");
        }

        // Eğer user bankName vermediyse en yakınını dön
        String bankName = request.getBankName() != null ? request.getBankName().trim() : null;

        // Load ATM entities only for those nearest ids (performans: batched query)
        List<ATM> nearestAtms = atmRepository.findAllById(nearestIds);

        if (nearestAtms.isEmpty()) {
            throw new NotFoundException("ATM_NOT_FOUND");
        }

        // Eğer bankName yok -> en yakını seç
        if (bankName == null || bankName.isEmpty()) {
            ATM selected = nearestAtms.stream()
                    .sorted(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("ATM_NOT_FOUND"));

            double dist = distanceMeters(selected, lat, lon);

            return NearestATMResponse.builder()
                    .selectedAtmId(selected.getId())
                    .bankName(selected.getName())
                    .selectedAtmLatitude(String.valueOf(selected.getLatitude()))
                    .selectedAtmLongitude(String.valueOf(selected.getLongitude()))
                    .userLatitude(request.getLatitude())
                    .userLongitude(request.getLongitude())
//                    .distanceMeters(dist)
//                    .note(dist > 1000 ? "En yakın ATM 1 km'den uzakta." : null)
                    .build();
        }

        // Eğer bankName varsa önce tercih edilen duruma uyanı bul
        Predicate<ATM> bankMatches = atm -> atm.getSupportedBanks() != null && atm.getSupportedBanks().stream()
                .anyMatch(b -> b.getName() != null && b.getName().equalsIgnoreCase(bankName));

        Optional<ATM> preferred = nearestAtms.stream()
                .filter(atm -> {
                    return atm.getName().equalsIgnoreCase(bankName);
                })
                .filter(atm -> ATMStatus.ACTIVE.equals(atm.getStatus())
                        && ATMDepositStatus.ACTIVE.equals(atm.getDepositStatus())
                        && ATMWithdrawStatus.ACTIVE.equals(atm.getWithdrawStatus()))
                .min(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)));

        Optional<ATM> depositOnly = nearestAtms.stream()
                .filter(atm -> {
                    return atm.getName().equalsIgnoreCase(bankName);
                })
                .filter(atm -> ATMDepositStatus.ACTIVE.equals(atm.getDepositStatus()))
                .min(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)));

        Optional<ATM> lastMatched = nearestAtms.stream()
                .filter(atm -> {
                    return atm.getName().equalsIgnoreCase(bankName);
                })
                .min(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)));

        ATM matchedATM = preferred.orElse(depositOnly.orElse(lastMatched.orElseGet(() -> {
            // Eğer aynı bankaya ait ATM yoksa fallback: en yakın herhangi bir ATM
            return nearestAtms.stream()
                    .min(Comparator.comparingDouble(atm -> distanceMeters(atm, lat, lon)))
                    .orElseThrow(() -> new NotFoundException("ATM_NOT_FOUND"));
        })));

        double dist = distanceMeters(matchedATM, lat, lon);

        String matchedBankName = matchedATM.getSupportedBanks() == null ? bankName :
                matchedATM.getSupportedBanks().stream()
                        .filter(b -> b.getName() != null && b.getName().equalsIgnoreCase(bankName))
                        .map(b -> b.getName())
                        .findFirst()
                        .orElse(bankName);

        return NearestATMResponse.builder()
                .selectedAtmId(matchedATM.getId())
                .bankName(matchedBankName)
                .selectedAtmLatitude(String.valueOf(matchedATM.getLatitude()))
                .selectedAtmLongitude(String.valueOf(matchedATM.getLongitude()))
                .userLatitude(request.getLatitude())
                .userLongitude(request.getLongitude())
                //.distanceMeters(dist)
//                .note(dist > 1000 ? "En yakın ATM 1 km'den uzakta." : null)
                .build();
    }

    // Yardımcı fonksiyon (Haversine veya ST_Distance_Sphere ile eşdeğer)
    private double distanceMeters(ATM atm, double userLat, double userLon) {
        try {
            double atmLat = atm.getLatitude();
            double atmLon = atm.getLongitude();
            // Basit Haversine hesaplaması
            int R = 6371000; // metres
            double phi1 = Math.toRadians(userLat);
            double phi2 = Math.toRadians(atmLat);
            double dPhi = Math.toRadians(atmLat - userLat);
            double dLambda = Math.toRadians(atmLon - userLon);
            double a = Math.sin(dPhi/2) * Math.sin(dPhi/2) +
                    Math.cos(phi1) * Math.cos(phi2) *
                            Math.sin(dLambda/2) * Math.sin(dLambda/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return R * c;
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }*/

    @Override
    public void generateRouteToATM(GenerateRouteToATMRequest request) {
        Map<String, Object> arguments = Map.of(
                "userLatitude", request.getUserLatitude(),
                "userLongitude", request.getUserLongitude(),
                "atmLatitude", request.getSelectedAtmLatitude(),
                "atmLongitude", request.getSelectedAtmLongitude(),
                "atmId", request.getSelectedAtmId()
        );

        notificationServiceClient.sendRouteGenerateRequest(ChatNotificationSendRequest.builder()
                .title("ATM Yol Tarifi")
                .level("success")
                .type("qr")
                .message(request.getBankName() + " adlı ATM'ye giden yol tarifi oluşturuldu.")
                .userId(request.getUserId())
                .time(LocalDateTime.now())
                .arguments(arguments)
                .build());

        notificationServiceClient.sendNotification(NotificationMessage.builder()
                .userId(request.getUserId())
                .title("ATM Yol Tarifi")
                .message(request.getBankName() + " adlı ATM'ye giden yol tarifi oluşturuldu.")
                .type("INFO")
                .build());
        log.info("Route generation request sent for ATM ID: {}", request.getSelectedAtmId());
    }
}