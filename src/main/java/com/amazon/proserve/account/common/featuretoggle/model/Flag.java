package com.amazon.proserve.account.common.featuretoggle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//{"key":"presets-msa-switch","name":"presets-msa-switch","description":"A switch toggle between MSA and Monolithic for Presets",
// "enabled":false,"createdAt":"2021-07-12T02:39:06.704041600Z","updatedAt":"2021-07-12T02:39:06.704041600Z","variants":[]}
@Getter
@Setter
public class Flag {
    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("variants")
    private List<Variant> variants = new ArrayList<>();
}
