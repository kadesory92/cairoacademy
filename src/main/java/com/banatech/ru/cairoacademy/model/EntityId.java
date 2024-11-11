package com.banatech.ru.cairoacademy.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public class EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
}
