package com.epam.bar.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The representation of Review
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class Review extends Entity {
    private int id;
    private String feedback;
    private int rate;
    private Cocktail cocktail;
    private User author;
}
