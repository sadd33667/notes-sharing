package com.notes.sharing.dto;


public class UpdateSettingsRequest {
    private String theme;
    private String language;
    private Boolean twoFactorEnabled;
    private Boolean notificationsEnabled;
    public static class UpdateSettingsRequestBuilder {
        private String theme;
        private String language;
        private Boolean twoFactorEnabled;
        private Boolean notificationsEnabled;
        UpdateSettingsRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateSettingsRequest.UpdateSettingsRequestBuilder theme(final String theme) {
            this.theme = theme;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateSettingsRequest.UpdateSettingsRequestBuilder language(final String language) {
            this.language = language;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateSettingsRequest.UpdateSettingsRequestBuilder twoFactorEnabled(final Boolean twoFactorEnabled) {
            this.twoFactorEnabled = twoFactorEnabled;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateSettingsRequest.UpdateSettingsRequestBuilder notificationsEnabled(final Boolean notificationsEnabled) {
            this.notificationsEnabled = notificationsEnabled;
            return this;
        }
        public UpdateSettingsRequest build() {
            return new UpdateSettingsRequest(this.theme, this.language, this.twoFactorEnabled, this.notificationsEnabled);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateSettingsRequest.UpdateSettingsRequestBuilder(theme=" + this.theme + ", language=" + this.language + ", twoFactorEnabled=" + this.twoFactorEnabled + ", notificationsEnabled=" + this.notificationsEnabled + ")";
        }
    }
    public static UpdateSettingsRequest.UpdateSettingsRequestBuilder builder() {
        return new UpdateSettingsRequest.UpdateSettingsRequestBuilder();
    }
    public String getTheme() {
        return this.theme;
    }
    public String getLanguage() {
        return this.language;
    }
    public Boolean getTwoFactorEnabled() {
        return this.twoFactorEnabled;
    }
    public Boolean getNotificationsEnabled() {
        return this.notificationsEnabled;
    }
    public void setTheme(final String theme) {
        this.theme = theme;
    }
    public void setLanguage(final String language) {
        this.language = language;
    }
    public void setTwoFactorEnabled(final Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }
    public void setNotificationsEnabled(final Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
    public UpdateSettingsRequest() {
    }
    public UpdateSettingsRequest(final String theme, final String language, final Boolean twoFactorEnabled, final Boolean notificationsEnabled) {
        this.theme = theme;
        this.language = language;
        this.twoFactorEnabled = twoFactorEnabled;
        this.notificationsEnabled = notificationsEnabled;
    }
}
