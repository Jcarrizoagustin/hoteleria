package com.example.hoteleria.exceptions;

public class EntityNotFoundException extends NotFoundException{
    public EntityNotFoundException(String detail){
        super(detail);
    }
}
