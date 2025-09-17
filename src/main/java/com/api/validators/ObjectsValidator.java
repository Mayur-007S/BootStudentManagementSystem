package com.api.validators;


import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.api.exceptions.ObjectNotValideException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class ObjectsValidator<T> {
	// Create a ValidatorFactory and Validator instance
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	// Create a Validator instance
	private final Validator validator = factory.getValidator();
	
	
	
	public void validate(T objectToValidate) 
	{
		// Validate the object and collect constraint violations
		Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
		if (!violations.isEmpty()) {
		
			var errorMessage = violations
					.stream()
					.map(ConstraintViolation::getMessage) 
					.collect(Collectors.toSet());
			throw new ObjectNotValideException(errorMessage);
		}

	}

}
