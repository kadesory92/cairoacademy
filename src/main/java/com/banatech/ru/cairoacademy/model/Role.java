package com.banatech.ru.cairoacademy.model;

import com.banatech.ru.cairoacademy.model.enums.RoleName;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Role extends EntityId {
    private RoleName name;
}
