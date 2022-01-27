package se.lexicon.peopleapi.model;

import java.util.List;
import java.util.Map;

public class ValidationErrorResponse extends CustomExceptionResponse{
    private Map<String, List<String>> errors;

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
