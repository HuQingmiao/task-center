package com.github.walker.common.utils;


/**
 * Common util class.
 *
 * @author HuQingmiao
 */
public class MappingUtil {

    /**
     * 根据驼峰规则将表名转为VO类名
     *
     * @param tableName
     * @return
     */
    public static String getEntityName(String tableName) {

        StringBuffer buff = new StringBuffer(tableName.toLowerCase());

        // the first character of class name is upper case
        buff.replace(0, 1, String.valueOf(Character.toUpperCase(tableName.charAt(0))));

        // delete character '_', and convert the next character to uppercase
        for (int i = 1, length = buff.length(); i < length; ) {

            char lastCh = buff.charAt(i - 1);// the last character
            char ch = buff.charAt(i); // the current character

            // if this character is a letter, and the last character is '_'
            if (Character.isLetter(ch) && lastCh == '_') {
                buff.replace(i - 1, i, String.valueOf(Character.toUpperCase(ch)));

                buff.deleteCharAt(i);
                length--;
            } else {
                i++;
            }
        }

        return buff.toString();
    }

    /**
     * 根据驼峰规则将列名转为VO类的属性名
     *
     * @param columnName
     * @return
     */
    public static String getFieldName(String columnName) {

        StringBuffer buff = new StringBuffer(columnName.toLowerCase());

        // delete character '_', and convert the next character to uppercase
        for (int i = 1, length = buff.length(); i < length; ) {

            char lastCh = buff.charAt(i - 1);// the last character
            char ch = buff.charAt(i); // the current character

            // if this character is a letter, and the last character is '_'
            if (Character.isLetter(ch) && lastCh == '_') {
                buff.replace(i - 1, i, String.valueOf(Character.toUpperCase(ch)));

                buff.deleteCharAt(i);
                length--;
            } else {
                i++;
            }
        }

        return buff.toString();
    }

    /**
     * 根据驼峰规则获取VO类指定的表名
     *
     * @param entityClassName
     * @return
     */
    public static String getTableName(String entityClassName) {

        int index = entityClassName.lastIndexOf('.');
        if (index >= 0) {
            entityClassName = entityClassName.substring(index + 1);
        }

        StringBuffer buff = new StringBuffer(entityClassName);

        for (int i = 1, length = buff.length(); i < length; i++) {
            // insert character '_' before uppercase
            if (Character.isUpperCase(buff.charAt(i))) {
                buff.insert(i++, '_');
                length++;
            }
        }

        return buff.toString().toUpperCase();
    }


    /**
     * 根据驼峰规则获取指定VO属性名所对应的列名
     *
     * @param fieldName
     * @return
     */
    public static String getColumnName(String fieldName) {

        StringBuffer buff = new StringBuffer(fieldName);

        for (int i = 1, length = buff.length(); i < length; i++) {
            // insert character '_' before uppercase or digit
            if (Character.isUpperCase(buff.charAt(i))) {
                buff.insert(i++, '_');
                length++;
            }
        }

        return buff.toString().toUpperCase();
    }
}