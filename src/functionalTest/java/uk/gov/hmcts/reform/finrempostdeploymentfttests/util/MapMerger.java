package uk.gov.hmcts.reform.finrempostdeploymentfttests.util;

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
                merge(
                    (Map<String, Object>) targetList.getFirst(),
                    (Map<String, Object>) sourceList.getFirst()
                );
            } else {
                targetMap.put(key, sourceValue);
            }
        }
    }
}
