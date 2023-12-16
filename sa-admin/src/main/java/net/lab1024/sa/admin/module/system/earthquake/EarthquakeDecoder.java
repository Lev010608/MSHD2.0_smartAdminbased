package net.lab1024.sa.admin.module.system.earthquake;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class EarthquakeDecoder {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void decodeEarthquakeCode(String code) {
        if (code.length() != 36) {
            System.out.println("Invalid earthquake code length!");
            return;
        }

        String geographicalInfoCode = code.substring(0, 26);
        String sourceCode = code.substring(26, 29);
        String carrierCode = code.substring(29, 30);
        String disasterInfoCode = code.substring(30, 36);
        try {
            File file = new File("src/main/java/net/lab1024/sa/admin/module/system/earthquake/EarthquakeCode.json"); // 替换为你的 JSON 文件路径
            Map<String, Map<String, String>> mappingData = objectMapper.readValue(file, Map.class);
            // 使用 mappingData 进行后续操作
            // 例如：mappingData.get("sourceMap").get("1").get("00") 获取来源码对应的值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
