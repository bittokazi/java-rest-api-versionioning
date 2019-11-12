package rest.api.versioning;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestEntityHelper {
	public static String getFieldPropertyName(Field field) {
		Annotation annotation = field.getAnnotation(ExposeProperty.class);
		ExposeProperty exposeProperty = (ExposeProperty) annotation;
		 
		String propertyName = exposeProperty.name();
		if(propertyName.equals("")) {
			propertyName = field.getName();
		}
		return propertyName;
	}
	
	public static Field getSourceField(Object sourceObject, String propertyName) throws NoSuchFieldException, SecurityException {
		Field sourceField = sourceObject.getClass().getDeclaredField(propertyName);
		sourceField.setAccessible(true);
		return sourceField;
	}

	public static boolean assignableField(Object sourceField) {
		if(sourceField.getClass().isAssignableFrom(int.class) ||
				sourceField.getClass().isAssignableFrom(long.class) ||
				sourceField.getClass().isAssignableFrom(double.class) ||
				sourceField.getClass().isAssignableFrom(float.class) ||
				sourceField.getClass().isAssignableFrom(boolean.class) ||
				sourceField.getClass().isAssignableFrom(Long.class) ||
				sourceField.getClass().isAssignableFrom(String.class) ||
				sourceField.getClass().isAssignableFrom(Integer.class) ||
				sourceField.getClass().isAssignableFrom(Double.class) ||
				sourceField.getClass().isAssignableFrom(Float.class) ||
				sourceField.getClass().isAssignableFrom(Boolean.class)) {
			return true;
		}
		return false;
	}
	
	public static boolean assignableField(Field sourceField) {
		if(sourceField.getType().isAssignableFrom(int.class) ||
				sourceField.getType().isAssignableFrom(long.class) ||
				sourceField.getType().isAssignableFrom(double.class) ||
				sourceField.getType().isAssignableFrom(float.class) ||
				sourceField.getType().isAssignableFrom(boolean.class) ||
				sourceField.getType().isAssignableFrom(Long.class) ||
				sourceField.getType().isAssignableFrom(String.class) ||
				sourceField.getType().isAssignableFrom(Integer.class) ||
				sourceField.getType().isAssignableFrom(Double.class) ||
				sourceField.getType().isAssignableFrom(Float.class) ||
				sourceField.getType().isAssignableFrom(Boolean.class)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getCollectionClass(Field listField) {
		ParameterizedType listType = (ParameterizedType) listField.getGenericType();
		return (Class) listType.getActualTypeArguments()[0];
	}
}
