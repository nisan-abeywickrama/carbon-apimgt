/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.impl.gatewayartifactsynchronizer;

import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.dao.GatewayArtifactsMgtDAO;
import org.wso2.carbon.apimgt.impl.dto.APIRuntimeArtifactDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static final GatewayArtifactsMgtDAO gatewayArtifactsMgtDAO = GatewayArtifactsMgtDAO.getInstance();

    public static void setDeployedTime(APIRuntimeArtifactDto apiRuntimeArtifactDto) throws APIManagementException {
        String deployedTime =
                gatewayArtifactsMgtDAO.retrieveAPIRevisionDeployedTime(
                        apiRuntimeArtifactDto.getLabel(), apiRuntimeArtifactDto.getRevision());
        if (deployedTime != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(deployedTime);
                apiRuntimeArtifactDto.setDeployedTimeStamp(String.valueOf(date.getTime()));
            } catch (java.text.ParseException e) {
                throw new APIManagementException("Error while parsing the deployed time:" + deployedTime, e);
            }
        }
    }
}
