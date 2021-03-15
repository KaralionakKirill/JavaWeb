package com.epam.bar.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class Cocktail extends Entity{
    private Long id;
    private String name;
    private String composition;
    private Alcohol alcohol;
    private String author;
    private String imgName;
    private boolean approved;

    public enum Alcohol {
        VODKA(1),
        WHISKEY(2),
        TEQUILA(3),
        GIN(4),
        RUM(5),
        CHAMPAGNE(6);

        private final int id;

        Alcohol(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
