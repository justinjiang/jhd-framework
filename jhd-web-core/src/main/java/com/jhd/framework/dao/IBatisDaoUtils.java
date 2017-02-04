package com.jhd.framework.dao;

import com.jbs.framework.annotation.Entity;
import com.jbs.framework.exception.JbsException;
import com.jbs.framework.model.BaseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;


public class IBatisDaoUtils {

    protected static final Log log = LogFactory.getLog(IBatisDaoUtils.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private IBatisDaoUtils() {
    }

    /**
     * Get primary key field name from object. Looks for "id", "Id" and "version".
     *
     * @param o the object to examine
     * @return the fieldName
     */
    protected static String getPrimaryKeyFieldName(BaseModel o) {
        Entity entity = o.getClass().getAnnotation(Entity.class);

        if (entity == null) {
            return null;
        }
        return "".equals(entity.id()) ? null : entity.id();
    }


    /**
     * Get the value of the primary key using reflection.
     *
     * @param o the object to examine
     * @return the value as an Object
     */
    public static Object getPrimaryKeyValue(BaseModel o) {
        String fieldName = getPrimaryKeyFieldName(o);
        if (fieldName == null) {
            return null;
        }
        String getterMethod = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        try {
            Method getMethod = o.getClass().getMethod(getterMethod, (Class[]) null);
            return getMethod.invoke(o, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not invoke method '" + getterMethod + "' on " + ClassUtils.getShortName(o.getClass()));
            throw new JbsException("D0000004");
        }
    }

    /**
     * @param queryName
     * @return
     */
    protected static String getCountQuery(String queryName) {
        return "count" + Character.toUpperCase(queryName.charAt(0)) + queryName.substring(1);
    }


    /**
     * get total count
     *
     * @param queryName
     * @return String
     */
    public static String getCountQueryPublic(String queryName) {
        return "count" + Character.toUpperCase(queryName.charAt(0)) + queryName.substring(1);
    }

    /**
     * Prepare object for save or update.
     *
     * @param o the object to examine
     */
    protected static void prepareObjectForSaveOrUpdate(Object o) {
        try {
            Entity entity = o.getClass().getAnnotation(Entity.class);
            if (entity == null || "".equals(entity.version())) {
                return;
            }
            Field fld = o.getClass().getDeclaredField(entity.version());
            handleObjectVersion(o, fld);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not prepare '" + ClassUtils.getShortName(o.getClass()) + "' for insert/update");
            throw new JbsException("D0000005");
        }
    }

    /**
     * handler object for save or update by looking for the @Version annotation field and incrementing it if it exists.
     *
     * @param o the object to examine
     */
    private static void handleObjectVersion(Object o, Field version) {
        try {
            String fieldName = version.getName();
            Method setMethod = o.getClass().getMethod("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), Integer.class);
            Object value = o.getClass().getMethod("get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1),
                    (Class[]) null).invoke(o, (Object[]) null);
            if (value == null) {
                setMethod.invoke(o, 1);
            } else {
                setMethod.invoke(o, (Integer) value + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could handle Object version field '" + ClassUtils.getShortName(o.getClass()) + " '");
            throw new JbsException("D0000006");
        }
    }

    /**
     * Sets the primary key's value
     *
     * @param o     the object to examine
     * @param value the value of the new primary key
     */
    @SuppressWarnings("unchecked")
    protected static void setPrimaryKey(BaseModel o, Object value) {
        String fieldName = getPrimaryKeyFieldName(o);
        try {
            Entity entity = o.getClass().getAnnotation(Entity.class);
            Class clazz = o.getClass().getDeclaredField(entity.id()).getType();

            String setMethodName = "set" + Character.toUpperCase(fieldName.charAt(0))
                    + fieldName.substring(1);

            Method setMethod = o.getClass().getMethod(setMethodName, clazz);
            if (value != null) {
                setMethod.invoke(o, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(MessageFormat.format("Could not set '{0}.{1}' with value {2}",
                    ClassUtils.getShortName(o.getClass()), fieldName, value));
            throw new JbsException("D0000003");
        }
    }

    /**
     * @param className the name of the class - returns "get" + className + "List"
     * @return Returns the select query name.
     */
    public static String getAllObjectQuery(String className) {
        return "find" + className + "List";
    }

    /**
     * @param className the name of the class - returns "get" + className
     * @return Returns the find query name.
     */
    public static String getObjectQuery(String className) {
        return "find" + className + "ById";
    }

    /**
     * @param className the name of the class - returns "add" + className
     * @return Returns the insert query name.
     */
    public static String getInsertQuery(String className) {
        return "add" + className;
    }

    public static String getBatchInsertQuery(String className) {
        return "batch" + className;
    }

    /**
     * @param className the name of the class - returns "update" + className
     * @return Returns the update query name.
     */
    public static String getUpdateQuery(String className) {
        return "update" + className;
    }

    /**
     * @param className the name of the class - returns "update" + className + "WithoutNull"
     * @return Returns the updateWithoutNull query name.
     */
    public static String getUpdateSelectiveQuery(String className) {
        return "update" + className + "Selective";
    }

    /**
     * @param className the name of the class - returns "remove" + className
     * @return Returns the delete query name.
     */
    public static String getDeleteQuery(String className) {
        return "delete" + className;
    }

    /**
     * @param className the name of the class - returns "get" + className + "WithPg";
     * @return Returns the getListWithPg query name.
     */
    public static String getListWithPg(String className) {
        return "find" + className + "List";
    }

    //分页查询时计算总条数
    public static String getCountWithPg(String className) {
        return "count" + className;
    }

    public static String getCustomList(String className) {
        return "findCustom" + className + "List";
    }
}
