package ba.unsa.etf.ppis.e_ticket_booking_app.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.File;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.FileDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
public class FileService {

    @Autowired
    private final FileRepository fileRepository;

    public FileService(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileDTO> findAll() {
        return fileRepository.findAll()
                .stream()
                .map(file -> mapToDTO(file, new FileDTO()))
                .collect(Collectors.toList());
    }

    public void update(final UUID id, final MultipartFile files) throws IOException{
        final File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(files, file);
        fileRepository.save(file);
    }

    public void delete(final UUID id) {
        fileRepository.deleteById(id);
    }
    public void deleteAll() {
        fileRepository.deleteAll();
    }

    public UUID create(MultipartFile files) throws IOException {

        /*File file = new File(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
         */
        final File file = new File();
        mapToEntity(files, file);
        return fileRepository.save(file).getId();
    }

    public FileDTO get(UUID id) throws IOException {
        return fileRepository.findById(id)
                .map(file -> mapToDTO(file, new FileDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private FileDTO mapToDTO(final File file, final FileDTO fileDTO) {
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setType(file.getType());
        fileDTO.setFileByte(decompressBytes(file.getFileByte()));
        return fileDTO;
    }

    private File mapToEntity(final MultipartFile file, final File files) throws IOException{
        files.setName(file.getOriginalFilename());
        files.setType(file.getContentType());
        files.setFileByte(compressBytes(file.getBytes()));
        return files;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}