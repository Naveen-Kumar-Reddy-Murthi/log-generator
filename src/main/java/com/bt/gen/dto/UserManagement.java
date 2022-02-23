
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
public class UserManagement {

    @JsonProperty("all_except_presence_updated")
    private Boolean allExceptPresenceUpdated;

}
