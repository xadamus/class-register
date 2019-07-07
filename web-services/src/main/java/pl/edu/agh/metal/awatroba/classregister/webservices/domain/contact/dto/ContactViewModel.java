package pl.edu.agh.metal.awatroba.classregister.webservices.domain.contact.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ContactViewModel {
    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Min(10)
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("[name=%s, email=%s, message=%s]", name, email, message);
    }
}
