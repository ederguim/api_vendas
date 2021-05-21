package io.github.vendas.validation.constraintValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.vendas.validation.NotEmptyList;

@SuppressWarnings("rawtypes")
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		return list != null && !list.isEmpty();
	}
	
	@Override
	public void initialize(NotEmptyList constraintAnnotation) {

	}
}
