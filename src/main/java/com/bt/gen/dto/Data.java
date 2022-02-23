package com.bt.gen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonPropertyOrder(value = {"actor","activities"})
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @JsonProperty("activities")
    private List<Activity> activities;
    @JsonProperty("actor")
    private Actor actor;

}
