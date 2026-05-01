package com.back;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

// Requset : 사용자의 명령 분석 담당
public class Rq {
    private final String actionName;
    private Map<String, String> params;

    public Rq(String cmd) {
        String[] cmdBits = cmd.split("\\?");
        actionName = cmdBits[0];

        String queryString = cmdBits.length == 1 ? "" : cmdBits[1].trim();

        if (queryString.isBlank()) { // actionName외 아무 파라미터도 없는 경우
            params = Map.of();
            return;
        }


        params = Arrays.stream(queryString.trim().split("&"))
                .map(paramStr -> paramStr.trim().split("=", 2))
                .filter(paramStrBits -> paramStrBits.length == 2 && !paramStrBits[0].isBlank() && !paramStrBits[1].isBlank())
                .collect(Collectors.toMap(paramStrBits -> paramStrBits[0], paramStrBits -> paramStrBits[1]));
    }
    public String getActionName() {
        return actionName;
    }

    public int getParamInt(String name, int defaultValue) {
        String value = getParam(name, "");

        if (value.equals("")) { return defaultValue; }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getParam(String name, String defaultValue) {
        return params.getOrDefault(name, defaultValue);
    }
}
