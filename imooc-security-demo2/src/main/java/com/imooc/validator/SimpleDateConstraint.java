package com.imooc.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=DateConstraintValidator.class)
public @interface SimpleDateConstraint {
	String message() default "日期格式错误！";
	String pattern() default "yyyy-MM-dd";
	boolean canBeNull() default false; //能否为空值
	String minDate() default "1990-9-1";  //设最小日期的缺省值
	String maxDate() default "2050-9-1";  //设最大日期的 缺省值
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
