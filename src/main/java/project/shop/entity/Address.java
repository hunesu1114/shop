package project.shop.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String streetName;
    private String zipcode;

}
