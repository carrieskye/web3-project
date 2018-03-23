package ui.controller;

import service.ShopService;

public class ControllerFactory {
	
    public RequestHandler getController(String key, ShopService model) {
        return createHandler(key, model);
    }
    
	private RequestHandler createHandler(String handlerName, ShopService model) {
		RequestHandler handler = null;
		try {
			Class<?> handlerClass = Class.forName("ui.controller."+ handlerName);
			Object handlerObject = handlerClass.newInstance();
			handler = (RequestHandler) handlerObject;
	    		handler.setService(model);
		} catch (Exception e) {
			throw new RuntimeException("The requested page doesn’t exist");
		}
		return handler;
	}


}
