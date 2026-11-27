package uk.gov.hmcts.reform.finrempostdeploymentfttests.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class MapMerger {

    private MapMerger() {
        // noop
    }

    public static void merge(
        Map<String, Object> targetMap,
        Map<String, Object> sourceMap
    ) {
        for (Map.Entry<String, Object> difference : sourceMap.entrySet()) {

            String key = difference.getKey();
            Object targetValue = targetMap.get(key);
            Object sourceValue = difference.getValue();

            if ((targetValue instanceof Map) && (sourceValue instanceof Map)) {
                merge(
                    (Map<String, Object>) targetValue,
                    (Map<String, Object>) sourceValue
                );
            } else if (targetValue instanceof List<?> targetList && sourceValue instanceof List<?> sourceList) {
                Map<String, Object> targetListMap = targetList.isEmpty()
                    ? Map.of()
                    : (Map<String, Object>) targetList.getFirst();

                List<Map<String, Object>> mergedList = new ArrayList<>(sourceList.size());
                for (Object sourceListMap : sourceList) {
                    Map<String, Object> mergedMap = new LinkedHashMap<>(targetListMap);
                    merge(mergedMap, (Map<String, Object>) sourceListMap);
                    mergedList.add(mergedMap);
                }
                targetMap.put(key, mergedList);
            } else {
                targetMap.put(key, sourceValue);
            }
        }
    }
}
