package com.amazon.proserve.account.common.featuretoggle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Variant {
    private String id;
    private String flagKey;
    private String key;
    private String name;
    private String description;
    private Calendar createdAt;
    private Calendar updatedAt;
}
