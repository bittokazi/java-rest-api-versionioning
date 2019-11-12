package rest.api.versioning;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestResponseHelper {
	@SuppressWarnings("unchecked")
	public static <T> void setListData(Field field, Object sourceObject, Object destinationObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		String propertyName = RestEntityHelper.getFieldPropertyName(field);
		Field domainField = RestEntityHelper.getSourceField(sourceObject, propertyName);
		 
		List<Object> list = (List<Object>) domainField.get(sourceObject);
		List<T> newList = (List<T>) new ArrayList<>();
		if(list!=null) {
			for(Object obj: list) {
				RestResponseEntity<Object> responseEntity = new RestResponseEntity<>(Object.class);
				newList.add((T)responseEntity.toResponseObject(RestEntityHelper.getCollectionClass(field).newInstance(), obj));
			}
			field.set(destinationObject, newList);
		}
	}
	@SuppressWarnings("unchecked")
	public static <T> void setSetData(Field field, Object sourceObject, Object destinationObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		String propertyName = RestEntityHelper.getFieldPropertyName(field);
		Field domainField = RestEntityHelper.getSourceField(sourceObject, propertyName);
		 
		Set<Object> list = (Set<Object>) domainField.get(sourceObject);
		Set<T> newList = (Set<T>) new HashSet<>();
		if(list!=null) {
			for(Object obj: list) {
				RestResponseEntity<Object> responseEntity = new RestResponseEntity<>(Object.class);
				newList.add((T) responseEntity.toResponseObject(RestEntityHelper.getCollectionClass(field).newInstance(), obj));
			}
			field.set(destinationObject, newList);
		}
	}
}
