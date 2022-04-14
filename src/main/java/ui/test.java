package ui;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;


public class test {

    public static void main(String[] args) throws Exception {
        System.load("C:\\Users\\J7ur8\\Desktop\\App\\OpenCV\\opencv\\build\\java\\x64\\opencv_java455.dll");
        String imgBase64 = "iVBORw0KGgoAAAANSUhEUgAAAG4AAAAoCAMAAAAohD+4AAAAP1BMVEUAAADdGW3lIXXcGGzWEmbTD2PEAFTdGW3NCV3VEWXfG2/HA1feGm7FAVXiHnLNCV3eGm7hHXHWEmbUEGTkIHRwBsdLAAAAAXRSTlMAQObYZgAAAVJJREFUeJzsl21zgyAMgLlbYREc3gb//7fuGFZ5SQJtcX6oz4diEXkSIvQqLk5Gnx3AxdugAmcHgaP+NTb1RXKELSYWG+ccOdB7P0S3t87RPu8JH1ULpbgqbboZh9JRsypWp6rsiuGELqtF3S9w3Xo3sa2XmQ99MGmNMWg/YUvYEm1sEZUsmjG7j9MhK8y9NS/qsHr268LnRwDXLRXoevXZ8lCz2sV+AAjNjc2N7sbGoS987ARYfdXwZPx3m3oCNI4uHT/PI1S6ks8W1IPW2v2LlDKG3LA9jbWJTwaItQmD9rNRVqflKzozKpu27ijT5rtfzjNjW5alc8ben/d7DQDgp0D8HSztKdA38sbq0F3Qp2N2AO4rN104VPt1eHY8qS4e4uKh2j3hE5UOQevR/8E4ndaH+Khbw3RQwOh8DyNiSms3jZiuTJJMdJp4328AAAD//zRYHkIKs2aeAAAAAElFTkSuQmCC";

        byte[] ss = b64decoder.decode(imgBase64);

        getHuidu("C:\\Users\\J7ur8\\Desktop\\111.jpg");
        //得到二值化处理图像
        getErzhihua("C:\\Users\\J7ur8\\Desktop\\111.jpg");
    }

    //得到灰度图像
    public static void getHuidu(String imgUrl){
        Mat image= Imgcodecs.imread(imgUrl,Imgcodecs.IMREAD_GRAYSCALE);
        //读入一个图像文件并转换为灰度图像（由无符号字节构成）
        Mat image1= Imgcodecs.imread(imgUrl,Imgcodecs.IMREAD_COLOR);
        //读取图像，并转换为三通道彩色图像，这里创建的图像中每个像素有3字节
        //如果输入图像为灰度图像，这三个通道的值就是相同的
        System.out.println("image has "+image1.channels()+" channel(s)");
        //channels方法可用来检查图像的通道数
        Core.flip(image,image,1);//就地处理,参数1表示输入图像，参数2表示输出图像
        //在一窗口显示结果
        HighGui.namedWindow("输入图片显示窗口");//定义窗口
        HighGui.imshow("输入图片显示窗口",image);//显示窗口
        HighGui.waitKey(0);//因为他是控制台窗口，会在mian函数结束时关闭;0表示永远的等待按键,正数表示等待指定的毫秒数
    }

    //得到二值化处理图像
    public static void getErzhihua(String imgUrl){
        // TODO Auto-generated method stub
        Mat image=Imgcodecs.imread(imgUrl);	//加载图像
        if(image.empty())
        {
            System.out.println("图像加载错误，请检查图片路径！");
            return ;
        }
        HighGui.imshow("原始图像",image);
        Mat gray=new Mat();
        Imgproc.cvtColor(image,gray,Imgproc.COLOR_RGB2GRAY);		//彩色图像转为灰度图像
        HighGui.imshow("灰度图像",gray);
        Mat bin=new Mat();
        Imgproc.threshold(gray,bin,120,255,Imgproc.THRESH_TOZERO); 	//图像二值化
        HighGui.imshow("二值图像",bin);
        HighGui.waitKey(0);
    }

//        OrtEnvironment ortEnv = OrtEnvironment.getEnvironment();
//        OrtSession ortSession = ortEnv.createSession("C:\\Users\\J7ur8\\Downloads\\common.onnx", new OrtSession.SessionOptions());
//        OnnxTensor t1 = OnnxTensor.createTensor(ortEnv, imageInByte);
//        OrtSession.Result result = ortSession.run(Collections.singletonMap("input1", t1));
//
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i).getValue());
//        }
//
//        result.close();
//        t1.close();


}

