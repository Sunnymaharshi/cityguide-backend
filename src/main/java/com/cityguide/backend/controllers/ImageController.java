package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.Images;
import com.cityguide.backend.exceptions.NotFoundException;
import com.cityguide.backend.repositories.ImageRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.google.protobuf.Message;

import static com.google.cloud.storage.Acl.User.ofAllUsers;

@RestController
public class ImageController {

    @Autowired
    Storage storage;

    @Autowired
    ImageRepository imageRepository;

    //Upload Image To GCP repository
    @RequestMapping(method = RequestMethod.POST, value = "/imageUpload/{city}")
    public String uploadFile(@RequestParam("image") MultipartFile fileStream, @PathVariable("city") String city)
            throws IOException, ServletException {

        String bucketName = "may-cityguide";
        checkFileExtension(fileStream.getOriginalFilename());
        String fileName = fileStream.getOriginalFilename();

        File file = multipartToFile(fileStream, fileName);
        String folder = city;

        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, folder + "/" + fileName)
                                .build(),
                        file.toURL().openStream()
                );

        return blobInfo.getMediaLink();
    }


    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }


    private void checkFileExtension(String fileName) throws ServletException {
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};
            for (String ext : allowedExt) {
                if (fileName.endsWith(ext)) {
                    return;
                }
            }
            throw new ServletException("file must be an image");
        }
    }

    //Get Signed Url
    @RequestMapping(value = "/geturl/{city}/{object-name}", method = RequestMethod.GET)
    public static URL generateV4GetObjectSignedUrl(
            @PathVariable("object-name") String objectName, @PathVariable("city") String city) throws StorageException {
        String projectId = "us-gcp-ame-con-116-npd-1";
        String bucketName = "may-cityguide";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        // Define resource
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, city + "/" + objectName)).build();

        URL url =
                storage.signUrl(blobInfo, 10080, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
        return url;
    }

    @RequestMapping(value = "/addimagedetails", method = RequestMethod.POST)
    public ResponseEntity<?> addimagedetails(@RequestBody Images images) {
        return new ResponseEntity<>(imageRepository.save(images), HttpStatus.OK);
    }

    @RequestMapping(value = "/getimagedetails/{type}/{type_id}", method = RequestMethod.GET)
    public ResponseEntity<?> addimagedetails(@PathVariable("type") String type, @PathVariable("type_id") String type_id) {
        List<Images> imagesList;
        try {
            imagesList = imageRepository.findimages(type, type_id);
        } catch (Exception e) {
            throw new NotFoundException("Images");
        }
        return new ResponseEntity<>(imagesList, HttpStatus.OK);
    }
}
