package io.pivotal.pa.usage.usagedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/v1")
public class UploadController {

	private UsageService usageService;

	@Autowired
	public UploadController(UsageService usageService) {
		this.usageService = usageService;
	}

	@PostMapping("/upload")
	public ResponseEntity<Void> handleUpload(@RequestParam("file") MultipartFile file) throws Exception {
		try(InputStream stream = file.getInputStream())
		{
			usageService.loadData(stream);
		}
		return ResponseEntity.ok(null);
	}

	class Status {
		private int rowsLoaded;
		private String message;

		public int getRowsLoaded() {
			return rowsLoaded;
		}

		public void setRowsLoaded(int rowsLoaded) {
			this.rowsLoaded = rowsLoaded;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
