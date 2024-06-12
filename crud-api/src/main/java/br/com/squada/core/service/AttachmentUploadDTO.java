package br.com.squada.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentUploadDTO {

	@NotNull
	private List<MultipartFile> files;
	
}
