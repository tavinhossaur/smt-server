package com.ifsp.tavinho.smt_backend.domain.dtos.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateProfilePhotoDTO(
    @NotBlank(message = "Encoded image is required") 
    @Pattern(regexp = "^data:image\\/(png|jpeg|jpg|gif);base64,.*$", message = "Invalid image format")
    String encodedBase64Image
) { }
