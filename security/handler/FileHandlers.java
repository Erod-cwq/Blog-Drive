package com.example.jpa_learn.security.handler;

import com.example.jpa_learn.entity.Attachment;
import com.example.jpa_learn.entity.support.UploadResult;
import com.example.jpa_learn.exception.FileOperationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;

@Slf4j
@Component
public class FileHandlers {
    private final String workDir;

    public FileHandlers() {
        final String USER_HOME = System.getProperty("user.home");
        final String FILE_SEPARATOR = File.separator;
        workDir = USER_HOME + FILE_SEPARATOR + ".halo" + FILE_SEPARATOR;
    }

    @NonNull
    public UploadResult upload(@NonNull MultipartFile file, String subDir){
        Assert.notNull(file, "Multipart file must not be null");
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String basename = getBasename(Objects.requireNonNull(file.getOriginalFilename()));
        String subFilePath = subDir + basename;
        Path uploadPath = Paths.get(workDir, subFilePath);
        System.out.println(uploadPath);
        try {
            file.transferTo(uploadPath);
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(basename);
            uploadResult.setFilePath(subFilePath);
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSize(file.getSize());
            uploadResult.setKey(subFilePath);
            if(uploadResult.getMediaType().toString().startsWith("image") && subDir.equals("upload/")){
                final String thumbnailBasename = basename + "-thumbnail" ;
                final String thumbnailSubFilePath = subDir + thumbnailBasename + '.' + extension;
                final Path thumbnailPath = Paths.get(workDir, thumbnailSubFilePath);
                try(InputStream is = file.getInputStream()){
                    BufferedImage originalImage = ImageIO.read(is);
                    boolean result = generateThumbnail(originalImage, thumbnailPath);
                    if (result){
                        uploadResult.setThumbPath(thumbnailSubFilePath);
                    }else {
                        uploadResult.setThumbPath(subFilePath);
                    }
                }

            }



            return uploadResult;
        } catch (Throwable e) {
            throw new FileOperationException("上传附件失败").setErrorData(uploadPath);
        }

    }

    public void delete(@NonNull Attachment attachment){
        Assert.notNull(attachment, "Attachment must not be null");
        String name = attachment.getName();
        String subFilePath = "upload/" + name;
        Path path = Paths.get(workDir, subFilePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileOperationException("附件 " + name + " 删除失败", e);
        }
        String extension = FilenameUtils.getExtension(name);
        String thumbnailName = name + "-thumbnail" + '.' + extension;
        String subThumbnailPath = "upload/"+thumbnailName;
        Path thumbnailPath = Paths.get(workDir, subThumbnailPath);
        try {
            boolean deleteResult = Files.deleteIfExists(thumbnailPath);
            if (!deleteResult) {
                log.warn("Thumbnail: [{}] may not exist", thumbnailPath.toString());
            }
        } catch (IOException e) {
            throw new FileOperationException("缩略图 " + name + " 删除失败", e);
        }


    }

    @NonNull
    public static String getBasename(@NonNull String filename){
        int separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar);
        filename = filename.substring(separatorLastIndex + 1);
        return filename;
    }

    private boolean generateThumbnail(BufferedImage originalImage, Path thumbPath){
        Assert.notNull(originalImage, "Image must not be null");
        Assert.notNull(thumbPath, "Thumb path must not be null");
        boolean result = false;
        try{
            Files.createFile(thumbPath);
            Thumbnails.of(originalImage).size(256, 256).keepAspectRatio(true)
                    .toFile(thumbPath.toString());
            result = true;

        }catch (Throwable e){
            log.warn("Failed to generate thumbnail: " + thumbPath, e);
        }finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            originalImage.getGraphics().dispose();
        }
        return result;
    }
}
