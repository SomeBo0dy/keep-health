package team.keephealth.common.python;
import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.YoloV5Translator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.LogRecord;

/*
detect方法为检验图片
参数：可为文件目录的字符串，或者输入ai.djl.modality.cv.Image加载路径下的图片
返回值：List<Map<String, Object>>，map包含种类名和每100g卡路里（例如：[{calorie=109, label=麻婆豆腐}, {calorie=116, label=米饭}]）
 */
public class ModelDetect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int imageSize = 640;
    private List index =Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");//标签索引，共10类食物
    /*
    model文件下存放模型权重，类别文本文件，卡路里文本文件
     */
    private String modelFile = "/www/wwwroot/west2/keep-health/model/best.torchscript.pt";
    private String labelFile = "/www/wwwroot/west2/keep-health/model/voc_classes.txt";
    private String calorieFile = "/www/wwwroot/west2/keep-health/model/calorie.txt";
//    private String modelFile = "model/best.torchscript.pt";
//    private String labelFile = "model/voc_classes.txt";
//    private String calorieFile = "model/calorie.txt";

    private Predictor<Image, DetectedObjects> predictor;

    public ModelDetect() {
        Pipeline pipeline = new Pipeline();
        pipeline.add(new Resize(imageSize));
        pipeline.add(new ToTensor());

        Translator<Image, DetectedObjects> translator =  YoloV5Translator
                .builder()
                .setPipeline(pipeline)
                .optSynset(index)
                .optThreshold(0.5f)
                .build();

        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .optApplication(Application.CV.INSTANCE_SEGMENTATION)
                .setTypes(Image.class, DetectedObjects.class)
                .optDevice(Device.cpu())
                .optModelPath(Paths.get(modelFile))
                .optTranslator(translator)
                .optProgress(new ProgressBar())
                .build();

        try {
            ZooModel<Image,DetectedObjects> model = ModelZoo.loadModel(criteria);
            predictor = model.newPredictor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ModelNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedModelException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> detect(MultipartFile file) {
        System.out.println("deal MultipartFile to inputStream");
        log.info("deal MultipartFile to inputStream");
        byte [] byteArr = new byte[0];
        try {
            byteArr = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        System.out.println("MultipartFile deal over");
        log.info("MultipartFile deal over");
        Image img = null;
        DetectedObjects results = null;
        try {
            img = ImageFactory.getInstance().fromInputStream(inputStream);
            System.out.println("start to predict");
            log.info("start to predict");
            results = predictor.predict(img);
            System.out.println("predict over");
            log.info("predict over");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TranslateException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List label = readTxt(new File(labelFile));
        List calorie = readTxt(new File(calorieFile));
        for (Classifications.Classification item : results.items()) {
            Map<String, Object> info = new HashMap<>();
            info.put("label", label.get(Integer.valueOf(item.getClassName())));
            info.put("calorie",calorie.get(Integer.valueOf(item.getClassName())));
            list.add(info);
        }
        return list;
    }

    public List<Map<String, Object>> detect(InputStream fileString) {
        Image img = null;
        DetectedObjects results = null;
        try {
            img = ImageFactory.getInstance().fromInputStream(fileString);
            log.info("start to predict");
            results = predictor.predict(img);
            log.info("predict over");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TranslateException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List label = readTxt(new File(labelFile));
        List calorie = readTxt(new File(calorieFile));
        for (Classifications.Classification item : results.items()) {
            Map<String, Object> info = new HashMap<>();
            info.put("label", label.get(Integer.valueOf(item.getClassName())));
            info.put("calorie",calorie.get(Integer.valueOf(item.getClassName())));
            list.add(info);
        }
        return list;
    }

    public static List<String> readTxt(File file){

        List<String> list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                list.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
