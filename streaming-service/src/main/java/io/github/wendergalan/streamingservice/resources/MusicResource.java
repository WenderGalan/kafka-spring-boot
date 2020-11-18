package io.github.wendergalan.streamingservice.resources;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping("/v1")
public class MusicResource {

    @RequestMapping("/music")
    @ResponseBody
    public ResponseEntity<InputStreamResource> listenMusic() throws Exception {
        // Recupera a música
        File musicFile = ResourceUtils.getFile("classpath:mp3/alo_ambev.mp3");

        // Pega o tamanho do arquivo
        long len = Files.size(Paths.get(musicFile.toURI()));

        MediaType mediaType = parseMediaType(APPLICATION_OCTET_STREAM_VALUE);

        // Crio o Stream que vai ser usado para retornar no response
        InputStreamResource resource = new InputStreamResource(new FileInputStream(musicFile));

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(len)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + musicFile.getName() + "\"")
                .body(resource);
    }
}
