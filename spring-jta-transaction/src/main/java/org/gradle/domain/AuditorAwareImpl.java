package org.gradle.domain;

import org.springframework.data.domain.AuditorAware;

/**
 * Implementation for dynamically finding currently logged in user.
 * 
 * @author Anil Kamath
 * 
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    public String getCurrentAuditor() {
        return "SYSTEM";
    }

}