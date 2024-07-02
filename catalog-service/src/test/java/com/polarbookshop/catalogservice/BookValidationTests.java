package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;


class BookValidationTests {
 private static Validator validator;

 @BeforeAll
 static void setUp() {
  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  validator = factory.getValidator();
 }

 @Test
 void whenAllFieldsCorrectThenValidationSucceeds() {
//  var                            book = new Book("1234567890", "Title", "Author", 9.90);
  var                            book = Book.of("1234567890", "Title", "Author", 9.90);
  Set<ConstraintViolation<Book>> violations = validator.validate(book);

  Assertions.assertThat(violations).isEmpty();
 }

 @Test
 void whenIsbnDefinedButIncorrectThenValidationFails() {
//  var                            book = new Book("a234567890", "Title", "Author", 9.90);
  var                            book = Book.of("a234567890", "Title", "Author", 9.90);
  Set<ConstraintViolation<Book>> violations = validator.validate(book);

  Assertions.assertThat(violations).hasSize(1);
  Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid.");
 }
}
