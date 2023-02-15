package co.kr.category.http;

import org.springframework.lang.Nullable;

public enum HttpStatus {
    NO_ERROR(200, "SUCCESS"),
    BAD_REQUEST(400, "Bad Request"),
    ERROR(404,"Not Found");

    private final int value;
    private final String reasonPhraseCode;
    HttpStatus(int value, String reasonPhraseCode) {
        this.value = value;
        this.reasonPhraseCode = reasonPhraseCode;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhraseCode() {
        return this.reasonPhraseCode;
    }

    @Nullable
    public static HttpStatus resolve(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

}