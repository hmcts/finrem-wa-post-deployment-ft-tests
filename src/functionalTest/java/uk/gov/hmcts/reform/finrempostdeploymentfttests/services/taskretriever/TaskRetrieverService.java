package uk.gov.hmcts.reform.finrempostdeploymentfttests.services.taskretriever;

import io.restassured.http.Headers;
import uk.gov.hmcts.reform.finrempostdeploymentfttests.domain.TestScenario;

import java.util.Map;

public interface TaskRetrieverService {

    void retrieveTask(Map<String, Object> clauseValues,
                      TestScenario scenario,
                      String caseId,
                      Headers authorizationHeaders);
}
