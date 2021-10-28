package com.rsm.bookstoremanager.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("male"),
    FEMALE("Female");


    private String description;
}
