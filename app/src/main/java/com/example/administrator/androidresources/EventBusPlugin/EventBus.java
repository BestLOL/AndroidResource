package com.example.administrator.androidresources.EventBusPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus <T>{

    private static EventBus instance = new EventBus();

    private Map<T,List<SubScribeMethod>> cacheMap;

    private EventBus(){
        cacheMap = new HashMap<>();
    }

    //单例模式,饿汉式
    public static EventBus getDefault() {
        return instance;
    }

    //注册对象
    public void register(T obj) {
        //传进来的对象是一个具体的类
        if(cacheMap==null || !cacheMap.containsKey(obj)){
            List<SubScribeMethod> listMethods = findSubScribeMethods(obj);
            cacheMap.put(obj,listMethods);
        }
    }

    private List<SubScribeMethod> findSubScribeMethods(T obj) {
        List<SubScribeMethod> listMethods = new ArrayList<>();

        //1 获取当前类对象
        Class<T> clazz = (Class<T>) obj.getClass();

        //优化部分，将其父类也加入到判断中来
        while(clazz!=null){
            String name = clazz.getName();
            //如果父类是系统类，则不用遍历
            if(name.startsWith("java.")||name.startsWith("javax.")){
                break;
            }
            //2 获取注册类中的所有方法，
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method:methods){
                //3 查找具有Subscribe注解的方法
                if(method.isAnnotationPresent(SubScribe.class)){
                    //4 有Subscribe注解，1 获取注解值 2 获取方法 3 获取参数类型
                    SubScribe subscribe = method.getAnnotation(SubScribe.class);

                    //1 注解对象
                    ThreadModel threadModel = subscribe.thread();
                    //2 方法就是method
                    //3 获取参数

                    Class<?>[] types = method.getParameterTypes();
                    if(types.length!=1){
                        System.out.println("参数类型错误，只接受一个参数");
                    }

                    //5 获取了我们想要的东西之后，需要使用一个对象将他保存起来
                    SubScribeMethod subScribeMethod = new SubScribeMethod(method,types[0],threadModel);
                    //6 注册类->SubScribe方法，一对多保存，使用Map
                    listMethods.add(subScribeMethod);
                }
            }
            clazz = (Class<T>) clazz.getSuperclass();
        }

        return listMethods;
    }

    //传递对象
    public void post(T objBean) {
        //根据参数类型objBean执行对应方法

        for(Map.Entry<T,List<SubScribeMethod>> mapEntry : cacheMap.entrySet()){
            T objClass = mapEntry.getKey(); //注册类
            for(SubScribeMethod method:mapEntry.getValue()){
                //参数
                Class<?> type = method.getClazz();

                //判断type与objBean类是否相同，或者type是否是objBean的父类或超类
                if(type.isAssignableFrom(objBean.getClass())){
                    invoke(method,objClass,objBean);
                }
            }
        }
    }

    //方法执行
    private void invoke(SubScribeMethod subMethod, T objClass, T type) {
        try {
            subMethod.getMethod().invoke(objClass,type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
