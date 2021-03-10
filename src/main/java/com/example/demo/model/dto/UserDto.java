package com.example.demo.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class UserDto {

    @NotNull
    @Positive
    private Long id;

    @NotEmpty
    private String Name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public UserDto() {
    }

    public UserDto(@NotNull @Positive Long id, @NotEmpty String name) {
        this.id = id;
        Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(Name, userDto.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name);
    }
}


