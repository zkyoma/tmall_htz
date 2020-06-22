package com.htz.tmall.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 */
public class ReflectionUtils {

    /**
     * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenericType(Class clazz, int index){
        //获取父类的参数类型
        Type genericSuperclass = clazz.getGenericSuperclass();
        /* 判断是否为泛型参数 */
        if(!(genericSuperclass instanceof ParameterizedType)){
            return Object.class;
        }
        //是泛型，就强转为泛型参数类型
        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
        //获取所有的泛型参数
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if(index < 0 || index >= actualTypeArguments.length){
            return Object.class;
        }
        if(!(actualTypeArguments[index] instanceof Class)){
            return Object.class;
        }
        return (Class) actualTypeArguments[index];
    }

    /**
     * 通过反射, 获得 Class 定义中声明的父类的泛型参数类型的第一个
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T> Class<T> getSuperGenericType(Class clazz){
        return getSuperClassGenericType(clazz, 0);
    }
}
