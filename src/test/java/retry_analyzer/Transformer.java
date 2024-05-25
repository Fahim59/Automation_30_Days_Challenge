package retry_analyzer;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Transformer implements IAnnotationTransformer {

    public void transform(ITestAnnotation annotation, Class testclass, Constructor constructor, Method method){
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
