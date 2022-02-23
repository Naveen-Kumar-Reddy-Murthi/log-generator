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
public class Role {

    @JsonProperty("role_id")
    private Long roleId;
    @JsonProperty("role_name")
    private String roleName;
    @JsonProperty("role_type")
    private String roleType;

}
