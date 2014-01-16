package org.gradle;

import java.io.Serializable;

public interface CurrentTenantResolver<T extends Serializable> {
    T getCurrentTenantId();
}
