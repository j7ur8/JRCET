/*
 * Copyright © 2022 <a href="mailto:zhang.h.n@foxmail.com">Zhang.H.N</a>.
 *
 * Licensed under the Apache License, Version 2.0 (thie "License");
 * You may not use this file except in compliance with the license.
 * You may obtain a copy of the License at
 *
 *       http://wwww.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language govering permissions and
 * limitations under the License.
 */
package jrcet.help.d4ocr;


import ai.onnxruntime.OnnxTensor;
import com.alibaba.fastjson.JSONArray;
import jrcet.help.Helper;
import jrcet.help.d4ocr.utils.IOUtils;
import jrcet.help.d4ocr.utils.ImageUtils;
import jrcet.help.d4ocr.utils.ONNXRuntimeUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


class OCREngineOldImpl implements OCREngine {
    /**字符集 */
    private static JSONArray charsetArray;
    /**ONNX模型文件 */
    private static File modelFile;

    public OCREngineOldImpl(){
        try  {

            InputStream is = OCREngineOldImpl.class.getResourceAsStream("/d4/common_old_charset.json");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            is.close();

            try{
                charsetArray = JSONArray.parseArray(buffer.toString("UTF-8"));
            }catch (Exception e){
                e.printStackTrace();
            }

            String javaTmpDir = System.getProperty("java.io.tmpdir", ".");
            File appTmpDir = new File(javaTmpDir, "top/gcszhn/d4ocr");

            modelFile = new File(appTmpDir, "common_old.onnx");

            if(!Helper.isFile(modelFile.getName())){
                appTmpDir.mkdirs();
                IOUtils.extractJarResource("/d4/common_old.onnx", modelFile);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public String recognize(BufferedImage image) {
        try  {

            ONNXRuntimeUtils onnx = new ONNXRuntimeUtils();

            if (image == null) {
                return null;
            }

            if (modelFile == null || !modelFile.exists() || charsetArray == null) {
                return null;
            }


            image = ImageUtils.resize(image, 64 * image.getWidth() / image.getHeight(), 64);
            image = ImageUtils.toGray(image);

            long[] shape = {1, 1, image.getHeight(), image.getWidth()};
            float[] data = new float[(int)(shape[0] * shape[1] * shape[2] * shape[3])];
            image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), data);

            for (int i = 0; i < data.length; i++) {
                data[i] /= 255;
                data[i] = (float) ((data[i] - 0.5) / 0.5);
            }


            Map<String, OnnxTensor> map = new HashMap<>();
            map.put("input1",  onnx.createTensor(data, shape));

            OnnxTensor indexTensor = (OnnxTensor) onnx.run(
                    modelFile.getAbsolutePath(),
                    map
            ).get(0);

            long[][] index = (long[][])indexTensor.getValue();

            indexTensor.close();
            onnx.close();

            StringBuilder words = new StringBuilder();
            for (long i: index[0]) {
                words.append((String) charsetArray.get((int) i));
            }

            return words.toString();
        } catch (Exception e) {


        }
        return null;
    }
}
