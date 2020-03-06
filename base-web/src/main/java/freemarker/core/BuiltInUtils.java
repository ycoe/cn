package freemarker.core;

import com.duoec.web.core.utils.SimpleTypeConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.ext.beans.*;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/5/24.
 */
public class BuiltInUtils {
    private static final Logger logger = LoggerFactory.getLogger(BuiltInUtils.class);

    public static Object eval(String expression, Environment env, Class<?> type) throws TemplateModelException {
        String[] keys = expression.split("\\.");
        TemplateModel tm = env.getDataModel().get(keys[0]);
        Object data = getTemplateModelData(tm, type);
        if (keys.length == 1) {
            return data;
        } else {
            for (int i = 1; i < keys.length; i++) {
                data = getObjectField(data, keys[i]);
            }
        }
        return data;
    }

    private static Object getObjectField(Object data, String key) throws TemplateModelException {
        Field field;
        try {
            field = data.getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            throw new TemplateModelException("获取类[" + data.getClass().getName() + "]属性[" + key + "]失败!", e);
        }
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, data);
    }

    private static Object getTemplateModelData(TemplateModel tm, Class<?> type) throws TemplateModelException {
        if (tm instanceof BeanModel) {
            if (tm instanceof CollectionModel) {
                List data = Lists.newArrayList();
                TemplateModelIterator it = ((CollectionModel) tm).iterator();
                while (it.hasNext()) {
                    TemplateModel item = it.next();
                    data.add(getTemplateModelData(item, type));
                }
                return data;
            } else if (tm instanceof IteratorModel || tm instanceof EnumerationModel) {
                throw new UnsupportedOperationException("暂未实现BuiltInUtils.getTemplateModelData(" + tm.getClass().getName() + " tm)");
            } else if (tm instanceof MapModel) {
                Map<Object, Object> maps = Maps.newHashMap();
                TemplateCollectionModel keys = ((MapModel) tm).keys();
                TemplateModelIterator it = keys.iterator();
                while (it.hasNext()) {
                    Object key = getTemplateModelData(it.next(), type);
                    maps.put(key, getTemplateModelData(((MapModel) tm).get(key.toString()), type));
                }
                return maps;
            } else if (tm instanceof NumberModel) {
                String strValue = ((NumberModel) tm).getAsNumber().toString();
                return SimpleTypeConverter.convert(strValue, type);
            } else if (tm instanceof BooleanModel) {
                return ((BooleanModel) tm).getAsBoolean();
            } else if (tm instanceof DateModel) {
                return ((DateModel) tm).getAsDate();
            } else if (tm instanceof StringModel) {
                return ((BeanModel) tm).getWrappedObject();
            } else {
                return null;
            }
        } else if (tm instanceof SimpleMethodModel || tm instanceof OverloadedMethodsModel) {
            throw new UnsupportedOperationException("暂未实现BuiltInUtils.getTemplateModelData(" + tm.getClass().getName() + " tm)");
        } else {
            return null;
        }
    }
}
