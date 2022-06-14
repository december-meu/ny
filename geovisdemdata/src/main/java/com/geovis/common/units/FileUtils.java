package com.geovis.common.units;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.*;

/**
 * 文件工具
 *
 * @Author GeL
 * @Date 2021/4/7 14:46
 */
public class FileUtils {
    /**
     * 日志管理
     */
    private final static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private static int BUFFER_LEN = 1024;


    /**
     * 将ZIP解压到ZIP同名文件夹下面
     *
     * @param zipFile
     */
    public static String unZipFile(File zipFile) {
        return unZipFile(zipFile, zipFile.getAbsolutePath().substring(0, zipFile.getAbsolutePath().lastIndexOf(".")) + File.separator);
    }


    /**
     * 将ZIP解压到rootPath目录下面
     *
     * @param zipFile
     */
    public static String unZipFile(File zipFile, String rootPath) {
        log.debug("开始解压  BEGIN·········");
        File rootFile = new File(rootPath);
        //1.创建根文件夹
        if (!rootFile.exists()) {
            rootFile.mkdirs();
            log.debug("创建根文件夹: " + rootPath);
        }
        try {
            String targetPath;
            BufferedOutputStream dest;
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis), Charset.forName("gbk"));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                targetPath = rootPath + entry.getName();
                File targetFile = new File(targetPath);
                if (entry.isDirectory()) {//2.1 文件夹直接新建
                    targetFile.mkdir();
                    log.debug("创建文件夹: " + entry.getName());
                } else {
                    //2.2 内容输出到文件
                    int count;
                    byte[] data = new byte[BUFFER_LEN];
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    dest = new BufferedOutputStream(fos, BUFFER_LEN);
                    while ((count = zis.read(data, 0, BUFFER_LEN)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                    log.debug("输入到文件: ", entry.getName());
                }
            }
            zis.close();
        } catch (Exception e) {
            log.error("解压数据失败：", e.getMessage());
        }
        log.debug("完成解压  END·········");
        return rootPath;
    }


    /**
     * 压缩文件
     *
     * @param dirPath
     * @param zipFileName
     */
    public static void compress(String dirPath, String zipFileName) {
        zipFileName = zipFileName + ".zip";//添加文件的后缀名

        File dirFile = new File(dirPath);

        List<File> fileList = Stream.of(dirFile.listFiles()).collect(Collectors.toList());

        byte[] buffer = new byte[1024];
        ZipEntry zipEntry = null;
        int readLength = 0;     //每次读取出来的长度

        try {
            // 对输出文件做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            for (File file : fileList) {
                if (file.isFile()) {   //若是文件，则压缩文件
                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zos.putNextEntry(zipEntry);

                    InputStream is = new BufferedInputStream(new FileInputStream(file));

                    while ((readLength = is.read(buffer, 0, 1024)) != -1) {
                        zos.write(buffer, 0, readLength);
                    }
                    is.close();
                    System.out.println("file compress:" + file.getCanonicalPath());
                } else {     //若是空目录，则写入zip条目中

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));
                    zos.putNextEntry(zipEntry);
                    System.out.println("dir compress: " + file.getCanonicalPath() + "/");
                }
            }
            zos.close();  //最后得关闭流，不然压缩最后一个文件会出错
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取相对路径
     *
     * @param dirPath 源文件路径
     * @param file    准备压缩的单个文件
     */
    public static String getRelativePath(String dirPath, File file) {
        File dirFile = new File(dirPath);
        String relativePath = file.getName();

        while (true) {
            file = file.getParentFile();
            if (file == null) break;
            if (file.equals(dirFile)) {
                break;
            } else {
                relativePath = file.getName() + File.separator + relativePath;
            }
        }
        return relativePath;
    }

    public static List<String> unPackZip(File zipFile, String suffix, String unpackFolder) {
        log.debug("开始解压  BEGIN·········");
        List<String> fileNames = new ArrayList<>();
        String fileEncoding = null;
        try {
            fileEncoding = checkEncoding(zipFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String fileEncoding1 = (fileEncoding != null) ? fileEncoding : "UTF-8";
        try (ZipArchiveInputStream zais = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), 4096), fileEncoding1)) {
            ZipArchiveEntry entry = null;
            while ((entry = zais.getNextZipEntry()) != null) {
                //遍历压缩包，如果进行有选择解压，可在此处进行过滤
                if (entry.getName().endsWith(suffix)) {
                    File tmpFile = new File(unpackFolder, entry.getName());
                    if (entry.isDirectory()) {
                        tmpFile.mkdirs();
                        log.debug("创建文件夹: " + tmpFile.getAbsolutePath());
                    } else {
                        if (!tmpFile.exists()) {
                            if (!tmpFile.getParentFile().exists()) {
                                tmpFile.getParentFile().mkdirs();
                                log.debug("创建根文件夹: " + tmpFile.getParentFile());
                            }
                        }
                        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), 4096)) {
                            IOUtils.copy(zais, os);
                        }
                        log.debug("解压文件成功： " + tmpFile.getAbsolutePath());
                        fileNames.add(tmpFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("解压文件失败： " + e.getMessage());
        }
        log.debug("完成解压  END·········");
        return fileNames;
    }

    //判断字符编码
    private static String checkEncoding(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] b = new byte[3];
        try {
            int i = in.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        if (b[0] == -1 && b[1] == -2) {
            return "UTF-16";
        } else if (b[0] == -2 && b[1] == -1) {
            return "Unicode";
        } else if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
            return "UTF-8";
        } else {
            return "GBK";
        }
    }

    public static void delDirectory(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File tmp : files) {
                if (tmp.isDirectory()) {
                    //递归直到目录下没有文件
                    delDirectory(tmp);
                } else {
                    tmp.delete();
                }
            }
        }
        if (file.delete()) {
            log.debug("删除文件成功： " + file.getAbsolutePath());
        }
    }
}
