package proyecto_pd_dh.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
