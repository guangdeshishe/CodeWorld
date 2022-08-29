package com.agilezhu.java;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Generate ARouterPage.java for ARouter
 */
@AutoService(Processor.class)
public class ARouterProcessor extends AbstractProcessor {
    private Filer mFiler; //文件相关工具类：用于保存生成的java类文件
    private Elements mElementUtils; //元素相关工具类：用于获取java类文件
    private Messager mMessager;//用于打印日志

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //返回该注解处理器能够处理哪些注解
        Set<String> types = new LinkedHashSet<>();
//        types.add(BindView.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //返回当前注解处理器支持的java版本号
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取所有的源码文件
        Set<? extends Element> elements = roundEnvironment.getRootElements();
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {//判断是否class类
                continue;
            }
            //转换成class类型
            TypeElement typeElement = (TypeElement) element;
            //当前文件的类名
            String classSimpleName = element.getSimpleName().toString();
            //将要生成的java完整类名：BindProxy$+当前类名
            String targetClassName = GENERATE_CLASS_NAME_HEAD + element.getSimpleName();

            //创建方法(构造方法)
            MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("<init>")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(typeElement.asType()), classSimpleName.toLowerCase());

            //获取当前类里所有元素
            List<? extends Element> members = mElementUtils.getAllMembers(typeElement);
            //当前类里所有添加了BindView注释的元素
            List<Element> annotationMembers = new ArrayList<>();
            for (Element member : members) {
                BindView bindViewAnnotation = member.getAnnotation(BindView.class);
                if (bindViewAnnotation != null) {
                    annotationMembers.add(member);
                    String paramName = classSimpleName.toLowerCase();
                    //构造方法中添加初始化代码：findViewById
                    bindMethodBuilder.addStatement(
                            String.format(
                                    paramName + ".%s = (%s) " + paramName + ".findViewById(%s)"
                                    , member.getSimpleName()
                                    , ClassName.get(member.asType()).toString()
                                    , bindViewAnnotation.value()));
                }
            }
            //如果该类中没有我们自定义的注解，则不生成对应java处理类
            if (annotationMembers.isEmpty()) {
                continue;
            }

            //创建类
            TypeSpec bindProxyClass = TypeSpec.classBuilder(targetClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(bindMethodBuilder.build())
                    .build();
            //创建java文件
            JavaFile bindProxyFile = JavaFile
                    .builder(GENERATE_PACKAGE, bindProxyClass)
                    .build();
            try {
                //保存java类文件
                bindProxyFile.writeTo(mFiler);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}


