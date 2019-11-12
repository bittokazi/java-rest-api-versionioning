package rest.api.versioning;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestRequestHelper {
	@SuppressWarnings("unchecked")
	public static <T> void setListData(Field sourceField, Object sourceObject, Object destinationObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		String propertyName = RestEntityHelper.getFieldPropertyName(sourceField);
		Field destinationField = RestEntityHelper.getSourceField(destinationObject, propertyName);

		List<Object> list = (List<Object>) sourceField.get(sourceObject);
		List<T> newList = (List<T>) new ArrayList<>();
		if(list!=null) {
			for(Object obj: list) {
				RestRequestEntity<Object> responseEntity = new RestRequestEntity<>(Object.class);
				newList.add((T)responseEntity.toRequestObject(RestEntityHelper.getCollectionClass(destinationField).newInstance(), obj));
			}
			destinationField.set(destinationObject, newList);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void setSetData(Field sourceField, Object sourceObject, Object destinationObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		String propertyName = RestEntityHelper.getFieldPropertyName(sourceField);
		Field destinationField = RestEntityHelper.getSourceField(destinationObject, propertyName);

		Set<Object> list = (Set<Object>) sourceField.get(sourceObject);
		Set<T> newList = (Set<T>) new HashSet<>();
		if(list!=null) {
			for(Object obj: list) {
				RestRequestEntity<Object> responseEntity = new RestRequestEntity<>(Object.class);
				newList.add((T)responseEntity.toRequestObject(RestEntityHelper.getCollectionClass(destinationField).newInstance(), obj));
			}
			destinationField.set(destinationObject, newList);
		}
	}
}
