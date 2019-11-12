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
public class RestRequestEntity<T> {
	
	@SuppressWarnings("rawtypes")
	private Class elementClass;
	
	@SuppressWarnings("rawtypes")
	public RestRequestEntity(Class elementClass) {
        this.elementClass = elementClass;
    }
	  
	@SuppressWarnings("rawtypes")
	private Class getSelfClass() {
		return elementClass;
	}
	
	private T toRequestList(Object domainObject) {
		List<Object> list = (List<Object>) domainObject;
		List<T> newList = (List<T>) new ArrayList<>();
		try {
			for(Object obj: list) {
				RestRequestEntity<Object> responseEntity = new RestRequestEntity<>(Object.class);
				newList.add((T) responseEntity.toRequestObject(elementClass.newInstance(), obj));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) newList;
	}
	
	private T toRequestSet(Object domainObject) {
		Set<Object> list = (Set<Object>) domainObject;
		Set<T> newList = (Set<T>) new HashSet<>();
		try {
			for(Object obj: list) {
				RestRequestEntity<Object> responseEntity = new RestRequestEntity<>(Object.class);
				newList.add((T) responseEntity.toRequestObject(elementClass.newInstance(), obj));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) newList;
	}
	
	public T toRequestObject(Object domainObject) {
		if(domainObject.getClass().getName().equals(ArrayList.class.getName())) {
			return this.toRequestList(domainObject);
		} else if (domainObject.getClass().getName().equals(HashSet.class.getName())) {
			return this.toRequestSet(domainObject);
		} else {
			T responseObject = null;
			try {
				responseObject = (T) getSelfClass().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (T) this.toRequestObject(responseObject, domainObject);
		}
	}
	
	public T toRequestObject(Object responseObject, Object domainObject) {
		try {
			if(RestEntityHelper.assignableField(responseObject)) {
				return (T) domainObject;
			} else {
				for (Field field : domainObject.getClass().getDeclaredFields()) {
					try {
						 if(field.isAnnotationPresent(ExposeProperty.class)) {
							 field.setAccessible(true);
							 if(field.getType().isAssignableFrom(List.class)) {
								 RestRequestHelper.setListData(field, domainObject, responseObject);
							 } else if(field.getType().isAssignableFrom(Set.class)) {
								 RestRequestHelper.setSetData(field, domainObject, responseObject);
							 } else if(RestEntityHelper.assignableField(field)) {
								 Annotation annotation = field.getAnnotation(ExposeProperty.class);
								 ExposeProperty exposeProperty = (ExposeProperty) annotation;
								 
								 if(!exposeProperty.setMethod().equals("")) {
									 String propertyName = exposeProperty.name();
									 if(propertyName.equals("")) {
										 propertyName = field.getName();
									 }
									 Method method = domainObject.getClass().getMethod(exposeProperty.setMethod(), responseObject.getClass(), domainObject.getClass());
									 method.setAccessible(true);
									 field.setAccessible(true);
									 try {
										 method.invoke(domainObject, responseObject, domainObject);
									 } catch(Exception e) {
										 e.printStackTrace();
									 }
								 } else {
									 String propertyName = RestEntityHelper.getFieldPropertyName(field);
									 Field domainField = RestEntityHelper.getSourceField(responseObject, propertyName);
									 domainField.set(responseObject, field.get(domainObject));
								 }
							 } else {
								 Annotation annotation = field.getAnnotation(ExposeProperty.class);
								 ExposeProperty exposeProperty = (ExposeProperty) annotation;
								 
								 if(!exposeProperty.setMethod().equals("")) {
									 String propertyName = exposeProperty.name();
									 if(propertyName.equals("")) {
										 propertyName = field.getName();
									 }
									 Method method = domainObject.getClass().getMethod(exposeProperty.setMethod(), responseObject.getClass(), domainObject.getClass());
									 method.setAccessible(true);
									 field.setAccessible(true);
									 try {
										 method.invoke(domainObject, responseObject, domainObject);
									 } catch(Exception e) {
										 e.printStackTrace();
									 }
								 } else {
									 String propertyName = RestEntityHelper.getFieldPropertyName(field);
									 Field domainField = RestEntityHelper.getSourceField(responseObject, propertyName);
	
									 Constructor<?> constructor = domainField.getType().getDeclaredConstructor();
									 constructor.setAccessible(true);
									 RestRequestEntity<Object> responseEntity = new RestRequestEntity<>(Object.class);
									 domainField.set(responseObject, responseEntity.toRequestObject(constructor.newInstance(), field.get(domainObject)));
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
