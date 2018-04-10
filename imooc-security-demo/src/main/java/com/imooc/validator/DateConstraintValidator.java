package com.imooc.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateConstraintValidator implements ConstraintValidator<SimpleDateConstraint, Object> {
	private SimpleDateConstraint simpleDateConstraint;
	@Override
	public void initialize(SimpleDateConstraint arg0) {
		this.simpleDateConstraint=arg0;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext arg1) {
		boolean isOk=true;
		String msg=simpleDateConstraint.message();
		SimpleDateFormat sdf=new SimpleDateFormat(simpleDateConstraint.pattern());
		boolean canBeNull = simpleDateConstraint.canBeNull();
		if(canBeNull && value==null) {
			return isOk;
		}
		if(value==null) {
			isOk=false;
			arg1.disableDefaultConstraintViolation();
			arg1.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
			return isOk;
		}
		
		try {
			Date s = sdf.parse(simpleDateConstraint.minDate());
			Date e = sdf.parse(simpleDateConstraint.maxDate());
			if(((Date)value).before(s)) {
				isOk=false;
				msg="日期不能小于"+simpleDateConstraint.minDate();
			}
			if(((Date)value).after(e)) {
				msg="日期不能大于"+simpleDateConstraint.maxDate();
				isOk=false;
			}
			arg1.disableDefaultConstraintViolation();
			arg1.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return isOk;
	}

}
