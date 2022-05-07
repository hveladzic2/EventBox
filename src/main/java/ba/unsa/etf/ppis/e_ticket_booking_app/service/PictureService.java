package ba.unsa.etf.ppis.e_ticket_booking_app.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Picture;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.PictureDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PictureService {

    @Autowired
    private final PictureRepository pictureRepository;

    public PictureService(final PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<PictureDTO> findAll() {
        return pictureRepository.findAll()
                .stream()
                .map(picture -> mapToDTO(picture, new PictureDTO()))
                .collect(Collectors.toList());
    }

    public void update(final UUID id, final MultipartFile file) throws IOException{
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(file, picture);
        pictureRepository.save(picture);
    }

    public void delete(final UUID id) {
        pictureRepository.deleteById(id);
    }
    public void deleteAll() {
        pictureRepository.deleteAll();
    }

    public UUID create(MultipartFile file) throws IOException {

        /*Picture picture = new Picture(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
         */
        final Picture picture = new Picture();
        mapToEntity(file, picture);
        return pictureRepository.save(picture).getId();
    }

    public PictureDTO get(UUID id) throws IOException {
        return pictureRepository.findById(id)
                .map(picture -> mapToDTO(picture, new PictureDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private PictureDTO mapToDTO(final Picture picture, final PictureDTO pictureDTO) {
        pictureDTO.setId(picture.getId());
        pictureDTO.setName(picture.getName());
        pictureDTO.setType(picture.getType());
        pictureDTO.setPicByte(decompressBytes(picture.getPicByte()));
        return pictureDTO;
    }

    private Picture mapToEntity(final MultipartFile file, final Picture picture) throws IOException{
        picture.setName(file.getOriginalFilename());
        picture.setType(file.getContentType());
        picture.setPicByte(compressBytes(file.getBytes()));
        return picture;
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