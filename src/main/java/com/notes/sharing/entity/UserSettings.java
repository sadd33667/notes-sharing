package com.notes.sharing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    private Long userID;
    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private User user;
    private String theme;
    private String language;
    private Boolean twoFactorEnabled;
    private Boolean notificationsEnabled;
    private static String $default$theme() {
        return "light";
    }
    private static String $default$language() {
        return "en";
    }
    private static Boolean $default$twoFactorEnabled() {
        return false;
    }
    private static Boolean $default$notificationsEnabled() {
        return true;
    }
    public static class UserSettingsBuilder {
        private Long userID;
        private User user;
        private boolean theme$set;
        private String theme$value;
        private boolean language$set;
        private String language$value;
        private boolean twoFactorEnabled$set;
        private Boolean twoFactorEnabled$value;
        private boolean notificationsEnabled$set;
        private Boolean notificationsEnabled$value;
        UserSettingsBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder userID(final Long userID) {
            this.userID = userID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder user(final User user) {
            this.user = user;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder theme(final String theme) {
            this.theme$value = theme;
            theme$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder language(final String language) {
            this.language$value = language;
            language$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder twoFactorEnabled(final Boolean twoFactorEnabled) {
            this.twoFactorEnabled$value = twoFactorEnabled;
            twoFactorEnabled$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UserSettings.UserSettingsBuilder notificationsEnabled(final Boolean notificationsEnabled) {
            this.notificationsEnabled$value = notificationsEnabled;
            notificationsEnabled$set = true;
            return this;
        }
        public UserSettings build() {
            String theme$value = this.theme$value;
            if (!this.theme$set) theme$value = UserSettings.$default$theme();
            String language$value = this.language$value;
            if (!this.language$set) language$value = UserSettings.$default$language();
            Boolean twoFactorEnabled$value = this.twoFactorEnabled$value;
            if (!this.twoFactorEnabled$set) twoFactorEnabled$value = UserSettings.$default$twoFactorEnabled();
            Boolean notificationsEnabled$value = this.notificationsEnabled$value;
            if (!this.notificationsEnabled$set) notificationsEnabled$value = UserSettings.$default$notificationsEnabled();
            return new UserSettings(this.userID, this.user, theme$value, language$value, twoFactorEnabled$value, notificationsEnabled$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UserSettings.UserSettingsBuilder(userID=" + this.userID + ", user=" + this.user + ", theme$value=" + this.theme$value + ", language$value=" + this.language$value + ", twoFactorEnabled$value=" + this.twoFactorEnabled$value + ", notificationsEnabled$value=" + this.notificationsEnabled$value + ")";
        }
    }
    public static UserSettings.UserSettingsBuilder builder() {
        return new UserSettings.UserSettingsBuilder();
    }
    public Long getUserID() {
        return this.userID;
    }
    public User getUser() {
        return this.user;
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
    public void setUserID(final Long userID) {
        this.userID = userID;
    }
    public void setUser(final User user) {
        this.user = user;
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
    public UserSettings() {
        this.theme = UserSettings.$default$theme();
        this.language = UserSettings.$default$language();
        this.twoFactorEnabled = UserSettings.$default$twoFactorEnabled();
        this.notificationsEnabled = UserSettings.$default$notificationsEnabled();
    }
    public UserSettings(final Long userID, final User user, final String theme, final String language, final Boolean twoFactorEnabled, final Boolean notificationsEnabled) {
        this.userID = userID;
        this.user = user;
        this.theme = theme;
        this.language = language;
        this.twoFactorEnabled = twoFactorEnabled;
        this.notificationsEnabled = notificationsEnabled;
    }
}
