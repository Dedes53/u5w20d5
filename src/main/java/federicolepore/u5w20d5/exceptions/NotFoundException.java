package federicolepore.u5w20d5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Nel database non esiste nessun elemento corrispondente all'id:  " + id);
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
