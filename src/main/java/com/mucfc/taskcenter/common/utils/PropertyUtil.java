package com.mucfc.taskcenter.common.utils;

import com.mucfc.taskcenter.common.BasicForm;
import com.mucfc.taskcenter.common.BasicVo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;


/**
 * 提供Form与PO之间的属性拷贝, 支持同名但不同类型的属性的复制。
 * <p/>
 * 目前，Spring的BeanUtils.copyProperties()以及其它第三方包都只支持同名且同类型的属性值的复制。
 *
 * @author HuQingmiao
 */
public class PropertyUtil {

    /**
     * form->entity, 把form中的属性值复制到po的同名属性中. 注意, 该方法对文件类型的属性忽略.
     *
     * @param form
     * @param vo
     */
    public static void copyProperties(BasicForm form, BasicVo vo)
            throws Exception {
        try {
            //po的属性及类型
            HashMap poFieldsMap = vo.fieldNameTypeMap();

            Field[] formFields = form.getClass().getDeclaredFields();

            StringBuffer methodName = new StringBuffer();
            Method gettingMethod = null;//form中的取值方法
            for (int i = 0; i < formFields.length; i++) {
                String formFieldName = formFields[i].getName(); //form中的属性名

                //如果po没有此同名属性
                if (!poFieldsMap.containsKey(formFieldName)) {
                    continue;
                }

                // form中的属性类型
                String formFieldType = formFields[i].getType().getName();

                // 取po的属性类型
                Class cls = (Class) poFieldsMap.get(formFieldName);
                String poFieldType = cls.getName();

                methodName.append("get");
                if (formFieldName.length() > 1 && Character.isUpperCase(formFieldName.charAt(1))) {
                    methodName.append(formFieldName);
                } else {
                    methodName.append(formFieldName.substring(0, 1).toUpperCase());
                    methodName.append(formFieldName.substring(1));
                }

                //获取form中的取值方法
                gettingMethod = form.getClass().getMethod(
                        methodName.toString(), new Class[]{});
                methodName.delete(0, methodName.length());

                //取form的属性值
                Object fValue = gettingMethod.invoke(form, new Object[]{});

                //如果属性类型相同
                if (formFieldType.equals(poFieldType)) {
                    vo.set(formFieldName, fValue); //给po设值
                    //settingMethod.invoke(vo, new Object[] { value });
                    continue;
                }

                if (fValue == null) {
                    vo.set(formFieldName, null);
                    continue;
                }

                if (fValue instanceof String) {
                    String v = ((String) fValue).trim();

                    if ("java.lang.String".equals(poFieldType)) {
                        vo.set(formFieldName, v);

                    } else if ("java.lang.Long".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Long(v));
                        }
                    } else if ("java.lang.Integer".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Integer(v));
                        }
                    } else if ("java.lang.Double".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Double(v));
                        }
                    } else if ("java.lang.Float".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            vo.set(formFieldName, new Float(v));
                        }
                    } else if ("java.util.Date".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            String format = DateTimeUtil.DATE_TIME_FORMAT.substring(0, v.length());
                            Date timestamp = DateTimeUtil.parse(v, format);
                            vo.set(formFieldName, timestamp);
                        }
                    } else if ("java.sql.Timestamp".equals(poFieldType)) {
                        if (v.equals("")) {
                            vo.set(formFieldName, null);
                        } else {
                            String format = DateTimeUtil.DATE_TIME_FORMAT.substring(0, v.length());
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

            Field[] poFields = vo.getClass().getDeclaredFields();

            StringBuffer methodName = new StringBuffer();
            Method settingMethod = null;//form中的设值方法

            for (int i = 0; i < poFields.length; i++) {
                String poFieldName = poFields[i].getName(); //po中的属性名

                //如果po没有此同名属性
                if (!formFieldsMap.containsKey(poFieldName)) {
                    continue;
                }
                //po中的属性类型
                String poFieldType = poFields[i].getType().getName();

                //取form的属性类型
                Class cls = (Class) formFieldsMap.get(poFieldName);
                String formFieldType = cls.getName();

                methodName.append("set");
                if (poFieldName.length() > 1 && Character.isUpperCase(poFieldName.charAt(1))) {
                    methodName.append(poFieldName);
                } else {
                    methodName.append(poFieldName.substring(0, 1).toUpperCase());
                    methodName.append(poFieldName.substring(1));
                }

                //获取form中设值方法
                settingMethod = form.getClass().getMethod(
                        methodName.toString(), new Class[]{cls});
                methodName.delete(0, methodName.length());

                //取po的属性值
                Object eValue = vo.get(poFieldName);

                //如果属性类型相同
                if (poFieldType.equals(formFieldType)) {
                    //给form设值
                    settingMethod.invoke(form, new Object[]{eValue});
                    continue;
                }

                if (eValue == null) {
                    settingMethod.invoke(form, new Object[]{null});
                    continue;
                }

                if ("java.lang.String".equals(formFieldType)) {
                    if (eValue instanceof String
                            || eValue instanceof Long
                            || eValue instanceof Integer
                            || eValue instanceof Double
                            || eValue instanceof Float) {
                        String v = eValue.toString();
                        settingMethod.invoke(form, new Object[]{v});

                    } else if (eValue instanceof Date) {
                        String vs = DateTimeUtil.format((Date) eValue, DateTimeUtil.DATE_TIME_FORMAT);
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

