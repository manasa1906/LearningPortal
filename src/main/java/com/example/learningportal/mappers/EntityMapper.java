package com.example.learningportal.mappers;

import java.util.List;

public interface EntityMapper<D, E> {
	D toDto(E e);

	E toEntity(D d);

	List<D> toDto(List<E> elist);

	List<E> toEntity(List<D> dlist);

}
