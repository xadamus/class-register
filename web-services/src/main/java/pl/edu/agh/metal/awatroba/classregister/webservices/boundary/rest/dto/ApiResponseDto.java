package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto;

public class ApiResponseDto {

    private Boolean success;
    private String message;

    public ApiResponseDto(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
