package com.ubi.beautyClinic.adapters.inbound.exceptionHandlers;

import java.util.List;

public class ValidationErrorDetails extends ErrorDetails {

    private List<Field> fields;

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;
        private List<Field> fields;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.title = this.title;
            validationErrorDetails.status = this.status;
            validationErrorDetails.detail = this.detail;
            validationErrorDetails.developerMessage = this.developerMessage;
            validationErrorDetails.timestamp = this.timestamp;
            validationErrorDetails.fields = this.fields;
            return validationErrorDetails;
        }
    }

    public List<Field> getFields() {
        return fields;
    }
}
