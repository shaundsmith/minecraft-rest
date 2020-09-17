package dev.shaundsmith.minecraft.rest.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Supplies the error handlers for the Minecraft REST mod.
 */
public class ErrorHandlerSupplier implements Supplier<List<ErrorHandler>> {

    private List<ErrorHandler> errorHandlers;

    @Override
    public List<ErrorHandler> get() {
        if (errorHandlers == null) {
            registerHandlers();
        }
        return errorHandlers;
    }

    private void registerHandlers() {
        errorHandlers = new ArrayList<>();

        errorHandlers.add(new UnsupportedMediaTypeErrorHandler());

        // This error handler must always be the last in the list!
        errorHandlers.add(new UnknownErrorHandler());
    }

}
