package br.com.squada.core.api.http.response;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequestResponseWrapper {

    private String apiVersion;
    private String transactionId;
    private Object data;

    public ApiRequestResponseWrapper(){
    	apiVersion = "1";
    }

    public ApiRequestResponseWrapper(final String apiVersion, final String transactionId) {
        this.apiVersion = apiVersion;
        this.transactionId = transactionId;
    }

    @SuppressWarnings("unchecked")
    public void addItem(final String key, final Object value) {
        if (data == null) {
            data = new HashMap<>();
        }

        if (data instanceof HashMap) {
            ((HashMap<String, Object>) data).put(key, value);
        }
    }

}
