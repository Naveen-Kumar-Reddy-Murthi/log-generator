package com.bt.gen.items;

import com.bt.gen.dto.Activity;
import com.bt.gen.dto.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RestApiItemWriter implements ItemWriter<ApiResponse> {
    private static final String HEADER =  "deviceServiceName|appID|RefName|device_HostName|eventTime|eventAction|sourceUserName|WorkstationAddress|WorkstationName";
    private static final String PIPE = "|";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String LOG_FILE_DATE_FMT = "ddMMyyyhhmmss";

    @Value("${app.audit.file.location}")
    private String logFilePath;

    @Override
    public void write(List<? extends ApiResponse> apiResponseList) throws Exception {
        if(null != apiResponseList && apiResponseList.get(0) != null) {
            ApiResponse apiResponse = apiResponseList.get(0);
            String logFileName = "AppName-siem-" + DateTimeFormatter.ofPattern(LOG_FILE_DATE_FMT).format(LocalDateTime.now()) + ".log";
            File logFile = new File(logFilePath + logFileName);

            try (FileWriter logFileWriter = new FileWriter(logFile)) {
                logFileWriter.write(HEADER + NEW_LINE);
                String userId = apiResponse.getData().getActor().getUserId();

                if (null != apiResponse && null != apiResponse.getData() && null != apiResponse.getData().getActivities()) {

                    List<Activity> activityList = apiResponse.getData().getActivities();

                    for (Activity activity : activityList) {
                        StringJoiner logs = new StringJoiner(PIPE, "", NEW_LINE);
                        logs.add("CCT");
                        logs.add("PAR12345");
                        logs.add("SIEM");
                        logs.add(activity.getClientPublicIp());
                        logs.add(activity.getCreatedTime().toString());
                        logs.add(activity.getActionGroupId());
                        logs.add(userId);
                        logs.add(activity.getClientPublicIp());
                        logs.add(activity.getClientPublicIp());
                        logFileWriter.write(logs.toString());
                    }
                }
            }
        } else{
            log.warn("Api response is null. Logs were not written");
        }
    }
}
