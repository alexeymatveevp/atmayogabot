package com.alexeym.atmayoga.checkvist;

import com.alexeym.atmayoga.checkvist.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import javax.annotation.Nullable;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API: https://checkvist.com/auth/api
 * Created by Alexey Matveev on 6/16/2018.
 */
public class CheckvistService {

    public static final Long AMTAYOGA_NEWS_LIST_ID = 677888L;
    public static final Long AMTAYOGA_SPONTANEOUS_LIST_ID = 677889L;

    private static final String CHECKVIST_URL = "https://checkvist.com";
    private static final String CHECKLISTS = "checklists"; // + list id

    private static final String CHECKLIST_ID_PARAM = "${checklistId}";
    private static final String TASK_ID_PARAM = "${taskId}";
    private static final String ACTION_PARAM = "${taskAction}";
    public static final String ACTION_CLOSE = "close"; // also invalidate and reopen

    private static final String GET_TASKS_FOR_CHECKLIST_URL = CHECKVIST_URL + "/" + CHECKLISTS + "/" + CHECKLIST_ID_PARAM + "/tasks.json?with_notes=true";
    private static final String CHANGE_TASK_STATUS_URL = CHECKVIST_URL + "/" + CHECKLISTS + "/" + CHECKLIST_ID_PARAM + "/tasks/" + TASK_ID_PARAM + "/" + ACTION_PARAM + ".json";

    private static final String BASIC_CREDENTIAL = Credentials.basic("david.lucky.star@gmail.com", "qnatq468");

    ObjectMapper objectMapper = new ObjectMapper();
//    OkHttpClient client = trustAllSslClient(new OkHttpClient());
    OkHttpClient client = trustAllSslClient(new OkHttpClient.Builder().authenticator((route, response) -> {
        return response.request().newBuilder().header("Authorization", BASIC_CREDENTIAL).build();
    }).build());

    public List<Task> getTasks(Long checklistId) throws Exception {
        String url = GET_TASKS_FOR_CHECKLIST_URL.replace(CHECKLIST_ID_PARAM, checklistId+"");

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        try (ResponseBody body = response.body()) {
            String responseJson = body.string();
            List<Task> tasks = objectMapper.readValue(responseJson, new TypeReference<List<Task>>() {
            });
            // resolve 2nd level tasks
            return linkSubTasks(tasks);
        }
    }

    public void closeTask(Long checklistId, Long taskId) throws Exception {
        String url = CHANGE_TASK_STATUS_URL
                .replace(CHECKLIST_ID_PARAM, checklistId+"")
                .replace(TASK_ID_PARAM, taskId+"")
                .replace(ACTION_PARAM, ACTION_CLOSE);

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", BASIC_CREDENTIAL)
                .get()
                .build();
        client.newCall(request).execute();
    }

    private List<Task> linkSubTasks(List<Task> tasks) {
        List<Task> result = null;
        if (tasks != null) {
            result = new ArrayList<>();
            Map<Long, Task> map = new HashMap<>();
            for (Task task : tasks) {
                map.put(task.getId(), task);
            }
            for (Task task : tasks) {
                List<Long> subTaskIds = task.getTaskIds();
                if (subTaskIds != null) {
                    for (Long subTaskId : subTaskIds) {
                        List<Task> subTasks = task.getTasks();
                        if (subTasks == null) {
                            subTasks = new ArrayList<>();
                            task.setTasks(subTasks);
                        }
                        subTasks.add(map.get(subTaskId));
                    }
                }
                // add only root level tasks
                if (task.getParentId() == 0L) {
                    result.add(task);
                }
            }
        }
        return result;
    }



    /*
     * This should not be used in production unless you really don't care
     * about the security. Use at your own risk.
     */
    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }

    /*
     * This is very bad practice and should NOT be used in production.
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

}
