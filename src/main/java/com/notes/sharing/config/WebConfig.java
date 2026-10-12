package com.notes.sharing.config;

import com.notes.sharing.security.CurrentUserIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
// Registers my @CurrentUserId so controllers can use it.
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserIdArgumentResolver);
    }
    public WebConfig(final CurrentUserIdArgumentResolver currentUserIdArgumentResolver) {
        this.currentUserIdArgumentResolver = currentUserIdArgumentResolver;
    }
}
