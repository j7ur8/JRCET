package jrcet.help.d4ocr.utils;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import burp.BurpExtender;

import java.nio.FloatBuffer;
import java.util.Map;


public class ONNXRuntimeUtils implements AutoCloseable {
    /**ONNX服务的环境 */
    private OrtEnvironment env;

    public ONNXRuntimeUtils(){
        try{
//            BurpExtender.stdout.println("cccc");
            env = OrtEnvironment.getEnvironment();
//            BurpExtender.stdout.println("ccc2c");
        }catch (Exception e){
            BurpExtender.stdout.println(e);
        }

    }

    /**
     * 执行ONNX模型并返回结果
     * @param modelPath 模型文件的存放地址
     * @param inputs 模型的输入
     * @return 执行结果
     */
    public OrtSession.Result run(String modelPath, Map<String, OnnxTensor> inputs) {
        try (OrtSession session = env.createSession(modelPath)) {
            return session.run(inputs);
        } catch (Exception e) {
            e.printStackTrace();
            BurpExtender.stdout.println(e);
        }
        return null;
    }
    /**
     * 创建单精度浮点数张量
     * @param data 浮点缓存数据
     * @param shape 张量形状
     * @return 创建的ONNX张量
     */
    public OnnxTensor createTensor(FloatBuffer data, long[] shape) {
        try {
            return OnnxTensor.createTensor(env, data, shape);
        } catch (Exception e) {
           BurpExtender.stdout.println(e);
        }
        return null;
    }

    /**
     * 创建单精度浮点数张量
     * @param data 浮点数组
     * @param shape 张量形状
     * @return 创建的ONNX张量
     */
    public OnnxTensor createTensor(float[] data, long[] shape) {
        return createTensor(FloatBuffer.wrap(data), shape);
    }

    /**
     * 关闭ONNX服务
     */
    @Override
    public void close() throws Exception {
        env.close();
    }
}
