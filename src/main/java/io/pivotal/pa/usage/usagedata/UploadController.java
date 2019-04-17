package io.pivotal.pa.usage.usagedata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@CrossOrigin
@RestController
@RequestMapping("/v1")
public class UploadController {

	private UsageService usageService;

	private Logger log = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	public UploadController(UsageService usageService) {
		this.usageService = usageService;
	}

	@PostMapping("/upload")
	public ResponseEntity<Void> handleUpload(@RequestParam("file") MultipartFile file){
		log.trace("Got file and starting processing");
		StopWatch loadWatch = new StopWatch("LoadTimer");
		loadWatch.start();
		try(ZipInputStream zipStream = new ZipInputStream(new BufferedInputStream(file.getInputStream()))) {
			ZipEntry entry;
			while((entry = zipStream.getNextEntry()) != null) {
				if("org-app-usage.csv".equals(entry.getName())) {
					usageService.loadData(zipStream);
				}
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		loadWatch.stop();
		log.trace("Data load took {} seconds", loadWatch.getTotalTimeSeconds());
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
