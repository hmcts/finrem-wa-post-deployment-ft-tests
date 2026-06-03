package uk.gov.hmcts.reform.finrempostdeploymentfttests.services;

import io.restassured.http.Headers;
import uk.gov.hmcts.reform.finrempostdeploymentfttests.domain.entities.idam.CredentialRequest;
import uk.gov.hmcts.reform.finrempostdeploymentfttests.domain.entities.idam.UserInfo;

import java.io.IOException;

public interface AuthorizationHeaders {
    Headers getWaSystemUserAuthorization();

    Headers getFinremSystemUserAuthorization();

    Headers getWaUserAuthorization(CredentialRequest request) throws IOException;

    UserInfo getUserInfo(String userToken);

    void cleanupTestUsers();
}
