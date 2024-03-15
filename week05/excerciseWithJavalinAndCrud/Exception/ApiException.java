package excerciseWithJavalinAndCrud.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiException extends Throwable {
    int statusCode;
    public ApiException(int statusCode,String msg){
        super(msg);
        this.statusCode = statusCode;
    }
}
