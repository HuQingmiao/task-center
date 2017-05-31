package com.github.walker.taskcenter.common.utils;


import com.github.walker.taskcenter.common.BasicForm;
import com.github.walker.taskcenter.common.BasicVo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;


/**
 * 提供Form与Vo之间的属性拷贝, 支持同名但不同类型的属性的复制。
 * <p/>
 * 目前，Spring的BeanUtils.copyProperties()以及其它第三方包都只支持同名且同类型的属性值的复制。
 *
 * @author HuQingmiao
 */
public class PropertyUtil {

    /**
     * form->entity, 把form中的属性值复制到vo的同名属性中. 注意, 该方法对文件类型的属性忽略.
     *
     * @param form
     * @param vo
     */
    public static void copyProperties(BasicForm form, BasicVo vo)
            throws Exception {
        try {
            //po的属性及类型
            HashMap<String, Class<?>> voFieldsMap = vo.fieldNameTypeMap();

            Field[] formFields = form.getClass().getDeclaredFields();

            StringBuffer methodName = new StringBuffer();
            Method gettingMethod = null;    //form中的取值方法
            for (int i = 0; i < formFields.length; i++) {
                String formFieldName = formFields[i].getName();
                String formFieldType = formFields[i].getType().getName();

                // vo没有此同名属性
                if (!voFieldsMap.containsKey(formFieldName)) {
                    continue;
                }

                String voFieldType = voFieldsMap.get(formFieldName).getName();

                methodName.append("get");
                if (formFieldName.length() > 1 && Character.isUpperCase(formFieldName.charAt(1))) {
                    methodName.append(formFieldName);
                } else {
                    methodName.append(formFieldName.substring(0, 1).toUpperCase());
                    methodName.append(formFieldName.substring(1));
                }

                //form的取值方法
                gettingMethod = form.getClass().getMethod(
                        methodName.toString(), new Class[]{});
                methodName.delete(0, methodName.length());

                //取form的属性值
                Object fValue = gettingMethod.invoke(form, new Object[]{});

                if (fValue == null) {
                    vo.set(formFieldName, null);
                    continue;
                }

                //如果属性类型相同
                if (formFieldType.equals(voFieldType)) {
                    if ("java.lang.String".equals(formFieldType)) {
                        fValue = ((String) fValue).trim();
                    }
                    vo.set(formFieldName, fValue);
                    continue;
                }

                if (fValue instanceof String) {
                    String v = ((String) fValue).trim();

                    if ("java.lang.String".equals(voFieldType)) {
                        vo.set(formFieldName, v);

                    } else if ("java.lang.Long".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Long(v));
                        }
                    } else if ("java.lang.Integer".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Integer(v));
                        }
                    } else if ("java.lang.Double".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Double(v));
                        }
                    } else if ("java.lang.Float".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Float(v));
                        }
                    } else if ("java.util.Date".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            String format = DateTimeUtil.PRETTY_DT_FORMAT.substring(0, v.length());
                            Date timestamp = DateTimeUtil.parse(v, format);
                            vo.set(formFieldName, timestamp);
                        }
                    } else if ("java.sql.Timestamp".equals(voFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            String format = DateTimeUtil.PRETTY_DT_FORMAT.substring(0, v.length());
                            Date timestamp = DateTimeUtil.parse(v, format);
                            vo.set(formFieldName, timestamp);
                        }
                    }
                }//end if (fValue instanceof String

            }//end for

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * vo->form, 把po中的属性值复制到form的同名属性中. 注意, 该方法对文件类型的属性忽略.
     *
     * @param form
     * @param vo
     */
    public static void copyProperties(BasicVo vo, BasicForm form)
            throws Exception {
        try {
            //存放form的属性及类型
            HashMap formFieldsMap = new HashMap(10);

            //将form属性及其类型存入map(fieldName,fieldType)
            Field[] fields = form.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                formFieldsMap.put(fields[i].getName(), fields[i].getType());
            }

            Field[] voFields = vo.getClass().getDeclaredFields();

            StringBuffer methodName = new StringBuffer();
            Method settingMethod = null;//form中的设值方法

            for (int i = 0; i < voFields.length; i++) {
                String voFieldName = voFields[i].getName();
                String voFieldType = voFields[i].getType().getName();

                //如果vo没有此同名属性
                if (!formFieldsMap.containsKey(voFieldName)) {
                    continue;
                }

                //取form的属性类型
                Class cls = (Class) formFieldsMap.get(voFieldName);
                String formFieldType = cls.getName();

                methodName.append("set");
                if (voFieldName.length() > 1 && Character.isUpperCase(voFieldName.charAt(1))) {
                    methodName.append(voFieldName);
                } else {
                    methodName.append(voFieldName.substring(0, 1).toUpperCase());
                    methodName.append(voFieldName.substring(1));
                }

                //获取form中设值方法
                settingMethod = form.getClass().getMethod(
                        methodName.toString(), new Class[]{cls});
                methodName.delete(0, methodName.length());

                //取po的属性值
                Object eValue = vo.get(voFieldName);

                if (eValue == null) {
                    settingMethod.invoke(form, new Object[]{null});
                    continue;
                }

                //如果属性类型相同
                if (voFieldType.equals(formFieldType)) {
                    if ("java.lang.String".equals(formFieldType)) {
                        eValue = ((String) eValue).trim();
                    }
                    settingMethod.invoke(form, new Object[]{eValue});
                    continue;
                }

                if ("java.lang.String".equals(formFieldType)) {
                    if (eValue instanceof String
                            || eValue instanceof Number) {
                        String v = eValue.toString();
                        settingMethod.invoke(form, new Object[]{v});

                    } else if (eValue instanceof Date) {
                        String vs = DateTimeUtil.format((Date) eValue, DateTimeUtil.PRETTY_DT_FORMAT);
                        settingMethod.invoke(form, new Object[]{vs});
                    }
                }

            }//end for

            formFieldsMap.clear();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {

//        
//                Role entity = new Role();
//                RoleMenuForm form2 = new RoleMenuForm();
//        
//                try {
//                    RoleMenuForm form = new RoleMenuForm();
//                    form.setId("334");
//                    form.setName("asd中");
//                    form.setTime("2006-07-09");
//        
//                    copyProperties(form, entity);
//        
//                    System.out.println("ID" + entity.getId() + "ID");
//                    System.out.println("NAME" + entity.getName() + "NAME");
//                    System.out.println("TIME" + entity.getTime() + "TIME");
//        
//                    copyProperties(entity, form2);
//        
//                    System.out.println("ID" + form2.getId() + "ID");
//                    System.out.println("NAME" + form2.getName() + "NAME");
//                    System.out.println("TIME" + form2.getTime() + "TIME");
//        
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//        
    }

}

