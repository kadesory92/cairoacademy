package com.banatech.ru.cairoacademy.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.*;

import static java.util.stream.Collectors.toList;

public abstract class BaseMapper<E, D> {
    protected ModelMapper modelMapper;

    public BaseMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }
    public abstract E convertToEntity(D dto, Object... args);

    public abstract D convertToDto(E entity, Object... args);

    public Collection<E> convertToEntity(Collection<D> dto, Object... args) {
        return dto.stream().map(d -> convertToEntity(d, args)).collect(toList());
    }

    public Collection<D> convertToDto(Collection<E> entity, Object... args) {
        return entity.stream().map(e -> convertToDto(e, args)).collect(toList());
    }

    public List<E> convertToEntityList(Collection<D> dto, Object... args) {
        return new ArrayList<>(convertToEntity(dto, args));
    }

    public List<D> convertToDtoList(Collection<E> entity, Object... args) {
        return convertToDto(entity, args).stream().toList();
    }

    public Set<E> convertToEntitySet(Collection<D> dto, Object... args) {
        return new HashSet<>(convertToEntity(dto, args));
    }

    public Set<D> convertToDtoSet(Collection<E> entity, Object... args) {
        return new HashSet<>(convertToDto(entity, args));
    }
}
