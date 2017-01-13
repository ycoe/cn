package freemarker.core;

import com.duoec.cn.core.freemarker.PortletException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 只有freemarker.core包才能继承BuiltIn!
 * Created by ycoe on 16/5/9.
 */
public abstract class BasePipe extends BuiltIn {
    private static final Logger logger = LoggerFactory.getLogger(BasePipe.class);

    @Override
    TemplateModel _eval(Environment env) throws TemplateException {
        return render(env);
    }

    protected Number getNumber(Environment env) throws PortletException {
        try {
            return target.evalToNumber(env);
        } catch (TemplateException e) {
            logger.error("evalToNumber出错! class={}", getClass().getName());
            throw new PortletException(env, e);
        }
    }

    protected TemplateModel getTemplateModel(Environment env) throws PortletException {

        try {
            return target.eval(env);
        } catch (TemplateException e) {
            logger.error("evalToTemplateModel出错! class={}", getClass().getName());
            throw new PortletException(env, e);
        }
    }

    protected String getString(Environment env) throws PortletException {
        try {
            return target.evalAndCoerceToString(env);
        } catch (TemplateException e) {
            logger.error("evalAndCoerceToString出错! class={}", getClass().getName());
            throw new PortletException(env, e);
        }
    }

    protected abstract TemplateModel render(Environment env) throws TemplateException;

    /**
     * 注册
     * @param name
     * @param builtIn
     */
    public static void regist(String name, BuiltIn builtIn){
        builtins.put(name, builtIn);
    }
}
