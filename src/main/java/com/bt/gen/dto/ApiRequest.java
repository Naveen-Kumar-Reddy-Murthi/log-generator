
package com.bt.gen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {
    @JsonProperty("action_types")
    private ActionTypes actionTypes;
    @JsonProperty("starts")
    private String starts;

}
