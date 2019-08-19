package com.single.router_compiler;

import com.google.auto.service.AutoService;
import com.single.router_annimator.BindView;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class BinderProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private HashMap<String, BinderClassCreator> mCreatorMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //processingEnvironment.getElementUtils(); 处理Element的工具类，用于获取程序的元素，例如包、类、方法。
        //processingEnvironment.getTypeUtils(); 处理TypeMirror的工具类，用于取类信息
        //processingEnvironment.getFiler(); 文件工具
        //processingEnvironment.getMessager(); 错误处理工具
        //初始化的时候获取到当前扫描的对象
        //processingEnv是父类定义的ProcessingEnvironment对象，其实就是init方法回传过来的
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //指明有哪些注解需要被扫描到，返回注解的路径
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //大部分class而已getName、getCanonicalNam这两个方法没有什么不同的。
        //但是对于array或内部类等就不一样了。
        //getName返回的是[[Ljava.lang.String之类的表现形式，
        //getCanonicalName返回的就是跟我们声明类似的形式。
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        return supportTypes;
        //因为兼容的原因，特别是针对Android平台，建议使用重载getSupportedAnnotationTypes()方法替代默认使用注解实现
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //扫描整个工程   找出含有BindView注解的元素
        //找到所有带有BindView注解的类，生成对应的****_ViewBinding类
        Set<? extends Element> elements =
                roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //遍历元素
        for (Element element : elements) {
            //BindView限定了只能属性使用，这里强转为VariableElement，如果是在类上面的，那么就是typeElement
            VariableElement variableElement = (VariableElement) element;
            //返回此元素直接封装（非严格意义上）的元素。
            //类或接口被认为用于封装它直接声明的字段、方法、构造方法和成员类型
            //这里就是获取封装属性元素的类元素
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //获取简单类名
            String fullClassName = classElement.getQualifiedName().toString();
            //里面放的是BinderClassCreator，关键生成***_ViewBinding类在里面生成的
            BinderClassCreator creator = mCreatorMap.get(fullClassName);
            if (creator == null) {
                creator = new BinderClassCreator(mElementUtils.getPackageOf(classElement),
                        classElement);
                //生成之后就放到map中，方便下次使用
                mCreatorMap.put(fullClassName, creator);

            }
            //获取元素注解
            BindView bindAnnotation = variableElement.getAnnotation(BindView.class);
            //注解值
            int id = bindAnnotation.value();
            creator.putElement(id, variableElement);
        }
        for (String key : mCreatorMap.keySet()) {
            BinderClassCreator binderClassCreator = mCreatorMap.get(key);
            //通过javapoet构建生成Java类文件
            //第一个参数传入包名
            //第二个参数传入TypeSpec
            JavaFile javaFile = JavaFile.builder(binderClassCreator.getPackageName(),
                    binderClassCreator.generateJavaCode()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}