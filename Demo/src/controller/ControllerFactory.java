package controller;

import domain.service.CountryService;

public class ControllerFactory {
	
    public RequestHandler getController(String key, CountryService model) {
        return createHandler(key, model);
    }
    
	private RequestHandler createHandler(String handlerName, CountryService model) {
		RequestHandler handler = null;
		try {
			Class<?> handlerClass = Class.forName("controller."+ handlerName);
			Object handlerObject = handlerClass.newInstance();
			handler = (RequestHandler) handlerObject;
	    		handler.setService(model);
		} catch (Exception e) {
			throw new RuntimeException("The requested page doesn’t exist");
		}
		return handler;
	}


}
