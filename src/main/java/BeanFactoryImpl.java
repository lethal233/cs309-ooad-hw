import annotations.Inject;
import annotations.Value;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/3 14:34
 */
public class BeanFactoryImpl implements BeanFactory {
    private Properties injectProperties;
    private Properties valueProperties;

    public BeanFactoryImpl() {
        this.injectProperties = new Properties();
        this.valueProperties = new Properties();
    }

    @Override
    public void loadInjectProperties(File file) {
        try {
            InputStream is = new FileInputStream(file);
            injectProperties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadValueProperties(File file) {
        try {
            InputStream is = new FileInputStream(file);
            valueProperties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> clazz) {
        Class<?> clazzImpl = null;
        try {
            clazzImpl = Class.forName(injectProperties.getProperty(clazz.getName(), clazz.getName()));
            Constructor<?>[] constructors = clazzImpl.getDeclaredConstructors();
            Constructor<?> constructor = null;
            for (Constructor<?> c : constructors) {
                c.setAccessible(true);
                if (c.getAnnotation(Inject.class) != null) {
                    constructor = c;
                    break;
                }
            }
            if (constructor == null) {
                constructor = clazzImpl.getDeclaredConstructor();
            }
            Parameter[] parameters = constructor.getParameters();
            List<Object> arguments = new ArrayList<>(parameters.length);
            for (Parameter p : parameters) {
                Value valueAnnotation = p.getAnnotation(Value.class);
                if (valueAnnotation != null) {
                    String v = valueProperties.getProperty(valueAnnotation.value());
                    arguments.add(parse(p.getType(), v, valueAnnotation, p.getType().isArray()));
                } else {
                    arguments.add(createInstance(p.getType()));
                }
            }
            Object instance = constructor.newInstance(arguments.toArray());

            Field[] fields = clazzImpl.getDeclaredFields();
            for (Field f : fields) {
                try {
                    f.setAccessible(true);
                    Value valueAnnotation = f.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        String v = valueProperties.getProperty(valueAnnotation.value());
                        f.set(instance, parse(f.getType(), v, valueAnnotation, f.getType().isArray()));
                    } else if (f.getAnnotation(Inject.class) != null) {
                        f.set(instance, createInstance(f.getType()));
                    }
                    f.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return (T) instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object parse(Class<?> source, String var1, Value v, boolean isAry) {
        return isAry ? parseAry(source, var1.split(v.delimiter())) : parseVar(source, var1);
    }

    private Object parseVar(Class<?> source, String var1) {
        Object par = null;
        try {
            if (source == byte.class) {
                par = Byte.parseByte(var1);
            } else if (source == short.class) {
                par = Short.parseShort(var1);
            } else if (source == int.class) {
                par = Integer.parseInt(var1);
            } else if (source == long.class) {
                par = Long.parseLong(var1);
            } else if (source == float.class) {
                par = Float.parseFloat(var1);
            } else if (source == double.class) {
                par = Double.parseDouble(var1);
            } else if (source == boolean.class) {
                par = Boolean.parseBoolean(var1);
            } else if (source == char.class) {
                par = var1.charAt(0);
            } else if (source == Character.class) {
                par = var1.charAt(0);
            } else {
                par = source.getConstructor(String.class).newInstance(var1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return par;
    }

    private Object parseAry(Class<?> source, String[] var1) {
        try {
            if (source == byte[].class) {
                byte[] par = new byte[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Byte.parseByte(var1[i]);
                }
                return par;
            } else if (source == short[].class) {
                short[] par = new short[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Short.parseShort(var1[i]);
                }
                return par;
            } else if (source == int[].class) {
                int[] par = new int[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Integer.parseInt(var1[i]);
                }
                return par;
            } else if (source == long[].class) {
                long[] par = new long[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Long.parseLong(var1[i]);
                }
                return par;
            } else if (source == float[].class) {
                float[] par = new float[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Float.parseFloat(var1[i]);
                }
                return par;
            } else if (source == double[].class) {
                double[] par = new double[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Double.parseDouble(var1[i]);
                }
                return par;
            } else if (source == boolean[].class) {
                boolean[] par = new boolean[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = Boolean.parseBoolean(var1[i]);
                }
                return par;
            } else if (source == char[].class) {
                char[] par = new char[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = var1[i].charAt(0);
                }
                return par;
            } else if (source == Character[].class) {
                Character[] par = new Character[var1.length];
                for (int i = 0; i < var1.length; i++) {
                    par[i] = var1[i].charAt(0);
                }
                return par;
            } else {
                Object target = Array.newInstance(source.getComponentType(), var1.length);
                for (int i = 0; i < var1.length; i++) {
                    Array.set(target, i, source.getComponentType().getConstructor(String.class).newInstance(var1[i]));
                }
                return target;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
