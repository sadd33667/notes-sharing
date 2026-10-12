package com.notes.sharing.dto;


public class SettingsResponse {
    private String theme;
    private String language;
    private Boolean twoFactorEnabled;
    private Boolean notificationsEnabled;
    public static class SettingsResponseBuilder {
        private String theme;
        private String language;
        private Boolean twoFactorEnabled;
        private Boolean notificationsEnabled;
        SettingsResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public SettingsResponse.SettingsResponseBuilder theme(final String theme) {
            this.theme = theme;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SettingsResponse.SettingsResponseBuilder language(final String language) {
            this.language = language;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SettingsResponse.SettingsResponseBuilder twoFactorEnabled(final Boolean twoFactorEnabled) {
            this.twoFactorEnabled = twoFactorEnabled;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SettingsResponse.SettingsResponseBuilder notificationsEnabled(final Boolean notificationsEnabled) {
            this.notificationsEnabled = notificationsEnabled;
            return this;
        }
        public SettingsResponse build() {
            return new SettingsResponse(this.theme, this.language, this.twoFactorEnabled, this.notificationsEnabled);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "SettingsResponse.SettingsResponseBuilder(theme=" + this.theme + ", language=" + this.language + ", twoFactorEnabled=" + this.twoFactorEnabled + ", notificationsEnabled=" + this.notificationsEnabled + ")";
        }
    }
    public static SettingsResponse.SettingsResponseBuilder builder() {
        return new SettingsResponse.SettingsResponseBuilder();
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
    public SettingsResponse() {
    }
    public SettingsResponse(final String theme, final String language, final Boolean twoFactorEnabled, final Boolean notificationsEnabled) {
        this.theme = theme;
        this.language = language;
        this.twoFactorEnabled = twoFactorEnabled;
        this.notificationsEnabled = notificationsEnabled;
    }
}
