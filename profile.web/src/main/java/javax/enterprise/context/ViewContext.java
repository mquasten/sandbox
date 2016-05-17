package javax.enterprise.context;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;


public class ViewContext implements Context {

    public Class<? extends Annotation> getScope() {
        return ViewScoped.class;
    }

   
	
	public  <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        Bean<T> bean = (Bean<T>) contextual;
        @SuppressWarnings("unchecked")
		  final Map<String,T> viewMap = (Map<String, T>) getViewMap();
        if(viewMap.containsKey(bean.getName())) {
            T result = viewMap.get(bean.getName());
            return result;
        } else {
            T t = bean.create(creationalContext);
            viewMap.put(bean.getName(), t);
            return (T) t;
        }
    }

    private Map<String,Object> getViewMap() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = fctx.getViewRoot();
        return viewRoot.getViewMap(true);
    }

  
	
	public  <T> T get(Contextual<T> contextual) {
        final Bean<T> bean = (Bean<T>) contextual;
        @SuppressWarnings("unchecked")
        final Map<String,T> viewMap = (Map<String, T>) getViewMap();
        if(viewMap.containsKey(bean.getName())) {
            return  (T) viewMap.get(bean.getName());
        } else {
            return null;
        }
    }

    public boolean isActive() {
        return true;
    }
}
