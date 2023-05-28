package com.ubi.beautyClinic.adapters.inbound.exceptionHandlers;

public class ObjectNotFoundDetails extends ErrorDetails {

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

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

        public UserAlreadyExistsDetails build() {
            UserAlreadyExistsDetails userAlreadyExistsDetails = new UserAlreadyExistsDetails();
            userAlreadyExistsDetails.title = this.title;
            userAlreadyExistsDetails.status = this.status;
            userAlreadyExistsDetails.detail = this.detail;
            userAlreadyExistsDetails.developerMessage = this.developerMessage;
            userAlreadyExistsDetails.timestamp = this.timestamp;
            return userAlreadyExistsDetails;
        }
    }
}
