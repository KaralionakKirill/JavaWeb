package com.epam.bar.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class Cocktail extends Entity{
    private int id;
    private String name;
    private String composition;
    private Alcohol alcohol;
    private double rate;
    private User author;
    private String imgName;
    private boolean approved;

}
