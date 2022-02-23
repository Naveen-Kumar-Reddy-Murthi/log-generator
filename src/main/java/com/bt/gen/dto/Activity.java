
package com.bt.gen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @JsonProperty("action_group_id")
    private String actionGroupId;
    @JsonProperty("action_type_id")
    private String actionTypeId;
    @JsonProperty("browser")
    private String browser;
    @JsonProperty("client_public_ip")
    private String clientPublicIp;
    @JsonProperty("client_version")
    private String clientVersion;
    @JsonProperty("created_time")
    private Long createdTime;
    @JsonProperty("device_model")
    private String deviceModel;
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("published")
    private String published;
    @JsonProperty("users")
    private List<User> users;

}
