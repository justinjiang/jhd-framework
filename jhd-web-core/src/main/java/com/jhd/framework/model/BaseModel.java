package com.jhd.framework.model;

import java.io.Serializable;

/**
 * Base class for Model objects.  This is an interface used to tag our Model
 * classes and to provide common methods to all Model.
 * @author zwh
 *
 */
public interface BaseModel extends Serializable {
	
	public abstract String toString();
    
    public abstract boolean equals(Object o);
    
    public abstract int hashCode();
}
