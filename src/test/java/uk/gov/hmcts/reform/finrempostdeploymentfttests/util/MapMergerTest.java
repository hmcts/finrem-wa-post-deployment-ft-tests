package uk.gov.hmcts.reform.finrempostdeploymentfttests.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.UnitTestAssertionsShouldIncludeMessage",
    "PMD.UseConcurrentHashMap"})
class MapMergerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @MethodSource("emptyTargetLists")
    void givenTwoNestedTasksMapsWithEmptyTargetMapWhenMergedThenSourceAddedToTarget(
        List<Map<String, Object>> emptylist) throws IOException {

        Map<String, Object> target = new LinkedHashMap<>();
        target.put("tasks", emptylist);
        Map<String, Object> source = loadJsonAsMap("maps/task-replacements.json");

        MapMerger.merge(target, source);

        assertEquals(
            List.of(
                Map.ofEntries(
                    entry("work_type_id", "evidence"),
                    entry("name", "Process Scanned Documents"),
                    entry("task_state", "unassigned"),
                    entry(
                        "description",
                        "[Attach scanned document](/cases/case-details"
                            + "/${[CASE_REFERENCE]}/trigger/attachScannedDocs/attachScannedDocs1)"
                    ),
                    entry("task_title", "Process Scanned Documents"),
                    entry("role_category", "CTSC"),
                    entry("type", "processScannedDocuments")
                )),
            target.get("tasks")
        );
    }

    @Test
    void givenTwoNestedTasksMapsWithOneSourceMapWhenMergedThenSourceAddedToTarget() throws IOException {
        Map<String, Object> target = loadJsonAsMap("maps/task-template.json");
        Map<String, Object> source = loadJsonAsMap("maps/task-replacements.json");

        MapMerger.merge(target, source);

        assertEquals(
            List.of(
                Map.ofEntries(
                    entry("security_classification", "PUBLIC"),
                    entry("case_management_category", "FR Consented"),
                    entry("task_system", "SELF"),
                    entry("jurisdiction", "DIVORCE"),
                    entry("case_name", "Baggins vs Gollum"),
                    entry("case_type_id", "FinancialRemedyMVP2"),
                    entry("work_type_id", "evidence"),
                    entry("name", "Process Scanned Documents"),
                    entry("task_state", "unassigned"),
                    entry(
                        "description",
                        "[Attach scanned document](/cases/case-details"
                            + "/${[CASE_REFERENCE]}/trigger/attachScannedDocs/attachScannedDocs1)"
                    ),
                    entry("task_title", "Process Scanned Documents"),
                    entry("role_category", "CTSC"),
                    entry("type", "processScannedDocuments")
                )),
            target.get("tasks")
        );
    }

    @Test
    void givenTwoNestedTasksMapsWithMultipleSourceMapsWhenMergedThenSourceAddedToTarget() throws IOException {
        Map<String, Object> target = loadJsonAsMap("maps/task-template.json");
        Map<String, Object> source = loadJsonAsMap("maps/task-replacements-multiple.json");

        MapMerger.merge(target, source);

        assertEquals(
            List.of(
                Map.ofEntries(
                    entry("security_classification", "PUBLIC"),
                    entry("case_management_category", "FR Consented"),
                    entry("task_system", "SELF"),
                    entry("jurisdiction", "DIVORCE"),
                    entry("case_name", "Baggins vs Gollum"),
                    entry("case_type_id", "FinancialRemedyMVP2"),
                    entry("work_type_id", "evidence"),
                    entry("name", "Process Scanned Documents"),
                    entry("task_state", "unassigned"),
                    entry(
                        "description",
                        "[Attach scanned document](/cases/case-details"
                            + "/${[CASE_REFERENCE]}/trigger/attachScannedDocs/attachScannedDocs1)"
                    ),
                    entry("task_title", "Process Scanned Documents"),
                    entry("role_category", "CTSC"),
                    entry("type", "processScannedDocuments")
                ),
                Map.ofEntries(
                    entry("security_classification", "PUBLIC"),
                    entry("case_management_category", "FR Consented"),
                    entry("task_system", "SELF"),
                    entry("jurisdiction", "DIVORCE"),
                    entry("case_name", "Baggins vs Gollum"),
                    entry("case_type_id", "FinancialRemedyMVP2"),
                    entry("work_type_id", "applications"),
                    entry("name", "Check and Issue Application"),
                    entry("task_state", "unassigned"),
                    entry(
                        "description",
                        "[Issue Application]"
                            + "(/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/FR_issueApplication/FR_issueApplication1)"
                    ),
                    entry("task_title", "Check and Issue Application"),
                    entry("role_category", "CTSC"),
                    entry("type", "checkAndIssueApplication")
                )
            ),
            target.get("tasks")
        );
    }

    private static Stream<Arguments> emptyTargetLists() {
        return Stream.of(
            Arguments.of(List.of()),
            Arguments.of(List.of(Map.of()))
        );
    }

    private Map<String, Object> loadJsonAsMap(String resourcePath) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + resourcePath)) {
            assertNotNull(inputStream, "Test resource not found: " + resourcePath);
            return objectMapper.readValue(
                inputStream, new TypeReference<>() {
                }
            );
        }
    }
}
