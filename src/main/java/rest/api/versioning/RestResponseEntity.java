package rest.api.versioning;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class RestResponseEntity<T> {
	
	@SuppressWarnings("rawtypes")
	private Class elementClass;
	
	@SuppressWarnings("rawtypes")
	public RestResponseEntity(Class elementClass) {
        this.elementClass = elementClass;
    }
	  
	@SuppressWarnings("rawtypes")
	private Class getSelfClass() {
		return elementClass;
	}
	
	private T toResponseList(Object domainObject) {
		List<Object> list = (List<Object>) domainObject;
		List<T> newList = (List<T>) new ArrayList<>();
		try {
			for(Object obj: list) {
				RestResponseEntity<Object> responseEntity = new RestResponseEntity<>(Object.class);
				newList.add((T) responseEntity.toResponseObject(elementClass.newInstance(), obj));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) newList;
	}
	
	private T toResponseSet(Object domainObject) {
		Set<Object> list = (Set<Object>) domainObject;
		Set<T> newList = (Set<T>) new HashSet<>();
		try {
			for(Object obj: list) {
				RestResponseEntity<Object> responseEntity = new RestResponseEntity<>(Object.class);
				newList.add((T) responseEntity.toResponseObject(elementClass.newInstance(), obj));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) newList;
	}
	
	public T toResponseObject(Object domainObject) {
		if(domainObject.getClass().getName().equals(ArrayList.class.getName())) {
			return this.toResponseList(domainObject);
		} else if (domainObject.getClass().getName().equals(HashSet.class.getName())) {
			return this.toResponseSet(domainObject);
		} else {
			T responseObject = null;
			try {
				responseObject = (T) getSelfClass().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (T) this.toResponseObject(responseObject, domainObject);
		}
	}
	
	public T toResponseObject(Object responseObject, Object domainObject) {
		try {
			if(RestEntityHelper.assignableField(responseObject)) {
				return (T) domainObject;
			} else {
				for (Field field : responseObject.getClass().getDeclaredFields()) {
					 try {
						 if(field.isAnnotationPresent(ExposeProperty.class)) {
							 field.setAccessible(true);
							 
							 if(field.getType().isAssignableFrom(List.class)) {
								 RestResponseHelper.setListData(field, domainObject, responseObject);
							 } else if(field.getType().isAssignableFrom(Set.class)) {
								 RestResponseHelper.setSetData(field, domainObject, responseObject);
							 } else if(RestEntityHelper.assignableField(field)) {
								 Annotation annotation = field.getAnnotation(ExposeProperty.class);
								 ExposeProperty exposeProperty = (ExposeProperty) annotation;
								 if(!exposeProperty.getMethod().equals("")) {
									 Method method = responseObject.getClass().getMethod(exposeProperty.getMethod(), domainObject.getClass());
									 method.setAccessible(true);
									 field.set(responseObject, method.invoke(responseObject, domainObject));
								 } else {
									 String propertyName = RestEntityHelper.getFieldPropertyName(field);
									 Field domainField = RestEntityHelper.getSourceField(domainObject, propertyName);
									 field.set(responseObject, domainField.get(domainObject)); 
								 }
							 } else {
								 Annotation annotation = field.getAnnotation(ExposeProperty.class);
								 ExposeProperty exposeProperty = (ExposeProperty) annotation;
								 
								 if(!exposeProperty.getMethod().equals("")) {
									 Method method = responseObject.getClass().getMethod(exposeProperty.getMethod(), domainObject.getClass());
									 method.setAccessible(true);
									 field.set(responseObject, method.invoke(responseObject, domainObject));
								 } else {
									 String propertyName = RestEntityHelper.getFieldPropertyName(field);
									 Field domainField = RestEntityHelper.getSourceField(domainObject, propertyName);
									 
									 Constructor<?> constructor = field.getType().getDeclaredConstructor();
									 constructor.setAccessible(true);
									 RestResponseEntity<Object> responseEntity = new RestResponseEntity<>(Object.class);
									 field.set(responseObject, responseEntity.toResponseObject(constructor.newInstance(), domainField.get(domainObject)));
	
								 }
							 }
							 
						 }
					 } catch(Exception e) {
						 e.printStackTrace();
					 }
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) responseObject;
	}
	
	
}
