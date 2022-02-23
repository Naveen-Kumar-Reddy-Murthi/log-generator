
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
public class Actor {

    @JsonProperty("disabled")
    private Boolean disabled;
    @JsonProperty("display_id")
    private String displayId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("is_admin")
    private Boolean isAdmin;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
    @JsonProperty("name")
    private String name;
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("org_id")
    private String orgId;
    @JsonProperty("phone_umber")
    private String phoneUmber;
    @JsonProperty("roles")
    private List<Role> roles;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_type")
    private String userType;


}
