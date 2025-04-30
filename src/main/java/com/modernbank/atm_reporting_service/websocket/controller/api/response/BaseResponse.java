package com.modernbank.atm_reporting_service.websocket.controller.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.modernbank.atm_reporting_service.constants.ErrorCodeConstants;
import com.modernbank.atm_reporting_service.constants.ResponseStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "Status",
        "ProcessCode",
        "ProcessMessage"
})
public class BaseResponse {

    @JsonProperty("Status")
    private String status = ResponseStatus.SUCCESS_CODE;

    @JsonProperty("ProcessCode")
    private String processCode = ErrorCodeConstants.SUCCESS;

    @JsonProperty("ProcessMessage")
    private String processMessage = ResponseStatus.PROCESS_SUCCESS;

    /*public BaseResponse(String processMessage){
        this.processCode = ResponseStatus.PROCESS_SUCCESS;
        this.processMessage = processMessage;
    }*/
}